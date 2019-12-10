package com.evasler.dokkanbase;

import com.evasler.dokkanbase.queryresponseobjects.active_skill_details;
import com.evasler.dokkanbase.queryresponseobjects.card_preview;
import com.evasler.dokkanbase.queryresponseobjects.dokkan_details;
import com.evasler.dokkanbase.queryresponseobjects.pre_dokkan_details;
import com.evasler.dokkanbase.queryresponseobjects.tier_1_medal_combination;
import com.evasler.dokkanbase.queryresponseobjects.tier_2_medal_combination;
import com.evasler.dokkanbase.queryresponseobjects.tier_3_medal_combination;
import com.evasler.dokkanbase.queryresponseobjects.tier_4_medal_combination;
import com.evasler.dokkanbase.queryresponseobjects.tier_5_medal_combination;
import com.evasler.dokkanbase.roomentinties.*;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface MyDao {

    @Query("SELECT card_id, rarity, type FROM card WHERE card_id NOT LIKE '%_jp' AND (rarity = 'LR')")
    List<card_preview> getCardsForPreview();

    @Query("SELECT * FROM card WHERE card_id = :card_id")
    card getCardMainDetails(String card_id);

    @Query("SELECT max_level FROM rarity_max_level_relationship WHERE rarity = :rarity")
    int getMaxLevel(String rarity);

    @Query("SELECT active_skill_name, active_skill_type, active_skill_effect FROM card, card_active_skill_relation,active_skill WHERE " +
            "card.card_id = card_active_skill_relation.card_id AND card_active_skill_relation.active_skill_id = active_skill.active_skill_id AND " +
            "card.card_id = :card_id")
    List<active_skill_details> getActiveSkill(String card_id);

    @Query("SELECT card_id, dokkan_awakening_medal_combination_id FROM card_dokkan_awakened_card_relation WHERE dokkan_awakened_card_id = :card_id")
    pre_dokkan_details getPreDokkanAwakenedCardDetails(String card_id);

    @Query("SELECT dokkan_awakened_card_id, dokkan_awakening_medal_combination_id FROM card_dokkan_awakened_card_relation WHERE card_id = :card_id")
    dokkan_details getDokkanAwakenedCardDetails(String card_id);

    @Query("SELECT medal_1_id, medal_1_count, medal_2_id, medal_2_count, medal_3_id, medal_3_count, medal_4_id, medal_4_count, medal_5_id, medal_5_count FROM tier_1_awakening_combination, tier_2_awakening_combination, tier_3_awakening_combination, tier_4_awakening_combination, tier_5_awakening_combination WHERE tier_1_awakening_combination.tier_1_combination_id = tier_2_awakening_combination.tier_1_combination_id AND tier_2_awakening_combination.tier_2_combination_id = tier_3_awakening_combination.tier_2_combination_id AND tier_3_awakening_combination.tier_3_combination_id = tier_4_awakening_combination.tier_3_combination_id AND tier_4_awakening_combination.tier_4_combination_id = tier_5_awakening_combination.tier_4_combination_id AND tier_5_awakening_combination.tier_5_combination_id = :medal_combination_id")
    tier_5_medal_combination getTier5MedalCombination(int medal_combination_id);

    @Query("SELECT medal_1_id, medal_1_count, medal_2_id, medal_2_count, medal_3_id, medal_3_count, medal_4_id, medal_4_count FROM tier_1_awakening_combination, tier_2_awakening_combination, tier_3_awakening_combination, tier_4_awakening_combination WHERE tier_1_awakening_combination.tier_1_combination_id = tier_2_awakening_combination.tier_1_combination_id AND tier_2_awakening_combination.tier_2_combination_id = tier_3_awakening_combination.tier_2_combination_id AND tier_3_awakening_combination.tier_3_combination_id = tier_4_awakening_combination.tier_3_combination_id AND tier_4_awakening_combination.tier_4_combination_id = :medal_combination_id")
    tier_4_medal_combination getTier4MedalCombination(int medal_combination_id);

    @Query("SELECT medal_1_id, medal_1_count, medal_2_id, medal_2_count, medal_3_id, medal_3_count FROM tier_1_awakening_combination, tier_2_awakening_combination, tier_3_awakening_combination WHERE tier_1_awakening_combination.tier_1_combination_id = tier_2_awakening_combination.tier_1_combination_id AND tier_2_awakening_combination.tier_2_combination_id = tier_3_awakening_combination.tier_2_combination_id AND tier_3_awakening_combination.tier_3_combination_id = :medal_combination_id")
    tier_3_medal_combination getTier3MedalCombination(int medal_combination_id);

    @Query("SELECT medal_1_id, medal_1_count, medal_2_id, medal_2_count FROM tier_1_awakening_combination, tier_2_awakening_combination WHERE tier_1_awakening_combination.tier_1_combination_id = tier_2_awakening_combination.tier_1_combination_id AND tier_2_awakening_combination.tier_2_combination_id = :medal_combination_id")
    tier_2_medal_combination getTier2MedalCombination(int medal_combination_id);

    @Query("SELECT medal_1_id, medal_1_count FROM tier_1_awakening_combination WHERE tier_1_awakening_combination.tier_1_combination_id = :medal_combination_id")
    tier_1_medal_combination getTier1MedalCombination(int medal_combination_id);

    @Query("SELECT super_attack.super_attack_id,super_attack_name,super_attack_launch_condition,super_attack_type,super_attack_effect from card_super_attack_relation,super_attack WHERE card_super_attack_relation.super_attack_id = super_attack.super_attack_id AND card_id = :card_id")
    List<super_attack> getSuperAttacks(String card_id);

    @Query("SELECT super_attack_extra_effect.extra_effect_id,extra_effect_condition,extra_effect FROM super_attack_super_attack_extra_effect_relation,super_attack_extra_effect WHERE super_attack_super_attack_extra_effect_relation.extra_effect_id = super_attack_extra_effect.extra_effect_id AND super_attack_super_attack_extra_effect_relation.super_attack_id = :super_attack_id")
    super_attack_extra_effect getSuperAttackExtraEffect(int super_attack_id);

    @Query("SELECT * FROM card_link_skill_relation,link_skill WHERE card_link_skill_relation.link_skill_id = link_skill.link_skill_id AND card_id = :card_id")
    List<link_skill> getLinkSkills(String card_id);

    @Query("SELECT * FROM card_category_relation,category WHERE card_category_relation.category_id = category.category_id AND card_id = :card_id")
    List<category> getCategories(String card_id);
}
