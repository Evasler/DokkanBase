package com.evasler.dokkanbase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GestureDetectorCompat;

import android.content.Intent;
import android.content.res.Resources;
import android.media.Image;
import android.os.Bundle;
import android.transition.Transition;
import android.util.DisplayMetrics;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.ViewFlipper;

import com.evasler.dokkanbase.queryresponseobjects.active_skill_details;
import com.evasler.dokkanbase.queryresponseobjects.invincible_form_card_details;
import com.evasler.dokkanbase.queryresponseobjects.related_card_details;
import com.evasler.dokkanbase.queryresponseobjects.tier_1_medal_combination;
import com.evasler.dokkanbase.queryresponseobjects.tier_2_medal_combination;
import com.evasler.dokkanbase.queryresponseobjects.tier_3_medal_combination;
import com.evasler.dokkanbase.queryresponseobjects.tier_4_medal_combination;
import com.evasler.dokkanbase.queryresponseobjects.tier_5_medal_combination;
import com.evasler.dokkanbase.queryresponseobjects.transformation_card_exchange_card_details;
import com.evasler.dokkanbase.roomentinties.card;
import com.evasler.dokkanbase.roomentinties.category;
import com.evasler.dokkanbase.roomentinties.exz_awakened_card;
import com.evasler.dokkanbase.roomentinties.link_skill;
import com.evasler.dokkanbase.roomentinties.super_attack;
import com.evasler.dokkanbase.roomentinties.super_attack_extra_effect;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static android.content.Intent.FLAG_ACTIVITY_NO_ANIMATION;

public class CardProfile extends AppCompatActivity implements GestureDetector.OnGestureListener {

    private GestureDetectorCompat detectorCompat;

    boolean navigationLocked;
    public static int extraSpace;

    MyDao myDao;

    card card;
    private int max_level;
    private List<link_skill> link_skills;
    private List<category> categories;
    private List<super_attack> super_attacks;
    private List<super_attack_extra_effect> super_attack_extra_effects;
    private List<active_skill_details> active_skill;
    private related_card_details pre_dokkan_details;
    private related_card_details dokkan_details;

    private String previousCardFormId;
    private String current_enhanced_form_card_id;
    private String current_enhanced_form_type;
    private String exz_card_id;
    private String enhancedFormType;
    private String enhancedFormCardId;
    private String transformationCondition;

    private tier_5_medal_combination pre_dokkan_medal_tier_5_details;
    private tier_4_medal_combination pre_dokkan_medal_tier_4_details;
    private tier_3_medal_combination pre_dokkan_medal_tier_3_details;
    private tier_2_medal_combination pre_dokkan_medal_tier_2_details;
    private tier_1_medal_combination pre_dokkan_medal_tier_1_details;

    private tier_5_medal_combination dokkan_medal_tier_5_details;
    private tier_4_medal_combination dokkan_medal_tier_4_details;
    private tier_3_medal_combination dokkan_medal_tier_3_details;
    private tier_2_medal_combination dokkan_medal_tier_2_details;
    private tier_1_medal_combination dokkan_medal_tier_1_details;

    private static SparseBooleanArray scrollsAdjustedStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        extraSpace = 0;
        navigationLocked = true;
        detectorCompat = new GestureDetectorCompat(this, this);
        scrollsAdjustedStatus = new SparseBooleanArray();
        scrollsAdjustedStatus.put(R.id.super_attacks_scroll, false);
        scrollsAdjustedStatus.put(R.id.passive_skill_scroll, false);
        scrollsAdjustedStatus.put(R.id.active_skill_scroll, false);

        setContentView(R.layout.card_profile);

        Intent intent = getIntent();
        String base_card_id = intent.getStringExtra("base_card_id");
        current_enhanced_form_card_id = intent.getStringExtra("enhanced_form_card_id");
        current_enhanced_form_type = intent.getStringExtra("enhanced_form_type");
        exz_card_id = intent.getStringExtra("exz_card_id");

        getCardDetails(base_card_id);
        setViewsContent(base_card_id);
        lowerScrollsHeight();

