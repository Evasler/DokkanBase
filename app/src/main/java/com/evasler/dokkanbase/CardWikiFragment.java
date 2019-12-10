package com.evasler.dokkanbase;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import com.evasler.dokkanbase.queryresponseobjects.card_preview;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CardWikiFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CardWikiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CardWikiFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    OnFragmentInteractionListener mListener;

    boolean lockedUI;
    int lockIndex;
    long lastUnlockTimeInMillis;
    int cardPreviewPixelSize;
    GridLayout gridLayout;
    static List<CardPreview> CardPreviews;
    static SparseArray<LinearLayout> card_preview_containers;

    public CardWikiFragment() { }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CardWikiFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CardWikiFragment newInstance(String param1, String param2) {
        CardWikiFragment fragment = new CardWikiFragment();
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
        return inflater.inflate(R.layout.card_wiki_fragment, container, false);
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

        List<Fragment> cardPreviews = getActivity().getSupportFragmentManager().getFragments();

        for (Fragment fragment : cardPreviews) {
            System.out.println(System.currentTimeMillis());
            getActivity().getSupportFragmentManager().popBackStack();
        }

        super.onDetach();

        new Thread(new Runnable() {
            @Override
            public void run() {
                mListener = null;
                gridLayout = null;
                CardPreviews = null;
                card_preview_containers = null;
                System.runFinalization();
                Runtime.getRuntime().gc();
                System.gc();
                System.out.println("Card Wiki detached");
            }
        }).start();
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
        CardPreviews = new ArrayList<>();
        card_preview_containers = new SparseArray<>();
        lockedUI = false;
        lockIndex = 0;
        lastUnlockTimeInMillis = 0;
        gridLayout = Objects.requireNonNull(getView()).findViewById(R.id.result_gridLayout);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        Objects.requireNonNull(getActivity()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        cardPreviewPixelSize = displayMetrics.widthPixels / 5;
        populate_grid();
    }

    public void populate_grid(View view) {
        populate_grid();
    }

    public void populate_grid() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MyDao myDao = AppDatabase.getDatabase(Objects.requireNonNull(getContext())).myDao();
                List<card_preview> card_previews_data = myDao.getCardsForPreview();
                loadCardPreviews(card_previews_data);
            }
        }).start();
    }

    public void loadCardPreviews(List<card_preview> card_previews_data) {
        GridLayout.Spec rowSpan = GridLayout.spec(GridLayout.UNDEFINED, 1);
        GridLayout.Spec colspan = GridLayout.spec(GridLayout.UNDEFINED, 1);
        int start = gridLayout.getChildCount();
        int end = start + 100 > card_previews_data.size() ? card_previews_data.size() : start + 100;
        for (int i = start; i < end; i++) {
            CardPreviewPostOperation cardPreviewOperation = new CardPreviewPostOperation();
            cardPreviewOperation.execute(i, card_previews_data, rowSpan, colspan);
        }
    }

    final class CardPreviewPostOperation extends AsyncTask<Object, Void, String> {

        int index;
        card_preview currentCard;
        GridLayout.LayoutParams gridLayoutParams;

        @Override
        protected String doInBackground(Object... params) {
            index = (int) params[0];
            @SuppressWarnings("unchecked")
            List<card_preview> card_previews_data = (List<card_preview>) params[1];
            GridLayout.Spec rowSpan = (GridLayout.Spec) params[2];
            GridLayout.Spec colspan = (GridLayout.Spec) params[3];
            gridLayoutParams = new GridLayout.LayoutParams(rowSpan, colspan);

            currentCard = (card_previews_data).get(index);
            LinearLayout cardPreviewContainer = new LinearLayout(getContext());
            cardPreviewContainer.setId(index + 1);
            card_preview_containers.put(index, cardPreviewContainer);

            /*while (lockedUI || (System.currentTimeMillis() - lastUnlockTimeInMillis < 20) ) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }*/
            lockedUI = true;

            return "Executed";
        }

        @Override
        protected void onPostExecute(String string) {
            CardPreviews.add(new CardPreview(currentCard.getCard_id(), currentCard.getRarity(), currentCard.getType(), cardPreviewPixelSize));
            gridLayout.addView(card_preview_containers.get(index), gridLayoutParams);
            Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                    .replace(index + 1, CardPreviews.get(index))
                    .addToBackStack(null)
                    .commit();
            currentCard = null;
            gridLayoutParams = null;
            lockedUI = false;
            lastUnlockTimeInMillis = System.currentTimeMillis();
        }
    }
}
