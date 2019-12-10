package com.evasler.dokkanbase;

import android.content.Context;

import com.evasler.dokkanbase.roomentinties.*;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {active_skill.class, awakening_medal.class, card.class, card_active_skill_relation.class,
        card_awakening_medal_combination_relation.class, card_category_relation.class, card_dokkan_awakened_card_relation.class,
        card_exchange_card_relation.class, card_exz_awakened_card_relation.class, card_hidden_potential_rank_relation.class,
        card_invincible_form_card_relation.class, card_link_skill_relation.class, card_super_attack_relation.class,
        card_transformation_card_relation.class, card_transformation_condition_relation.class, category.class, exchange_card.class,
        exz_awakened_card.class, free_to_play_cards.class, hidden_potential.class,  hidden_potential_orb_combinations.class,
        hidden_potential_rank_b_special.class, invincible_form_card.class, link_skill.class, rarity_max_level_relationship.class,
        super_attack.class, super_attack_extra_effect.class, super_attack_super_attack_extra_effect_relation.class,
        tier_1_awakening_combination.class, tier_2_awakening_combination.class, tier_3_awakening_combination.class,
        tier_4_awakening_combination.class, tier_5_awakening_combination.class, transformation_card.class, transformation_condition.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract MyDao myDao();
    private static AppDatabase INSTANCE;
    private static final String DB_NAME = "DokkanBattle.db";

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
        }
    };

    public static AppDatabase getDatabase(final @NonNull Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DB_NAME)
                            .addMigrations(MIGRATION_1_2)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}