        MainActivity.getTransitionAnimations().fadeOutAnimation(findViewById(R.id.screen_overlay), new Runnable() {
            @Override
            public void run() {
                navigationLocked = false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (!navigationLocked && findViewById(R.id.screen_overlay).getVisibility() == View.GONE) {
            navigationLocked = true;
            super.onBackPressed();
            overridePendingTransition(0, 0);
            MainActivity.getTransitionAnimations().fadeInAnimation(findViewById(R.id.screen_overlay), new Runnable() {
                @Override
                public void run() {
                    navigationLocked = false;
                }
            });
        }
    }

    @Override
    protected void onRestart() {
        navigationLocked = true;
        super.onRestart();
        if (findViewById(R.id.screen_overlay).getVisibility() == View.VISIBLE) {
            MainActivity.getTransitionAnimations().fadeOutAnimation(findViewById(R.id.screen_overlay), new Runnable() {
                @Override
                public void run() {
                    navigationLocked = false;
                }
            });
        } else {
            navigationLocked = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        detectorCompat.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenHeight = displayMetrics.heightPixels;
        float downPositionRatio = e1.getY() / screenHeight;

        if (Math.abs(velocityX) > Math.abs(velocityY) && downPositionRatio > ((ConstraintLayout.LayoutParams) findViewById(R.id.guideline3).getLayoutParams()).guidePercent &&
                downPositionRatio < ((ConstraintLayout.LayoutParams) findViewById(R.id.guideline4).getLayoutParams()).guidePercent) {

            ViewFlipper viewFlipper = findViewById(R.id.contentFlipper);

            if (velocityX > 0) {
                if (viewFlipper.getDisplayedChild() == 1) {
                    viewFlipper.setInAnimation(this, R.anim.right_slide_in);
                    viewFlipper.setOutAnimation(this, R.anim.left_slide_out);
                    viewFlipper.showPrevious();
                }
            } else {
                if (viewFlipper.getDisplayedChild() == 0) {
                    viewFlipper.setInAnimation(this, R.anim.left_slide_in);
                    viewFlipper.setOutAnimation(this, R.anim.right_slide_out);
                    viewFlipper.showNext();
                }
            }
        }
        return true;
    }

    private void getCardDetails(String base_card_id) {
        myDao = AppDatabase.getDatabase(Objects.requireNonNull(this)).myDao();
        System.out.println("base_card_id: " + base_card_id);

        card = myDao.getCardMainDetails(base_card_id);

        if (isExzAwakening() && current_enhanced_form_card_id == null) {
            exz_awakened_card exz_awakened_card = myDao.getExzAwakenedCardDetails(exz_card_id);
            card.setMax_sa_level(exz_awakened_card.getMax_sa_level());
            card.setLeader_skill(exz_awakened_card.getLeader_skill());
            card.setPassive_skill(exz_awakened_card.getPassive_skill());
            card.setMax_hp(exz_awakened_card.getMax_hp());
            card.setMax_atk(exz_awakened_card.getMax_atk());
            card.setMax_def(exz_awakened_card.getMax_def());
        }

        if (current_enhanced_form_card_id != null) {
            card.setCard_id(current_enhanced_form_card_id);
            previousCardFormId = myDao.getPreviousCardForm(current_enhanced_form_card_id);
        }

        if (current_enhanced_form_card_id != null && current_enhanced_form_type != null) {
            System.out.println("current_enhanced_form_card_id: " + current_enhanced_form_card_id);
            if (current_enhanced_form_type.equals("Invincible Form")) {
                invincible_form_card_details invincible_form_card_details = myDao.getInvincibleFormCardDetails(current_enhanced_form_card_id);
                card.setPassive_skill_name(invincible_form_card_details.getPassive_skill_name());
                card.setPassive_skill(invincible_form_card_details.getPassive_skill());
                card.setMax_hp(invincible_form_card_details.getBase_hp());
                card.setMax_atk(invincible_form_card_details.getBase_atk());
                card.setMax_def(invincible_form_card_details.getBase_def());
            } else if (current_enhanced_form_type.equals("Transformation")) {
                transformation_card_exchange_card_details transformation_card_exchange_card_details = myDao.getTransformationCardDetails(current_enhanced_form_card_id);
                card.setCharacter_name(transformation_card_exchange_card_details.getCharacter_name());
                card.setPassive_skill_name(transformation_card_exchange_card_details.getPassive_skill_name());
                card.setPassive_skill(transformation_card_exchange_card_details.getPassive_skill());
            } else if (current_enhanced_form_type.equals("Exchange")) {
                transformation_card_exchange_card_details transformation_card_exchange_card_details = myDao.getExchangeCardDetails(current_enhanced_form_card_id);
                card.setCharacter_name(transformation_card_exchange_card_details.getCharacter_name());
                card.setPassive_skill_name(transformation_card_exchange_card_details.getPassive_skill_name());
                card.setPassive_skill(transformation_card_exchange_card_details.getPassive_skill());
            }

            if (isExzAwakening()) {
                exz_awakened_card exz_awakened_card = myDao.getExzAwakenedCardDetails(exz_card_id);
                card.setMax_sa_level(exz_awakened_card.getMax_sa_level());
                card.setLeader_skill(exz_awakened_card.getLeader_skill());
                card.setPassive_skill(exz_awakened_card.getPassive_skill());
                card.setMax_hp(exz_awakened_card.getMax_hp());
                card.setMax_atk(exz_awakened_card.getMax_atk());
                card.setMax_def(exz_awakened_card.getMax_def());
            }
        }

        active_skill = myDao.getActiveSkill(card.getCard_id());
        if (isExzAwakening()) {
            max_level = myDao.getMaxLevel("Exz_UR");
            super_attacks = myDao.getSuperAttacks(exz_card_id);
        } else {
            max_level = myDao.getMaxLevel(card.getRarity());
            super_attacks = myDao.getSuperAttacks(card.getCard_id());
        }
        link_skills = myDao.getLinkSkills(card.getCard_id());
        categories = myDao.getCategories(card.getCard_id());

        getEnhancedFormDetails();
        getTransformationCondition();

        if (current_enhanced_form_card_id == null) {
            pre_dokkan_details = myDao.getPreDokkanAwakenedCardDetails(base_card_id);
            if (pre_dokkan_details != null) {
                int preDokkanAwakenedCardMedalCombinationId = pre_dokkan_details.getDokkan_awakening_medal_combination_id();
                switch (pre_dokkan_details.getDokkan_awakening_medal_combination_id() / 1000) {
                    case 1:
                        pre_dokkan_medal_tier_1_details = myDao.getTier1MedalCombination(preDokkanAwakenedCardMedalCombinationId);
                        break;
                    case 2:
                        pre_dokkan_medal_tier_2_details = myDao.getTier2MedalCombination(preDokkanAwakenedCardMedalCombinationId);
                        break;
                    case 3:
                        pre_dokkan_medal_tier_3_details = myDao.getTier3MedalCombination(preDokkanAwakenedCardMedalCombinationId);
                        break;
                    case 4:
                        pre_dokkan_medal_tier_4_details = myDao.getTier4MedalCombination(preDokkanAwakenedCardMedalCombinationId);
                        break;
                    case 5:
                        pre_dokkan_medal_tier_5_details = myDao.getTier5MedalCombination(preDokkanAwakenedCardMedalCombinationId);
                        break;
                }
            }

            dokkan_details = myDao.getDokkanAwakenedCardDetails(base_card_id);
            if (dokkan_details != null) {
                int dokkanAwakenedCardMedalCombinationId = dokkan_details.getDokkan_awakening_medal_combination_id();
                switch (dokkanAwakenedCardMedalCombinationId / 1000) {
                    case 1:
                        dokkan_medal_tier_1_details = myDao.getTier1MedalCombination(dokkanAwakenedCardMedalCombinationId);
                        break;
                    case 2:
                        dokkan_medal_tier_2_details = myDao.getTier2MedalCombination(dokkanAwakenedCardMedalCombinationId);
                        break;
                    case 3:
                        dokkan_medal_tier_3_details = myDao.getTier3MedalCombination(dokkanAwakenedCardMedalCombinationId);
                        break;
                    case 4:
                        dokkan_medal_tier_4_details = myDao.getTier4MedalCombination(dokkanAwakenedCardMedalCombinationId);
                        break;
                    case 5:
                        dokkan_medal_tier_5_details = myDao.getTier5MedalCombination(dokkanAwakenedCardMedalCombinationId);
                        break;
                }
            }
        }
    }



    private void setViewsContent(final String base_card_id) {

        ConstraintLayout.LayoutParams params;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        Objects.requireNonNull(this).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        double card_icon_dimension = Math.ceil(displayMetrics.heightPixels * 0.14);

        params = (ConstraintLayout.LayoutParams) findViewById(R.id.card_icon).getLayoutParams();
        params.width = (int) card_icon_dimension;
        params.height = (int) card_icon_dimension;
        findViewById(R.id.card_icon).setLayoutParams(params);
        params = (ConstraintLayout.LayoutParams) findViewById(R.id.card_background).getLayoutParams();
        params.width = (int) Math.ceil(card_icon_dimension * 0.8);
        params.height = (int) Math.ceil(card_icon_dimension * 0.8);
        params.bottomMargin = (int) Math.ceil(card_icon_dimension * 0.06);
        findViewById(R.id.card_background).setLayoutParams(params);
        params = (ConstraintLayout.LayoutParams) findViewById(R.id.card_rarity).getLayoutParams();
        params.width = (int) Math.ceil(card_icon_dimension * 0.44);
        params.height = (int) Math.ceil(card_icon_dimension * 0.49);
        findViewById(R.id.card_rarity).setLayoutParams(params);
        params = (ConstraintLayout.LayoutParams) findViewById(R.id.card_type).getLayoutParams();
        params.width = (int) Math.ceil(card_icon_dimension * 0.35);
        params.height = (int) Math.ceil(card_icon_dimension * 0.35);
        findViewById(R.id.card_type).setLayoutParams(params);

        String card_icon_name = isJapaneseVersion() ? "card_icon_" + card.getCard_id().replace("_jp", "") : "card_icon_" + card.getCard_id();
        String background_icon_name = card.getType().replaceAll("Super|Extreme", "").trim().toLowerCase() + "_background";
        ((ImageView) findViewById(R.id.card_icon)).setImageResource(getResourceId(card_icon_name));
        ((ImageView) findViewById(R.id.card_background)).setImageResource(getResourceId(background_icon_name));
        ((ImageView) findViewById(R.id.card_rarity)).setImageResource(getResourceId(card.getRarity().toLowerCase()));
        ((ImageView) findViewById(R.id.card_type)).setImageResource(getResourceId(card.getType().replace(" ", "_").toLowerCase()));

        ((TextView) findViewById(R.id.card_name_character_name)).setText(String.format("%s\n%s", card.getCard_name(), card.getCharacter_name()));
        ((TextView) findViewById(R.id.leader_skill)).setText(card.getLeader_skill());
        ((TextView) findViewById(R.id.passive_skill_name)).setText(card.getPassive_skill_name());
        ((TextView) findViewById(R.id.passive_skill)).setText(card.getPassive_skill());

        List<Integer> link_name_ids = new ArrayList<>();
        link_name_ids.add(R.id.linkSkill1);
        link_name_ids.add(R.id.linkSkill2);
        link_name_ids.add(R.id.linkSkill3);
        link_name_ids.add(R.id.linkSkill4);
        link_name_ids.add(R.id.linkSkill5);
        link_name_ids.add(R.id.linkSkill6);
        link_name_ids.add(R.id.linkSkill7);

        for (int i = 0; i < link_skills.size(); i++) {
            ((TextView) findViewById(link_name_ids.get(i))).setText(link_skills.get(i).getLink_skill_name());
        }

        List<Integer> category_ids = new ArrayList<>();
        category_ids.add(R.id.category1);
        category_ids.add(R.id.category2);
        category_ids.add(R.id.category3);
        category_ids.add(R.id.category4);
        category_ids.add(R.id.category5);
        category_ids.add(R.id.category6);
        category_ids.add(R.id.category7);
        category_ids.add(R.id.category8);
        category_ids.add(R.id.category9);
        category_ids.add(R.id.category10);

        for (int i = 0; i < categories.size(); i++) {
            ((TextView) findViewById(category_ids.get(i))).setText(categories.get(i).getCategory_name());
        }

        if (active_skill.size() == 1) {
            findViewById(R.id.active_skill_master).setVisibility(View.VISIBLE);
            boolean is_ki_blast = active_skill.get(0).getActive_skill_type().equals("Ki Blast");
            findViewById(R.id.active_skill_ki_blast).setVisibility(is_ki_blast ? View.VISIBLE : View.GONE);
            ((TextView) findViewById(R.id.active_skill_name)).setText(active_skill.get(0).getActive_skill_name());
            ((TextView) findViewById(R.id.active_skill_effect)).setText(String.format("Effect(s): %s", active_skill.get(0).getActive_skill_effect()));
            ((TextView) findViewById(R.id.active_skill_condition)).setText(String.format("Condition(s): %s", active_skill.get(0).getActive_skill_condition()));
        }

        ((TextView) findViewById(R.id.hp)).setText(card.getMax_hp() > 9000000 || card.getMax_hp() == 0 ? "∞" : String.valueOf(card.getMax_hp()));
        params = (ConstraintLayout.LayoutParams) findViewById(R.id.hp_background).getLayoutParams();
        params.width = (int) Math.ceil(displayMetrics.heightPixels * 0.14);
        params.height = (int) Math.ceil(displayMetrics.heightPixels * 0.06);
        findViewById(R.id.hp_background).setLayoutParams(params);

        ((TextView) findViewById(R.id.atk)).setText(String.valueOf(card.getMax_atk()));
        params = (ConstraintLayout.LayoutParams) findViewById(R.id.atk_background).getLayoutParams();
        params.width = (int) Math.ceil(displayMetrics.heightPixels * 0.14);
        params.height = (int) Math.ceil(displayMetrics.heightPixels * 0.06);
        findViewById(R.id.atk_background).setLayoutParams(params);

        ((TextView) findViewById(R.id.def)).setText(card.getMax_def() > 9000000 || card.getMax_def() == 0 ? "∞" : String.valueOf(card.getMax_def()));
        params = (ConstraintLayout.LayoutParams) findViewById(R.id.def_background).getLayoutParams();
        params.width = (int) Math.ceil(displayMetrics.heightPixels * 0.14);
        params.height = (int) Math.ceil(displayMetrics.heightPixels * 0.06);
        findViewById(R.id.def_background).setLayoutParams(params);

        ((TextView) findViewById(R.id.max_level)).setText(String.valueOf(max_level));
        ((TextView) findViewById(R.id.max_sa_level)).setText(String.valueOf(card.getMax_sa_level()));

        String cost = card.getCost();
        cost = cost.contains("/") ? cost.substring(cost.indexOf("/") + 1) : cost;
        ((TextView) findViewById(R.id.cost)).setText(cost);
        ((TextView) findViewById(R.id.twelve_ki_multiplier)).setText(MessageFormat.format("{0}%", card.getTwelve_ki_multiplier()));

        double related_card_icon_dimensions = card_icon_dimension / 2;
        double medal_dimensions = related_card_icon_dimensions * 0.8 * 2 / 3;

        if (pre_dokkan_details != null) {

            params = (ConstraintLayout.LayoutParams) findViewById(R.id.top_row_form_type_icon).getLayoutParams();
            params.width = (int) related_card_icon_dimensions / 2;
            params.height = (int) related_card_icon_dimensions / 2;
            findViewById(R.id.top_row_form_type_icon).setLayoutParams(params);
            params = (ConstraintLayout.LayoutParams) findViewById(R.id.top_row_card_icon).getLayoutParams();
            params.width = (int) related_card_icon_dimensions;
            params.height = (int) related_card_icon_dimensions;
            findViewById(R.id.top_row_card_icon).setLayoutParams(params);
            params = (ConstraintLayout.LayoutParams) findViewById(R.id.top_row_card_background).getLayoutParams();
            params.width = (int) Math.ceil(related_card_icon_dimensions * 0.8);
            params.height = (int) Math.ceil(related_card_icon_dimensions * 0.8);
            params.bottomMargin = (int) Math.ceil(related_card_icon_dimensions * 0.06);
            findViewById(R.id.top_row_card_background).setLayoutParams(params);
            params = (ConstraintLayout.LayoutParams) findViewById(R.id.top_row_card_rarity).getLayoutParams();
            params.width = (int) Math.ceil(related_card_icon_dimensions * 0.44);
            params.height = (int) Math.ceil(related_card_icon_dimensions * 0.49);
            findViewById(R.id.top_row_card_rarity).setLayoutParams(params);
            params = (ConstraintLayout.LayoutParams) findViewById(R.id.top_row_card_type).getLayoutParams();
            params.width = (int) Math.ceil(related_card_icon_dimensions * 0.35);
            params.height = (int) Math.ceil(related_card_icon_dimensions * 0.35);
            findViewById(R.id.top_row_card_type).setLayoutParams(params);

            String preDokkanAwakenedCardId = pre_dokkan_details.getCard_id();
            String top_row_card_icon_name = preDokkanAwakenedCardId.contains("_jp") ? "card_icon_" + preDokkanAwakenedCardId.replace("_jp", "") : "card_icon_" + preDokkanAwakenedCardId;
            ((ImageView) findViewById(R.id.top_row_card_icon)).setImageResource(getResourceId(top_row_card_icon_name));
            findViewById(R.id.top_row_card_icon).setTag(preDokkanAwakenedCardId);
            ((ImageView) findViewById(R.id.top_row_card_background)).setImageResource(getResourceId(background_icon_name));
            ((ImageView) findViewById(R.id.top_row_card_rarity)).setImageResource(getResourceId(pre_dokkan_details.getRarity().toLowerCase()));
            ((ImageView) findViewById(R.id.top_row_card_type)).setImageResource(getResourceId(card.getType().replace(" ", "_").toLowerCase()));

            switch (pre_dokkan_details.getDokkan_awakening_medal_combination_id() / 1000) {
                case 1:
                    ((ImageView) findViewById(R.id.pre_dokkan_medal_1)).setImageResource(getResourceId("medal_" + pre_dokkan_medal_tier_1_details.getMedal_1_id()));
                    params = (ConstraintLayout.LayoutParams) findViewById(R.id.pre_dokkan_medal_1).getLayoutParams();
                    params.width = (int) Math.ceil(medal_dimensions);
                    params.height = (int) Math.ceil(medal_dimensions);
                    findViewById(R.id.pre_dokkan_medal_1).setLayoutParams(params);
                    ((TextView) findViewById(R.id.pre_dokkan_medal_1_count)).setText(String.valueOf(pre_dokkan_medal_tier_1_details.getMedal_1_count()));
                    break;
                case 2:
                    findViewById(R.id.pre_dokkan_medal_2_group).setVisibility(View.VISIBLE);
                    ((ImageView) findViewById(R.id.pre_dokkan_medal_1)).setImageResource(getResourceId("medal_" + pre_dokkan_medal_tier_2_details.getMedal_1_id()));
                    params = (ConstraintLayout.LayoutParams) findViewById(R.id.pre_dokkan_medal_1).getLayoutParams();
                    params.width = (int) Math.ceil(medal_dimensions);
                    params.height = (int) Math.ceil(medal_dimensions);
                    findViewById(R.id.pre_dokkan_medal_1).setLayoutParams(params);
                    ((TextView) findViewById(R.id.pre_dokkan_medal_1_count)).setText(String.valueOf(pre_dokkan_medal_tier_2_details.getMedal_1_count()));
                    ((ImageView) findViewById(R.id.pre_dokkan_medal_2)).setImageResource(getResourceId("medal_" + pre_dokkan_medal_tier_2_details.getMedal_2_id()));
                    params = (ConstraintLayout.LayoutParams) findViewById(R.id.pre_dokkan_medal_2).getLayoutParams();
                    params.width = (int) Math.ceil(medal_dimensions);
                    params.height = (int) Math.ceil(medal_dimensions);
                    findViewById(R.id.pre_dokkan_medal_2).setLayoutParams(params);
                    ((TextView) findViewById(R.id.pre_dokkan_medal_2_count)).setText(String.valueOf(pre_dokkan_medal_tier_2_details.getMedal_2_count()));
                    break;
                case 3:
                    findViewById(R.id.pre_dokkan_medal_2_group).setVisibility(View.VISIBLE);
                    findViewById(R.id.pre_dokkan_medal_3_group).setVisibility(View.VISIBLE);
                    ((ImageView) findViewById(R.id.pre_dokkan_medal_1)).setImageResource(getResourceId("medal_" + pre_dokkan_medal_tier_3_details.getMedal_1_id()));
                    params = (ConstraintLayout.LayoutParams) findViewById(R.id.pre_dokkan_medal_1).getLayoutParams();
                    params.width = (int) Math.ceil(medal_dimensions);
                    params.height = (int) Math.ceil(medal_dimensions);
                    findViewById(R.id.pre_dokkan_medal_1).setLayoutParams(params);
                    ((TextView) findViewById(R.id.pre_dokkan_medal_1_count)).setText(String.valueOf(pre_dokkan_medal_tier_3_details.getMedal_1_count()));
                    ((ImageView) findViewById(R.id.pre_dokkan_medal_2)).setImageResource(getResourceId("medal_" + pre_dokkan_medal_tier_3_details.getMedal_2_id()));
                    params = (ConstraintLayout.LayoutParams) findViewById(R.id.pre_dokkan_medal_2).getLayoutParams();
                    params.width = (int) Math.ceil(medal_dimensions);
                    params.height = (int) Math.ceil(medal_dimensions);
                    findViewById(R.id.pre_dokkan_medal_2).setLayoutParams(params);
                    ((TextView) findViewById(R.id.pre_dokkan_medal_2_count)).setText(String.valueOf(pre_dokkan_medal_tier_3_details.getMedal_2_count()));
                    ((ImageView) findViewById(R.id.pre_dokkan_medal_3)).setImageResource(getResourceId("medal_" + pre_dokkan_medal_tier_3_details.getMedal_3_id()));
                    params = (ConstraintLayout.LayoutParams) findViewById(R.id.pre_dokkan_medal_3).getLayoutParams();
                    params.width = (int) Math.ceil(medal_dimensions);
                    params.height = (int) Math.ceil(medal_dimensions);
                    findViewById(R.id.pre_dokkan_medal_3).setLayoutParams(params);
                    ((TextView) findViewById(R.id.pre_dokkan_medal_3_count)).setText(String.valueOf(pre_dokkan_medal_tier_3_details.getMedal_3_count()));
                    break;
                case 4:
                    findViewById(R.id.pre_dokkan_medal_2_group).setVisibility(View.VISIBLE);
                    findViewById(R.id.pre_dokkan_medal_3_group).setVisibility(View.VISIBLE);
                    findViewById(R.id.pre_dokkan_medal_4_group).setVisibility(View.VISIBLE);
                    ((ImageView) findViewById(R.id.pre_dokkan_medal_1)).setImageResource(getResourceId("medal_" + pre_dokkan_medal_tier_4_details.getMedal_1_id()));
                    params = (ConstraintLayout.LayoutParams) findViewById(R.id.pre_dokkan_medal_1).getLayoutParams();
                    params.width = (int) Math.ceil(medal_dimensions);
                    params.height = (int) Math.ceil(medal_dimensions);
                    findViewById(R.id.pre_dokkan_medal_1).setLayoutParams(params);
                    ((TextView) findViewById(R.id.pre_dokkan_medal_1_count)).setText(String.valueOf(pre_dokkan_medal_tier_4_details.getMedal_1_count()));
                    ((ImageView) findViewById(R.id.pre_dokkan_medal_2)).setImageResource(getResourceId("medal_" + pre_dokkan_medal_tier_4_details.getMedal_2_id()));
                    params = (ConstraintLayout.LayoutParams) findViewById(R.id.pre_dokkan_medal_2).getLayoutParams();
                    params.width = (int) Math.ceil(medal_dimensions);
                    params.height = (int) Math.ceil(medal_dimensions);
                    findViewById(R.id.pre_dokkan_medal_2).setLayoutParams(params);
                    ((TextView) findViewById(R.id.pre_dokkan_medal_2_count)).setText(String.valueOf(pre_dokkan_medal_tier_4_details.getMedal_2_count()));
                    ((ImageView) findViewById(R.id.pre_dokkan_medal_3)).setImageResource(getResourceId("medal_" + pre_dokkan_medal_tier_4_details.getMedal_3_id()));
                    params = (ConstraintLayout.LayoutParams) findViewById(R.id.pre_dokkan_medal_3).getLayoutParams();
                    params.width = (int) Math.ceil(medal_dimensions);
                    params.height = (int) Math.ceil(medal_dimensions);
                    findViewById(R.id.pre_dokkan_medal_3).setLayoutParams(params);
                    ((TextView) findViewById(R.id.pre_dokkan_medal_3_count)).setText(String.valueOf(pre_dokkan_medal_tier_4_details.getMedal_3_count()));
                    ((ImageView) findViewById(R.id.pre_dokkan_medal_4)).setImageResource(getResourceId("medal_" + pre_dokkan_medal_tier_4_details.getMedal_4_id()));
                    params = (ConstraintLayout.LayoutParams) findViewById(R.id.pre_dokkan_medal_4).getLayoutParams();
                    params.width = (int) Math.ceil(medal_dimensions);
                    params.height = (int) Math.ceil(medal_dimensions);
                    findViewById(R.id.pre_dokkan_medal_4).setLayoutParams(params);
                    ((TextView) findViewById(R.id.pre_dokkan_medal_4_count)).setText(String.valueOf(pre_dokkan_medal_tier_4_details.getMedal_4_count()));
                    break;
                case 5:
                    findViewById(R.id.pre_dokkan_medal_2_group).setVisibility(View.VISIBLE);
                    findViewById(R.id.pre_dokkan_medal_3_group).setVisibility(View.VISIBLE);
                    findViewById(R.id.pre_dokkan_medal_4_group).setVisibility(View.VISIBLE);
                    findViewById(R.id.pre_dokkan_medal_5_group).setVisibility(View.VISIBLE);
                    ((ImageView) findViewById(R.id.pre_dokkan_medal_1)).setImageResource(getResourceId("medal_" + pre_dokkan_medal_tier_5_details.getMedal_1_id()));
                    params = (ConstraintLayout.LayoutParams) findViewById(R.id.pre_dokkan_medal_1).getLayoutParams();
                    params.width = (int) Math.ceil(medal_dimensions);
                    params.height = (int) Math.ceil(medal_dimensions);
                    findViewById(R.id.pre_dokkan_medal_1).setLayoutParams(params);
                    ((TextView) findViewById(R.id.pre_dokkan_medal_1_count)).setText(String.valueOf(pre_dokkan_medal_tier_5_details.getMedal_1_count()));
                    ((ImageView) findViewById(R.id.pre_dokkan_medal_2)).setImageResource(getResourceId("medal_" + pre_dokkan_medal_tier_5_details.getMedal_2_id()));
                    params = (ConstraintLayout.LayoutParams) findViewById(R.id.pre_dokkan_medal_2).getLayoutParams();
                    params.width = (int) Math.ceil(medal_dimensions);
                    params.height = (int) Math.ceil(medal_dimensions);
                    findViewById(R.id.pre_dokkan_medal_2).setLayoutParams(params);
                    ((TextView) findViewById(R.id.pre_dokkan_medal_2_count)).setText(String.valueOf(pre_dokkan_medal_tier_5_details.getMedal_2_count()));
                    ((ImageView) findViewById(R.id.pre_dokkan_medal_3)).setImageResource(getResourceId("medal_" + pre_dokkan_medal_tier_5_details.getMedal_3_id()));
                    params = (ConstraintLayout.LayoutParams) findViewById(R.id.pre_dokkan_medal_3).getLayoutParams();
                    params.width = (int) Math.ceil(medal_dimensions);
                    params.height = (int) Math.ceil(medal_dimensions);
                    findViewById(R.id.pre_dokkan_medal_3).setLayoutParams(params);
                    ((TextView) findViewById(R.id.pre_dokkan_medal_3_count)).setText(String.valueOf(pre_dokkan_medal_tier_5_details.getMedal_3_count()));
                    ((ImageView) findViewById(R.id.pre_dokkan_medal_4)).setImageResource(getResourceId("medal_" + pre_dokkan_medal_tier_5_details.getMedal_4_id()));
                    params = (ConstraintLayout.LayoutParams) findViewById(R.id.pre_dokkan_medal_4).getLayoutParams();
                    params.width = (int) Math.ceil(medal_dimensions);
                    params.height = (int) Math.ceil(medal_dimensions);
                    findViewById(R.id.pre_dokkan_medal_4).setLayoutParams(params);
                    ((TextView) findViewById(R.id.pre_dokkan_medal_4_count)).setText(String.valueOf(pre_dokkan_medal_tier_5_details.getMedal_4_count()));
                    ((ImageView) findViewById(R.id.pre_dokkan_medal_5)).setImageResource(getResourceId("medal_" + pre_dokkan_medal_tier_5_details.getMedal_5_id()));
                    params = (ConstraintLayout.LayoutParams) findViewById(R.id.pre_dokkan_medal_5).getLayoutParams();
                    params.width = (int) Math.ceil(medal_dimensions);
                    params.height = (int) Math.ceil(medal_dimensions);
                    findViewById(R.id.pre_dokkan_medal_5).setLayoutParams(params);
                    ((TextView) findViewById(R.id.pre_dokkan_medal_5_count)).setText(String.valueOf(pre_dokkan_medal_tier_5_details.getMedal_5_count()));
                    break;
            }

            if (enhancedFormCardId != null) {

                findViewById(R.id.dokkan_medal_1_group).setVisibility(View.INVISIBLE);

                findViewById(R.id.bottom_transformation_condition_container).setVisibility(View.VISIBLE);
                ((TextView) findViewById(R.id.bottom_transformation_condition)).setText(transformationCondition);

                params = (ConstraintLayout.LayoutParams) findViewById(R.id.bottom_row_form_type_icon).getLayoutParams();
                params.width = (int) related_card_icon_dimensions / 2;
                params.height = (int) related_card_icon_dimensions / 2;
                findViewById(R.id.bottom_row_form_type_icon).setLayoutParams(params);
                params = (ConstraintLayout.LayoutParams) findViewById(R.id.bottom_row_card_icon).getLayoutParams();
                params.width = (int) related_card_icon_dimensions;
                params.height = (int) related_card_icon_dimensions;
                findViewById(R.id.bottom_row_card_icon).setLayoutParams(params);
                params = (ConstraintLayout.LayoutParams) findViewById(R.id.bottom_row_card_background).getLayoutParams();
                params.width = (int) Math.ceil(related_card_icon_dimensions * 0.8);
                params.height = (int) Math.ceil(related_card_icon_dimensions * 0.8);
                params.bottomMargin = (int) Math.ceil(related_card_icon_dimensions * 0.06);
                findViewById(R.id.bottom_row_card_background).setLayoutParams(params);
                params = (ConstraintLayout.LayoutParams) findViewById(R.id.bottom_row_card_rarity).getLayoutParams();
                params.width = (int) Math.ceil(related_card_icon_dimensions * 0.44);
                params.height = (int) Math.ceil(related_card_icon_dimensions * 0.49);
                findViewById(R.id.bottom_row_card_rarity).setLayoutParams(params);
                params = (ConstraintLayout.LayoutParams) findViewById(R.id.bottom_row_card_type).getLayoutParams();
                params.width = (int) Math.ceil(related_card_icon_dimensions * 0.35);
                params.height = (int) Math.ceil(related_card_icon_dimensions * 0.35);
                findViewById(R.id.bottom_row_card_type).setLayoutParams(params);

                String enhanced_form_card_icon_name = enhancedFormCardId.contains("_jp") ? "card_icon_" + enhancedFormCardId.replace("_jp", "") : "card_icon_" + enhancedFormCardId;
                ((ImageView) findViewById(R.id.bottom_row_card_icon)).setImageResource(getResourceId(enhanced_form_card_icon_name));
                findViewById(R.id.bottom_row_card_icon).setTag(null);
                findViewById(R.id.bottom_row_card_icon).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openFormProfile("next", base_card_id);
                    }
                });
                ((ImageView) findViewById(R.id.bottom_row_card_background)).setImageResource(getResourceId(background_icon_name));
                ((ImageView) findViewById(R.id.bottom_row_card_rarity)).setImageResource(getResourceId(card.getRarity().toLowerCase()));

                String enhanced_form_card_type_name = enhancedFormCardId.equals("9012021") ? "extreme_agl" : card.getType().replace(" ", "_").toLowerCase();
                ((ImageView) findViewById(R.id.bottom_row_card_type)).setImageResource(getResourceId(enhanced_form_card_type_name));
            }
        } else if (previousCardFormId != null) {

            findViewById(R.id.pre_dokkan_medal_1_group).setVisibility(View.INVISIBLE);

            findViewById(R.id.top_transformation_condition_container).setVisibility(View.VISIBLE);

            params = (ConstraintLayout.LayoutParams) findViewById(R.id.top_row_form_type_icon).getLayoutParams();
            params.width = (int) related_card_icon_dimensions / 2;
            params.height = (int) related_card_icon_dimensions / 2;
            findViewById(R.id.top_row_form_type_icon).setLayoutParams(params);
            params = (ConstraintLayout.LayoutParams) findViewById(R.id.top_row_card_icon).getLayoutParams();
            params.width = (int) related_card_icon_dimensions;
            params.height = (int) related_card_icon_dimensions;
            findViewById(R.id.top_row_card_icon).setLayoutParams(params);
            params = (ConstraintLayout.LayoutParams) findViewById(R.id.top_row_card_background).getLayoutParams();
            params.width = (int) Math.ceil(related_card_icon_dimensions * 0.8);
            params.height = (int) Math.ceil(related_card_icon_dimensions * 0.8);
            params.bottomMargin = (int) Math.ceil(related_card_icon_dimensions * 0.06);
            findViewById(R.id.top_row_card_background).setLayoutParams(params);
            params = (ConstraintLayout.LayoutParams) findViewById(R.id.top_row_card_rarity).getLayoutParams();
            params.width = (int) Math.ceil(related_card_icon_dimensions * 0.44);
            params.height = (int) Math.ceil(related_card_icon_dimensions * 0.49);
            findViewById(R.id.top_row_card_rarity).setLayoutParams(params);
            params = (ConstraintLayout.LayoutParams) findViewById(R.id.top_row_card_type).getLayoutParams();
            params.width = (int) Math.ceil(related_card_icon_dimensions * 0.35);
            params.height = (int) Math.ceil(related_card_icon_dimensions * 0.35);
            findViewById(R.id.top_row_card_type).setLayoutParams(params);

            String top_row_card_icon_name = previousCardFormId.contains("_jp") ? "card_icon_" + previousCardFormId.replace("_jp", "") : "card_icon_" + previousCardFormId;
            ((ImageView) findViewById(R.id.top_row_card_icon)).setImageResource(getResourceId(top_row_card_icon_name));
            findViewById(R.id.top_row_card_icon).setTag(previousCardFormId);
            findViewById(R.id.top_row_card_icon).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openFormProfile("previous", base_card_id);
                }
            });
            ((ImageView) findViewById(R.id.top_row_card_background)).setImageResource(getResourceId(background_icon_name));
            ((ImageView) findViewById(R.id.top_row_card_rarity)).setImageResource(getResourceId(card.getRarity().toLowerCase()));
            ((ImageView) findViewById(R.id.top_row_card_type)).setImageResource(getResourceId(card.getType().replace(" ", "_").toLowerCase()));
        } else if (enhancedFormCardId == null || dokkan_details == null) {
            findViewById(R.id.top_row_form_type_icon).setVisibility(View.INVISIBLE);
            findViewById(R.id.top_row_card_icon).setVisibility(View.INVISIBLE);
            findViewById(R.id.top_row_card_background).setVisibility(View.INVISIBLE);
            findViewById(R.id.top_row_card_type).setVisibility(View.INVISIBLE);
            findViewById(R.id.top_row_card_rarity).setVisibility(View.INVISIBLE);
            findViewById(R.id.pre_dokkan_medal_1_group).setVisibility(View.INVISIBLE);
        }

        if (dokkan_details != null) {

            params = (ConstraintLayout.LayoutParams) findViewById(R.id.bottom_row_form_type_icon).getLayoutParams();
            params.width = (int) related_card_icon_dimensions / 2;
            params.height = (int) related_card_icon_dimensions / 2;
            findViewById(R.id.bottom_row_form_type_icon).setLayoutParams(params);

            params = (ConstraintLayout.LayoutParams) findViewById(R.id.bottom_row_card_icon).getLayoutParams();
            params.width = (int) related_card_icon_dimensions;
            params.height = (int) related_card_icon_dimensions;
            findViewById(R.id.bottom_row_card_icon).setLayoutParams(params);
            params = (ConstraintLayout.LayoutParams) findViewById(R.id.bottom_row_card_background).getLayoutParams();
            params.width = (int) Math.ceil(related_card_icon_dimensions * 0.8);
            params.height = (int) Math.ceil(related_card_icon_dimensions * 0.8);
            params.bottomMargin = (int) Math.ceil(related_card_icon_dimensions * 0.06);
            findViewById(R.id.bottom_row_card_background).setLayoutParams(params);
            params = (ConstraintLayout.LayoutParams) findViewById(R.id.bottom_row_card_rarity).getLayoutParams();
            params.width = (int) Math.ceil(related_card_icon_dimensions * 0.44);
            params.height = (int) Math.ceil(related_card_icon_dimensions * 0.49);
            findViewById(R.id.bottom_row_card_rarity).setLayoutParams(params);
            params = (ConstraintLayout.LayoutParams) findViewById(R.id.bottom_row_card_type).getLayoutParams();
            params.width = (int) Math.ceil(related_card_icon_dimensions * 0.35);
            params.height = (int) Math.ceil(related_card_icon_dimensions * 0.35);
            findViewById(R.id.bottom_row_card_type).setLayoutParams(params);

            String dokkanAwakenedCardId = dokkan_details.getCard_id();
            String bottom_row_card_icon_name = dokkanAwakenedCardId.contains("_jp") ? "card_icon_" + dokkanAwakenedCardId.replace("_jp", "") : "card_icon_" + dokkanAwakenedCardId;
            ((ImageView) findViewById(R.id.bottom_row_card_icon)).setImageResource(getResourceId(bottom_row_card_icon_name));
            findViewById(R.id.bottom_row_card_icon).setTag(dokkanAwakenedCardId);
            ((ImageView) findViewById(R.id.bottom_row_card_background)).setImageResource(getResourceId(background_icon_name));
            ((ImageView) findViewById(R.id.bottom_row_card_rarity)).setImageResource(getResourceId(dokkan_details.getRarity().toLowerCase()));
            ((ImageView) findViewById(R.id.bottom_row_card_type)).setImageResource(getResourceId(card.getType().replace(" ", "_").toLowerCase()));

            switch (dokkan_details.getDokkan_awakening_medal_combination_id() / 1000) {
                case 1:
                    ((ImageView) findViewById(R.id.dokkan_medal_1)).setImageResource(getResourceId("medal_" + dokkan_medal_tier_1_details.getMedal_1_id()));
                    params = (ConstraintLayout.LayoutParams) findViewById(R.id.dokkan_medal_1).getLayoutParams();
                    params.width = (int) Math.ceil(medal_dimensions);
                    params.height = (int) Math.ceil(medal_dimensions);
                    findViewById(R.id.dokkan_medal_1).setLayoutParams(params);
                    ((TextView) findViewById(R.id.dokkan_medal_1_count)).setText(String.valueOf(dokkan_medal_tier_1_details.getMedal_1_count()));
                    break;
                case 2:
                    findViewById(R.id.dokkan_medal_2_group).setVisibility(View.VISIBLE);
                    ((ImageView) findViewById(R.id.dokkan_medal_1)).setImageResource(getResourceId("medal_" + dokkan_medal_tier_2_details.getMedal_1_id()));
                    params = (ConstraintLayout.LayoutParams) findViewById(R.id.dokkan_medal_1).getLayoutParams();
                    params.width = (int) Math.ceil(medal_dimensions);
                    params.height = (int) Math.ceil(medal_dimensions);
                    findViewById(R.id.dokkan_medal_1).setLayoutParams(params);
                    ((TextView) findViewById(R.id.dokkan_medal_1_count)).setText(String.valueOf(dokkan_medal_tier_2_details.getMedal_1_count()));
                    ((ImageView) findViewById(R.id.dokkan_medal_2)).setImageResource(getResourceId("medal_" + dokkan_medal_tier_2_details.getMedal_2_id()));
                    params = (ConstraintLayout.LayoutParams) findViewById(R.id.dokkan_medal_2).getLayoutParams();
                    params.width = (int) Math.ceil(medal_dimensions);
                    params.height = (int) Math.ceil(medal_dimensions);
                    findViewById(R.id.dokkan_medal_2).setLayoutParams(params);
                    ((TextView) findViewById(R.id.dokkan_medal_2_count)).setText(String.valueOf(dokkan_medal_tier_2_details.getMedal_2_count()));
                    break;
                case 3:
                    findViewById(R.id.dokkan_medal_2_group).setVisibility(View.VISIBLE);
                    findViewById(R.id.dokkan_medal_3_group).setVisibility(View.VISIBLE);
                    ((ImageView) findViewById(R.id.dokkan_medal_1)).setImageResource(getResourceId("medal_" + dokkan_medal_tier_3_details.getMedal_1_id()));
                    params = (ConstraintLayout.LayoutParams) findViewById(R.id.dokkan_medal_1).getLayoutParams();
                    params.width = (int) Math.ceil(medal_dimensions);
                    params.height = (int) Math.ceil(medal_dimensions);
                    findViewById(R.id.dokkan_medal_1).setLayoutParams(params);
                    ((TextView) findViewById(R.id.dokkan_medal_1_count)).setText(String.valueOf(dokkan_medal_tier_3_details.getMedal_1_count()));
                    ((ImageView) findViewById(R.id.dokkan_medal_2)).setImageResource(getResourceId("medal_" + dokkan_medal_tier_3_details.getMedal_2_id()));
                    params = (ConstraintLayout.LayoutParams) findViewById(R.id.dokkan_medal_2).getLayoutParams();
                    params.width = (int) Math.ceil(medal_dimensions);
                    params.height = (int) Math.ceil(medal_dimensions);
                    findViewById(R.id.dokkan_medal_2).setLayoutParams(params);
                    ((TextView) findViewById(R.id.dokkan_medal_2_count)).setText(String.valueOf(dokkan_medal_tier_3_details.getMedal_2_count()));
                    ((ImageView) findViewById(R.id.dokkan_medal_3)).setImageResource(getResourceId("medal_" + dokkan_medal_tier_3_details.getMedal_3_id()));
                    params = (ConstraintLayout.LayoutParams) findViewById(R.id.dokkan_medal_3).getLayoutParams();
                    params.width = (int) Math.ceil(medal_dimensions);
                    params.height = (int) Math.ceil(medal_dimensions);
                    findViewById(R.id.dokkan_medal_3).setLayoutParams(params);
                    ((TextView) findViewById(R.id.dokkan_medal_3_count)).setText(String.valueOf(dokkan_medal_tier_3_details.getMedal_3_count()));
                    break;
                case 4:
                    findViewById(R.id.dokkan_medal_2_group).setVisibility(View.VISIBLE);
                    findViewById(R.id.dokkan_medal_3_group).setVisibility(View.VISIBLE);
                    findViewById(R.id.dokkan_medal_4_group).setVisibility(View.VISIBLE);
                    ((ImageView) findViewById(R.id.dokkan_medal_1)).setImageResource(getResourceId("medal_" + dokkan_medal_tier_4_details.getMedal_1_id()));
                    params = (ConstraintLayout.LayoutParams) findViewById(R.id.dokkan_medal_1).getLayoutParams();
                    params.width = (int) Math.ceil(medal_dimensions);
                    params.height = (int) Math.ceil(medal_dimensions);
                    findViewById(R.id.dokkan_medal_1).setLayoutParams(params);
                    ((TextView) findViewById(R.id.dokkan_medal_1_count)).setText(String.valueOf(dokkan_medal_tier_4_details.getMedal_1_count()));
                    ((ImageView) findViewById(R.id.dokkan_medal_2)).setImageResource(getResourceId("medal_" + dokkan_medal_tier_4_details.getMedal_2_id()));
                    params = (ConstraintLayout.LayoutParams) findViewById(R.id.dokkan_medal_2).getLayoutParams();
                    params.width = (int) Math.ceil(medal_dimensions);
                    params.height = (int) Math.ceil(medal_dimensions);
                    findViewById(R.id.dokkan_medal_2).setLayoutParams(params);
                    ((TextView) findViewById(R.id.dokkan_medal_2_count)).setText(String.valueOf(dokkan_medal_tier_4_details.getMedal_2_count()));
                    ((ImageView) findViewById(R.id.dokkan_medal_3)).setImageResource(getResourceId("medal_" + dokkan_medal_tier_4_details.getMedal_3_id()));
                    params = (ConstraintLayout.LayoutParams) findViewById(R.id.dokkan_medal_3).getLayoutParams();
                    params.width = (int) Math.ceil(medal_dimensions);
                    params.height = (int) Math.ceil(medal_dimensions);
                    findViewById(R.id.dokkan_medal_3).setLayoutParams(params);
                    ((TextView) findViewById(R.id.dokkan_medal_3_count)).setText(String.valueOf(dokkan_medal_tier_4_details.getMedal_3_count()));
                    ((ImageView) findViewById(R.id.dokkan_medal_4)).setImageResource(getResourceId("medal_" + dokkan_medal_tier_4_details.getMedal_4_id()));
                    params = (ConstraintLayout.LayoutParams) findViewById(R.id.dokkan_medal_4).getLayoutParams();
                    params.width = (int) Math.ceil(medal_dimensions);
                    params.height = (int) Math.ceil(medal_dimensions);
                    findViewById(R.id.dokkan_medal_4).setLayoutParams(params);
                    ((TextView) findViewById(R.id.dokkan_medal_4_count)).setText(String.valueOf(dokkan_medal_tier_4_details.getMedal_4_count()));
                    break;
                case 5:
                    findViewById(R.id.dokkan_medal_2_group).setVisibility(View.VISIBLE);
                    findViewById(R.id.dokkan_medal_3_group).setVisibility(View.VISIBLE);
                    findViewById(R.id.dokkan_medal_4_group).setVisibility(View.VISIBLE);
                    findViewById(R.id.dokkan_medal_5_group).setVisibility(View.VISIBLE);
                    ((ImageView) findViewById(R.id.dokkan_medal_1)).setImageResource(getResourceId("medal_" + dokkan_medal_tier_5_details.getMedal_1_id()));
                    params = (ConstraintLayout.LayoutParams) findViewById(R.id.dokkan_medal_1).getLayoutParams();
                    params.width = (int) Math.ceil(medal_dimensions);
                    params.height = (int) Math.ceil(medal_dimensions);
                    findViewById(R.id.dokkan_medal_1).setLayoutParams(params);
                    ((TextView) findViewById(R.id.dokkan_medal_1_count)).setText(String.valueOf(dokkan_medal_tier_5_details.getMedal_1_count()));
                    ((ImageView) findViewById(R.id.dokkan_medal_2)).setImageResource(getResourceId("medal_" + dokkan_medal_tier_5_details.getMedal_2_id()));
                    params = (ConstraintLayout.LayoutParams) findViewById(R.id.dokkan_medal_2).getLayoutParams();
                    params.width = (int) Math.ceil(medal_dimensions);
                    params.height = (int) Math.ceil(medal_dimensions);
                    findViewById(R.id.dokkan_medal_2).setLayoutParams(params);
                    ((TextView) findViewById(R.id.dokkan_medal_2_count)).setText(String.valueOf(dokkan_medal_tier_5_details.getMedal_2_count()));
                    ((ImageView) findViewById(R.id.dokkan_medal_3)).setImageResource(getResourceId("medal_" + dokkan_medal_tier_5_details.getMedal_3_id()));
                    params = (ConstraintLayout.LayoutParams) findViewById(R.id.dokkan_medal_3).getLayoutParams();
                    params.width = (int) Math.ceil(medal_dimensions);
                    params.height = (int) Math.ceil(medal_dimensions);
                    findViewById(R.id.dokkan_medal_3).setLayoutParams(params);
                    ((TextView) findViewById(R.id.dokkan_medal_3_count)).setText(String.valueOf(dokkan_medal_tier_5_details.getMedal_3_count()));
                    ((ImageView) findViewById(R.id.dokkan_medal_4)).setImageResource(getResourceId("medal_" + dokkan_medal_tier_5_details.getMedal_4_id()));
                    params = (ConstraintLayout.LayoutParams) findViewById(R.id.dokkan_medal_4).getLayoutParams();
                    params.width = (int) Math.ceil(medal_dimensions);
                    params.height = (int) Math.ceil(medal_dimensions);
                    findViewById(R.id.dokkan_medal_4).setLayoutParams(params);
                    ((TextView) findViewById(R.id.dokkan_medal_4_count)).setText(String.valueOf(dokkan_medal_tier_5_details.getMedal_4_count()));
                    ((ImageView) findViewById(R.id.dokkan_medal_5)).setImageResource(getResourceId("medal_" + dokkan_medal_tier_5_details.getMedal_5_id()));
                    params = (ConstraintLayout.LayoutParams) findViewById(R.id.dokkan_medal_5).getLayoutParams();
                    params.width = (int) Math.ceil(medal_dimensions);
                    params.height = (int) Math.ceil(medal_dimensions);
                    findViewById(R.id.dokkan_medal_5).setLayoutParams(params);
                    ((TextView) findViewById(R.id.dokkan_medal_5_count)).setText(String.valueOf(dokkan_medal_tier_5_details.getMedal_5_count()));
                    break;
            }

            if (enhancedFormCardId != null) {

                findViewById(R.id.pre_dokkan_medal_1_group).setVisibility(View.INVISIBLE);

                findViewById(R.id.top_transformation_condition_container).setVisibility(View.VISIBLE);
                ((TextView) findViewById(R.id.top_transformation_condition)).setText(transformationCondition);

                params = (ConstraintLayout.LayoutParams) findViewById(R.id.top_row_form_type_icon).getLayoutParams();
                params.width = (int) related_card_icon_dimensions / 2;
                params.height = (int) related_card_icon_dimensions / 2;
                findViewById(R.id.top_row_form_type_icon).setLayoutParams(params);
                params = (ConstraintLayout.LayoutParams) findViewById(R.id.top_row_card_icon).getLayoutParams();
                params.width = (int) related_card_icon_dimensions;
                params.height = (int) related_card_icon_dimensions;
                findViewById(R.id.top_row_card_icon).setLayoutParams(params);
                params = (ConstraintLayout.LayoutParams) findViewById(R.id.top_row_card_background).getLayoutParams();
                params.width = (int) Math.ceil(related_card_icon_dimensions * 0.8);
                params.height = (int) Math.ceil(related_card_icon_dimensions * 0.8);
                params.bottomMargin = (int) Math.ceil(related_card_icon_dimensions * 0.06);
                findViewById(R.id.top_row_card_background).setLayoutParams(params);
                params = (ConstraintLayout.LayoutParams) findViewById(R.id.top_row_card_rarity).getLayoutParams();
                params.width = (int) Math.ceil(related_card_icon_dimensions * 0.44);
                params.height = (int) Math.ceil(related_card_icon_dimensions * 0.49);
                findViewById(R.id.top_row_card_rarity).setLayoutParams(params);
                params = (ConstraintLayout.LayoutParams) findViewById(R.id.top_row_card_type).getLayoutParams();
                params.width = (int) Math.ceil(related_card_icon_dimensions * 0.35);
                params.height = (int) Math.ceil(related_card_icon_dimensions * 0.35);
                findViewById(R.id.top_row_card_type).setLayoutParams(params);

                String enhanced_form_card_icon_name = enhancedFormCardId.contains("_jp)") ? "card_icon_" + enhancedFormCardId.replace("_jp", "") : "card_icon_" + enhancedFormCardId;
                ((ImageView) findViewById(R.id.top_row_card_icon)).setImageResource(getResourceId(enhanced_form_card_icon_name));
                findViewById(R.id.top_row_card_icon).setTag(null);
                findViewById(R.id.top_row_card_icon).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openFormProfile("next", base_card_id);
                    }
                });
                ((ImageView) findViewById(R.id.top_row_card_background)).setImageResource(getResourceId(background_icon_name));
                ((ImageView) findViewById(R.id.top_row_card_rarity)).setImageResource(getResourceId(card.getRarity().toLowerCase()));

                String enhanced_form_card_type_name = enhancedFormCardId.equals("9012021") ? "extreme_agl" : card.getType().replace(" ", "_").toLowerCase();
                ((ImageView) findViewById(R.id.top_row_card_type)).setImageResource(getResourceId(enhanced_form_card_type_name));
            }
        } else if (enhancedFormCardId != null) {

            findViewById(R.id.dokkan_medal_1_group).setVisibility(View.INVISIBLE);

            findViewById(R.id.bottom_transformation_condition_container).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.bottom_transformation_condition)).setText(transformationCondition);

            params = (ConstraintLayout.LayoutParams) findViewById(R.id.bottom_row_form_type_icon).getLayoutParams();
            params.width = (int) related_card_icon_dimensions / 2;
            params.height = (int) related_card_icon_dimensions / 2;
            findViewById(R.id.bottom_row_form_type_icon).setLayoutParams(params);
            params = (ConstraintLayout.LayoutParams) findViewById(R.id.bottom_row_card_icon).getLayoutParams();
            params.width = (int) related_card_icon_dimensions;
            params.height = (int) related_card_icon_dimensions;
            findViewById(R.id.bottom_row_card_icon).setLayoutParams(params);
            params = (ConstraintLayout.LayoutParams) findViewById(R.id.bottom_row_card_background).getLayoutParams();
            params.width = (int) Math.ceil(related_card_icon_dimensions * 0.8);
            params.height = (int) Math.ceil(related_card_icon_dimensions * 0.8);
            params.bottomMargin = (int) Math.ceil(related_card_icon_dimensions * 0.06);
            findViewById(R.id.bottom_row_card_background).setLayoutParams(params);
            params = (ConstraintLayout.LayoutParams) findViewById(R.id.bottom_row_card_rarity).getLayoutParams();
            params.width = (int) Math.ceil(related_card_icon_dimensions * 0.44);
            params.height = (int) Math.ceil(related_card_icon_dimensions * 0.49);
            findViewById(R.id.bottom_row_card_rarity).setLayoutParams(params);
            params = (ConstraintLayout.LayoutParams) findViewById(R.id.bottom_row_card_type).getLayoutParams();
            params.width = (int) Math.ceil(related_card_icon_dimensions * 0.35);
            params.height = (int) Math.ceil(related_card_icon_dimensions * 0.35);
            findViewById(R.id.bottom_row_card_type).setLayoutParams(params);

            String bottom_row_card_icon_name = enhancedFormCardId.contains("_jp") ? "card_icon_" + enhancedFormCardId.replace("_jp", "") : "card_icon_" + enhancedFormCardId;
            ((ImageView) findViewById(R.id.bottom_row_card_icon)).setImageResource(getResourceId(bottom_row_card_icon_name));
            findViewById(R.id.bottom_row_card_icon).setTag(enhancedFormCardId);
            findViewById(R.id.bottom_row_card_icon).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openFormProfile("next", base_card_id);
                }
            });
            ((ImageView) findViewById(R.id.bottom_row_card_background)).setImageResource(getResourceId(background_icon_name));
            ((ImageView) findViewById(R.id.bottom_row_card_rarity)).setImageResource(getResourceId(card.getRarity().toLowerCase()));
            ((ImageView) findViewById(R.id.bottom_row_card_type)).setImageResource(getResourceId(card.getType().replace(" ", "_").toLowerCase()));
        } else {
            findViewById(R.id.bottom_row_form_type_icon).setVisibility(View.INVISIBLE);
            findViewById(R.id.bottom_row_card_icon).setVisibility(View.INVISIBLE);
            findViewById(R.id.bottom_row_card_background).setVisibility(View.INVISIBLE);
            findViewById(R.id.bottom_row_card_type).setVisibility(View.INVISIBLE);
            findViewById(R.id.bottom_row_card_rarity).setVisibility(View.INVISIBLE);
            findViewById(R.id.dokkan_medal_1_group).setVisibility(View.INVISIBLE);
        }

        View twelve_ki_multiplier_label = findViewById(R.id.twelve_ki_multiplier_label);
        twelve_ki_multiplier_label.measure(0, 0);
        View twelve_ki_multiplier = findViewById(R.id.twelve_ki_multiplier);
        twelve_ki_multiplier.measure(0, 0);
        params = (ConstraintLayout.LayoutParams) findViewById(R.id.generic_stats_container).getLayoutParams();
        params.width = twelve_ki_multiplier_label.getMeasuredWidth() + twelve_ki_multiplier.getMeasuredWidth() + getDp(10);
        params.height = twelve_ki_multiplier_label.getMeasuredHeight() * 4 + getDp(10);
        findViewById(R.id.generic_stats_container).setLayoutParams(params);

        params = (ConstraintLayout.LayoutParams) findViewById(R.id.form_tree_container).getLayoutParams();
        params.width = (int) Math.ceil(related_card_icon_dimensions * 1.5 + medal_dimensions * 5 + 64);
        params.height = (int) Math.ceil(related_card_icon_dimensions * 2 + 6);
        findViewById(R.id.form_tree_container).setLayoutParams(params);

        super_attack_extra_effects = new ArrayList<>();
        myDao = AppDatabase.getDatabase(Objects.requireNonNull(this)).myDao();

        if (super_attacks.size() > 0) {
            ((TextView) findViewById(R.id.super_attack_1_name)).setText(super_attacks.get(0).getSuper_attack_name());
            ((TextView) findViewById(R.id.super_attack_1_effect)).setText(super_attacks.get(0).getSuper_attack_effect());
            if (super_attacks.get(0).getSuper_attack_type().equals("Melee")) {
                findViewById(R.id.super_attack_1_ki_blast).setVisibility(View.GONE);
            }

            super_attack_extra_effect super_attack_extra_effect = myDao.getSuperAttackExtraEffect(super_attacks.get(0).getSuper_attack_id());
            if (super_attack_extra_effect != null) {
                super_attack_extra_effects.add(super_attack_extra_effect);
                findViewById(R.id.super_attack_1_extra_effect).setTag(super_attack_extra_effects.size());
                findViewById(R.id.super_attack_1_extra_effect).setVisibility(View.VISIBLE);
            }
        }

        if (super_attacks.size() > 1) {

            findViewById(R.id.super_attack_2_name).setVisibility(View.VISIBLE);
            findViewById(R.id.super_attack_2_effect).setVisibility(View.VISIBLE);

            ((TextView) findViewById(R.id.super_attack_2_name)).setText(super_attacks.get(1).getSuper_attack_name());
            ((TextView) findViewById(R.id.super_attack_2_effect)).setText(super_attacks.get(1).getSuper_attack_effect());
            if (super_attacks.get(1).getSuper_attack_type().equals("Melee")) {
                findViewById(R.id.super_attack_2_ki_blast).setVisibility(View.GONE);
            }

            super_attack_extra_effect super_attack_extra_effect = myDao.getSuperAttackExtraEffect(super_attacks.get(1).getSuper_attack_id());
            if (super_attack_extra_effect != null) {
                super_attack_extra_effects.add(super_attack_extra_effect);
                findViewById(R.id.super_attack_2_extra_effect).setTag(super_attack_extra_effects.size());
                findViewById(R.id.super_attack_2_extra_effect).setVisibility(View.VISIBLE);
            }
        } else {
            findViewById(R.id.super_attack_2_name).setVisibility(View.GONE);
            findViewById(R.id.super_attack_2_effect).setVisibility(View.GONE);
            findViewById(R.id.super_attack_2_ki_blast).setVisibility(View.GONE);
            findViewById(R.id.super_attack_2_extra_effect).setVisibility(View.GONE);
        }

        if (super_attacks.size() > 2) {

            findViewById(R.id.super_attack_3_name).setVisibility(View.VISIBLE);
            findViewById(R.id.super_attack_3_effect).setVisibility(View.VISIBLE);

            ((TextView) findViewById(R.id.super_attack_3_name)).setText(super_attacks.get(2).getSuper_attack_name());
            ((TextView) findViewById(R.id.super_attack_3_effect)).setText(super_attacks.get(2).getSuper_attack_effect());
            if (super_attacks.get(2).getSuper_attack_type().equals("Melee")) {
                findViewById(R.id.super_attack_3_ki_blast).setVisibility(View.GONE);
            }

            super_attack_extra_effect super_attack_extra_effect = myDao.getSuperAttackExtraEffect(super_attacks.get(2).getSuper_attack_id());
            if (super_attack_extra_effect != null) {
                super_attack_extra_effects.add(super_attack_extra_effect);
                findViewById(R.id.super_attack_3_extra_effect).setTag(super_attack_extra_effects.size());
                findViewById(R.id.super_attack_3_extra_effect).setVisibility(View.VISIBLE);
            }

        } else {
            findViewById(R.id.super_attack_3_name).setVisibility(View.GONE);
            findViewById(R.id.super_attack_3_effect).setVisibility(View.GONE);
            findViewById(R.id.super_attack_3_ki_blast).setVisibility(View.GONE);
            findViewById(R.id.super_attack_3_extra_effect).setVisibility(View.GONE);
        }

        params = (ConstraintLayout.LayoutParams) findViewById(R.id.ki_meter).getLayoutParams();
        params.height = (int) Math.ceil(related_card_icon_dimensions / 2);
        findViewById(R.id.ki_meter).setLayoutParams(params);
        ((ImageView) findViewById(R.id.ki_meter)).setImageResource(getResourceId(getKiMeterFileName()));

        params = (ConstraintLayout.LayoutParams) findViewById(R.id.super_attack_details_ki_meter).getLayoutParams();
        params.height = (int) Math.ceil(related_card_icon_dimensions / 2.5);
        findViewById(R.id.super_attack_details_ki_meter).setLayoutParams(params);
        ((ImageView) findViewById(R.id.super_attack_details_ki_meter)).setImageResource(getResourceId(getKiMeterFileName()));

        params = (ConstraintLayout.LayoutParams) findViewById(R.id.super_attack_details_panel).getLayoutParams();
        params.height = (int) Math.ceil(displayMetrics.heightPixels * 0.3);
        params.width = (int) Math.ceil(displayMetrics.widthPixels * 0.85);
        findViewById(R.id.super_attack_details_panel).setLayoutParams(params);

        findViewById(R.id.screen_overlay).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        if (isOrHasExzAwakening(base_card_id)) {
            findViewById(R.id.exz_awakening).setVisibility(View.VISIBLE);

            if (isExzAwakening()) {
                findViewById(R.id.exz_awakening_icon).setVisibility(View.VISIBLE);
                findViewById(R.id.exz_awakening).setTag(myDao.getPreExzAwakenedCardId(exz_card_id));
            } else {
                System.out.println("exz: " + myDao.getExzAwakenedCardId(card.getCard_id()));
                findViewById(R.id.exz_awakening).setTag(myDao.getExzAwakenedCardId(card.getCard_id()));
            }
        }

        if (isOrHasJapaneseVersion()) {
            findViewById(R.id.origin_icon).setVisibility(View.VISIBLE);
            findViewById(R.id.origin_flag).setVisibility(View.VISIBLE);

            if (card.getCard_id().contains("_jp")) {
                findViewById(R.id.origin_flag).setTag(card.getCard_id().replace("_jp", ""));
                ((ImageView) findViewById(R.id.origin_flag)).setImageResource(getResourceId("global_flag"));
                ((ImageView) findViewById(R.id.origin_icon)).setImageResource(getResourceId("japanese_flag"));
            } else {
                findViewById(R.id.origin_flag).setTag(card.getCard_id() + "_jp");
            }
        }
    }

    public int getResourceId(@NonNull String pVariableName) {
        try {
            return getResources().getIdentifier(pVariableName, "drawable", Objects.requireNonNull(this).getPackageName());
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static void updateScrollStatus(int scrollId) {
        scrollsAdjustedStatus.append(scrollId, true);
    }

    public void lowerScrollsHeight() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!(scrollsAdjustedStatus.get(R.id.super_attacks_scroll) && scrollsAdjustedStatus.get(R.id.passive_skill_scroll) &&
                        (scrollsAdjustedStatus.get(R.id.active_skill_scroll) || findViewById(R.id.active_skill_master).getVisibility() == View.GONE))) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        resizeScrollHeights(R.id.super_attacks_scroll);
                        resizeScrollHeights(R.id.passive_skill_scroll);
                        resizeScrollHeights(R.id.active_skill_scroll);
                        addSpaceForSuperAttacksHeader();
                        if (getFreeHeightOfMainFlipperViews(true) == 0) {
                            lowerScrollHeight(R.id.super_attacks_scroll);
                            lowerScrollHeight(R.id.passive_skill_scroll);
                            lowerScrollHeight(R.id.active_skill_scroll);
                        }
                        resizeMasterLayout(R.id.super_attack_master);
                        resizeMasterLayout(R.id.passive_skill_master);
                        resizeMasterLayout(R.id.active_skill_master);
                        setScrollable(R.id.super_attacks_scroll);
                        setScrollable(R.id.passive_skill_scroll);
                        setScrollable(R.id.active_skill_scroll);
                    }
                });
            }
        }).start();
    }

    public void lowerScrollHeight(int layoutId) {

        ViewGroup.LayoutParams params = findViewById(layoutId).getLayoutParams();
        params.height -= getDp(5);
        findViewById(layoutId).setLayoutParams(params);
    }

    public int getDp(int value) {
        Resources r = getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, r.getDisplayMetrics());
    }

    public void resizeScrollHeights(int scroll_id) {

        LimitedScrollView scroll = findViewById(scroll_id);

        int scroll_height = scroll.getHeight();

        View container = ((LimitedScrollView) findViewById(scroll_id)).getChildAt(0);
        int container_height = container.getHeight();

        ConstraintLayout.LayoutParams scroll_params = (ConstraintLayout.LayoutParams) scroll.getLayoutParams();

        if (container_height > scroll_height) {
            if ((container_height - scroll_height) >= getFreeHeightOfMainFlipperViews(true)) {
                scroll_params.height += extraSpace;
                extraSpace = 0;
            } else {
                scroll_params.height = container_height;
                extraSpace -= container_height - scroll_height;
            }
            findViewById(scroll_id).setLayoutParams(scroll_params);
        }
    }

    public void addSpaceForSuperAttacksHeader() {
        int superAttackLabelHeight = findViewById(R.id.super_attack_label).getHeight() + findViewById(R.id.passive_skill_label).getHeight() + findViewById(R.id.active_skill_label).getHeight();
        if (superAttackLabelHeight > getFreeHeightOfMainFlipperViews(false)) {
            int requiredSpacePerScroll = (superAttackLabelHeight - getFreeHeightOfMainFlipperViews(false)) / 3;

            ViewGroup.LayoutParams params = findViewById(R.id.super_attacks_scroll).getLayoutParams();
            params.height -= requiredSpacePerScroll;
            findViewById(R.id.super_attacks_scroll).setLayoutParams(params);
            params = findViewById(R.id.passive_skill_scroll).getLayoutParams();
            params.height -= requiredSpacePerScroll;
            findViewById(R.id.passive_skill_scroll).setLayoutParams(params);
            params = findViewById(R.id.active_skill_scroll).getLayoutParams();
            params.height -= requiredSpacePerScroll;
            findViewById(R.id.active_skill_scroll).setLayoutParams(params);

            extraSpace = 0;
        } else {
            extraSpace -= superAttackLabelHeight;
        }
    }

    public int getFreeHeightOfMainFlipperViews(boolean headersIncluded) {

        int result = findViewById(R.id.contentFlipper).getHeight() - findViewById(R.id.super_attacks_scroll).getHeight() -
                findViewById(R.id.passive_skill_scroll).getHeight() - findViewById(R.id.active_skill_scroll).getHeight();

        if (headersIncluded) {
            result -= findViewById(R.id.super_attack_label).getHeight() + findViewById(R.id.passive_skill_label).getHeight() + findViewById(R.id.active_skill_label).getHeight();
        }

        return result;
    }

    public void setScrollable(int layoutId) {
        LimitedScrollView limitedScrollView = findViewById(layoutId);
        if (limitedScrollView.getChildAt(0).getHeight() <= limitedScrollView.getLayoutParams().height) {
            ((LimitedScrollView) findViewById(layoutId)).setScrollable(false);
        }
    }

    void resizeMasterLayout(int layoutId) {

        ViewGroup masterLayout = findViewById(layoutId);
        int childCount = masterLayout.getChildCount();

        ViewGroup.LayoutParams params = findViewById(layoutId).getLayoutParams();
        params.height = masterLayout.getChildAt(0).getLayoutParams().height + masterLayout.getChildAt(childCount - 1).getLayoutParams().height;
        findViewById(layoutId).setLayoutParams(params);
    }

    public void openProfile(View view) {

        if (!navigationLocked) {
            navigationLocked = true;
            final Intent myIntent = new Intent(this, CardProfile.class);
            myIntent.addFlags(FLAG_ACTIVITY_NEW_TASK);
            myIntent.addFlags(FLAG_ACTIVITY_NO_ANIMATION);
            myIntent.putExtra("base_card_id", view.getTag().toString());

            MainActivity.getTransitionAnimations().fadeInAnimation(findViewById(R.id.screen_overlay), new Runnable() {
                @Override
                public void run() {
                    Objects.requireNonNull(getApplicationContext()).startActivity(myIntent);
                    navigationLocked = false;
                }
            });
        }
    }

    public void openExzProfile(View view) {

        if (!navigationLocked) {
            navigationLocked = true;
            final Intent myIntent = new Intent(this, CardProfile.class);
            myIntent.addFlags(FLAG_ACTIVITY_NEW_TASK);
            myIntent.addFlags(FLAG_ACTIVITY_NO_ANIMATION);

            String preExzCardId = null;

            if (isExzAwakening()) {
                preExzCardId = view.getTag().toString();
                myIntent.putExtra("base_card_id", card.getCard_id());
            } else {
                preExzCardId = myDao.getPreExzAwakenedCardId(view.getTag().toString());
                myIntent.putExtra("exz_card_id", view.getTag().toString());
            }

            String previousFormCardId = myDao.getPreviousCardForm(preExzCardId);
            if (previousFormCardId == null) {
                myIntent.putExtra("base_card_id", preExzCardId);
            } else {
                myIntent.putExtra("base_card_id", previousFormCardId);
            }

            if (current_enhanced_form_card_id != null) {
                myIntent.putExtra("enhanced_form_card_id", current_enhanced_form_card_id);
                myIntent.putExtra("enhanced_form_type", current_enhanced_form_type);
            }

            MainActivity.getTransitionAnimations().fadeInAnimation(findViewById(R.id.screen_overlay), new Runnable() {
                @Override
                public void run() {
                    Objects.requireNonNull(getApplicationContext()).startActivity(myIntent);
                    navigationLocked = false;
                }
            });
        }
    }

    public void openFormProfile(String formState, String base_card_id) {

        if (!navigationLocked) {
            navigationLocked = true;
            final Intent myIntent = new Intent(this, CardProfile.class);
            myIntent.addFlags(FLAG_ACTIVITY_NEW_TASK);
            myIntent.addFlags(FLAG_ACTIVITY_NO_ANIMATION);

            String enhanced_form_card_id = null;
            String enhanced_form_type = null;
            if (formState.equals("next")) {
                enhanced_form_card_id = enhancedFormCardId;
                enhanced_form_type = enhancedFormType;
            } else if (formState.equals("previous") && !previousCardFormId.equals(base_card_id)) {
                enhanced_form_card_id = previousCardFormId;
            }

            myIntent.putExtra("base_card_id", base_card_id);
            myIntent.putExtra("enhanced_form_card_id", enhanced_form_card_id);
            myIntent.putExtra("enhanced_form_type", enhanced_form_type);

            MainActivity.getTransitionAnimations().fadeInAnimation(findViewById(R.id.screen_overlay), new Runnable() {
                @Override
                public void run() {
                    Objects.requireNonNull(getApplicationContext()).startActivity(myIntent);
                    navigationLocked = false;
                }
            });
        }
    }

    public void openExzProfile() {

    }

    public String getKiMeterFileName() {

        List<Integer> super_attack_ki = new ArrayList<>();
        for (super_attack super_attack : super_attacks) {
            super_attack_ki.add(Integer.valueOf(super_attack.getSuper_attack_launch_condition()));
        }

        String kiMeterFileName;

        if (card.getRarity().equals("LR")) {
            kiMeterFileName = "ki_meter_24_" + card.getGreen_ki() + "_" + Collections.max(super_attack_ki);
        } else {
            kiMeterFileName = "ki_meter_12_" + card.getGreen_ki() + "_" + Collections.min(super_attack_ki);
        }

        return kiMeterFileName;
    }

    public void getEnhancedFormDetails() {
        String invincibleFormCardId = myDao.getInvincibleFormCardId(card.getCard_id());
        String transformationCardId = myDao.getTransformationCardId(card.getCard_id());
        String exchangeCardId = myDao.getExchangeCardId(card.getCard_id());
        if (invincibleFormCardId != null) {
            enhancedFormType = "Invincible Form";
            enhancedFormCardId = invincibleFormCardId;
        } else if (transformationCardId != null) {
            enhancedFormType = "Transformation";
            enhancedFormCardId = transformationCardId;
        } else if (exchangeCardId != null) {
            enhancedFormType = "Exchange";
            enhancedFormCardId = exchangeCardId;
        }
    }

    public void getTransformationCondition() {
        transformationCondition = "";

        if (enhancedFormType != null && enhancedFormType.equals("Invincible Form")) {
            int index;
            String separator;
            if (card.getPassive_skill().contains(";")) {
                separator = ";";
                index = card.getPassive_skill().indexOf(";");
                while(true) {
                    if (card.getPassive_skill().indexOf(";", index + 1) > 0) {
                        index = card.getPassive_skill().indexOf(";", index + 1);
                    } else {
                        break;
                    }
                }
            } else if (card.getPassive_skill().contains(",")) {
                separator = ",";
                index = card.getPassive_skill().indexOf(",");
            } else {
                separator = " and ";
                index = card.getPassive_skill().indexOf(" and ");
            }

            transformationCondition = card.getPassive_skill().substring(index + separator.length()).trim();
            transformationCondition = transformationCondition.substring(0, 1).toUpperCase() + transformationCondition.substring(1);
        } else if (enhancedFormType != null && enhancedFormType.equals("Transformation")) {
            transformationCondition = myDao.getTransformationCondition(card.getCard_id());
        } else if (enhancedFormType != null && enhancedFormType.equals("Exchange")) {
            transformationCondition = active_skill.get(0).getActive_skill_condition();
        }
    }

    public void openSuperAttackDetailsPanel(View view) {

        int index = ((Integer) view.getTag()) - 1;

        super_attack_extra_effect super_attack_extra_effect = super_attack_extra_effects.get(index);

        if (super_attacks.get(index).getSuper_attack_type().equals("Melee")) {
            findViewById(R.id.super_attack_details_ki_blast).setVisibility(View.GONE);
        }
        ((TextView) findViewById(R.id.super_attack_details_name)).setText(super_attacks.get(index).getSuper_attack_name());
        ((TextView) findViewById(R.id.super_attack_details_ki)).setText(super_attacks.get(index).getSuper_attack_launch_condition());
        ((TextView) findViewById(R.id.super_attack_details_super_attack_effect)).setText(super_attacks.get(index).getSuper_attack_effect());

        ((TextView) findViewById(R.id.super_attack_details_extra_effect_condition)).setText(super_attack_extra_effect.getExtra_effect_condition());
        ((TextView) findViewById(R.id.super_attack_details_extra_effect)).setText(super_attack_extra_effect.getExtra_effect());

        findViewById(R.id.screen_overlay).setAlpha(0.9f);
        findViewById(R.id.screen_overlay).setVisibility(View.VISIBLE);
        findViewById(R.id.super_attack_details_panel).setVisibility(View.VISIBLE);
    }

    public void closeSuperAttackDetailsPanel(View view) {
        findViewById(R.id.screen_overlay).setVisibility(View.GONE);
        findViewById(R.id.super_attack_details_panel).setVisibility(View.GONE);
        findViewById(R.id.screen_overlay).setAlpha(1f);
    }

    public boolean isOrHasExzAwakening(String card_id) {
        return isExzAwakening() || myDao.getExzAwakenedCardId(card.getCard_id()) != null;
    }

    public boolean isExzAwakening() {
        return exz_card_id != null;
    }

    public boolean isOrHasJapaneseVersion() {
        return isJapaneseVersion() || myDao.getCardMainDetails(card.getCard_id() + "_jp") != null;
    }

    public boolean isJapaneseVersion() {
        return card.getCard_id().contains("_jp");
    }
}
