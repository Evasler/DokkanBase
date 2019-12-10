package com.evasler.dokkanbase;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CardPreview.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CardPreview#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CardPreview extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    OnFragmentInteractionListener mListener;

    RelativeLayout cardPreviewView;
    ImageView rarityView;
    ImageView typeView;
    ImageView backgroundView;
    ImageView iconView;

    boolean lockedUI;
    long lastUnlockTimeInMillis;
    String card_id;
    String rarity;
    String type;
    private int backgroundAndIconPixelSize;
    private int typeAndRarityPixelSize;

    public CardPreview() {}

    public CardPreview(String card_id, String rarity, String type, int pixelSize) {
        this.card_id = card_id;
        this.rarity = rarity;
        this.type = type;
        this.backgroundAndIconPixelSize = pixelSize;
        typeAndRarityPixelSize = pixelSize / 2;
        lockedUI = false;
        lastUnlockTimeInMillis = 0;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CardPreview.
     */
    // TODO: Rename and change types and number of parameters
    public static CardPreview newInstance(String param1, String param2) {
        CardPreview fragment = new CardPreview();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.card_preview_fragment, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                backgroundView.setImageResource(0);
                rarityView.setImageResource(0);
                typeView.setImageResource(0);
                iconView.setImageResource(0);

                mListener = null;
                rarityView = null;
                typeView = null;
                backgroundView = null;
                iconView = null;
                card_id = null;
                rarity = null;
                type = null;
            }
        }).start();

        super.onDetach();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        cardPreviewView = Objects.requireNonNull(getView()).findViewById(R.id.card_preview);
        rarityView = Objects.requireNonNull(getView()).findViewById(R.id.rarity);
        typeView = Objects.requireNonNull(getView()).findViewById(R.id.type);
        backgroundView = Objects.requireNonNull(getView()).findViewById(R.id.card_background);
        iconView = Objects.requireNonNull(getView()).findViewById(R.id.card_icon);

        getView().setOnClickListener(this);

        scaleTypeAndRarity();
        updateViews();
    }

    public void scaleTypeAndRarity() {
        cardPreviewView.getLayoutParams().height = backgroundAndIconPixelSize;
        cardPreviewView.getLayoutParams().width = backgroundAndIconPixelSize;
        iconView.getLayoutParams().height = backgroundAndIconPixelSize;
        iconView.getLayoutParams().width = backgroundAndIconPixelSize;
        backgroundView.getLayoutParams().height = (int)Math.ceil(backgroundAndIconPixelSize * 0.88);
        backgroundView.getLayoutParams().width = (int)Math.ceil(backgroundAndIconPixelSize * 0.8);
        rarityView.getLayoutParams().height = typeAndRarityPixelSize;
        rarityView.getLayoutParams().width = typeAndRarityPixelSize;
        typeView.getLayoutParams().height = typeAndRarityPixelSize/2;
        typeView.getLayoutParams().width = typeAndRarityPixelSize/2;
    }

    public void updateViews() {

        String card_icon_name = "card_icon_" + card_id;
        String elm_type_icon_name = type.replaceAll("Super|Extreme", "").trim().toLowerCase() + "_background";

        CardItemsOperation iconViewOperation = new CardItemsOperation();
        iconViewOperation.execute(card_icon_name);
        CardItemsOperation backgroundViewOperation = new CardItemsOperation();
        backgroundViewOperation.execute(elm_type_icon_name);
        CardItemsOperation rarityViewOperation = new CardItemsOperation();
        rarityViewOperation.execute(rarity.toLowerCase());
        CardItemsOperation typeViewOperation = new CardItemsOperation();
        typeViewOperation.execute(type.toLowerCase().replace(" ", "_"));
    }

    final class CardItemsOperation extends AsyncTask<Object, Void, String> {

        String resourceName;

        @Override
        protected String doInBackground(Object... params) {

            resourceName = params[0].toString();

            while (lockedUI || (System.currentTimeMillis() - lastUnlockTimeInMillis < 10) ) {
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            lockedUI = true;

            return "Executed";
        }

        @Override
        protected void onPostExecute(String string) {
            if (resourceName.contains("card_icon_")) {
                iconView.setImageResource(getResourceId(resourceName));
            } else if (resourceName.contains("_background")) {
                backgroundView.setImageBitmap(getBitmapFromMemCache(resourceName));
            } else if (resourceName.matches("n|r|sr|ssr|ur|lr")) {
                rarityView.setImageBitmap(getBitmapFromMemCache(resourceName));
            } else {
                typeView.setImageBitmap(getBitmapFromMemCache(resourceName));
            }
            lockedUI = false;
            lastUnlockTimeInMillis = System.currentTimeMillis();
        }
    }

    private Bitmap getBitmap(int resourceId) {
        return BitmapFactory.decodeResource(getResources(), resourceId);
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return ((MainActivity)getActivity()).getCache().get(key);
    }

    public Bitmap getResizedCardIcon(Bitmap cardIcon) {

        return Bitmap.createScaledBitmap(cardIcon, backgroundAndIconPixelSize, backgroundAndIconPixelSize, true);
    }

    public int getResourceId(@NonNull String pVariableName)
    {
        try {
            return getResources().getIdentifier(pVariableName, "drawable", Objects.requireNonNull(getActivity()).getPackageName());
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public void onClick(View view) {
        Intent myIntent = new Intent(getContext(), CardProfile.class);
        myIntent.putExtra("card_id", card_id); //Optional parameters
        Objects.requireNonNull(getContext()).startActivity(myIntent);
    }
}
