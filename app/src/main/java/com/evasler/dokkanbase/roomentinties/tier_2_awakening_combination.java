package com.evasler.dokkanbase.roomentinties;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class tier_2_awakening_combination {

    @NonNull
    @PrimaryKey
    private Integer tier_2_combination_id;
    @NonNull
    private Integer tier_1_combination_id;
    @NonNull
    private Integer medal_2_id;
    @NonNull
    private Integer medal_2_count;

    @NonNull
    public Integer getTier_2_combination_id() {
        return tier_2_combination_id;
    }

    @NonNull
    public Integer getTier_1_combination_id() {
        return tier_1_combination_id;
    }

    @NonNull
    public Integer getMedal_2_id() {
        return medal_2_id;
    }

    @NonNull
    public Integer getMedal_2_count() {
        return medal_2_count;
    }

    public void setTier_2_combination_id(@NonNull Integer tier_2_combination_id) {
        this.tier_2_combination_id = tier_2_combination_id;
    }

    public void setTier_1_combination_id(@NonNull Integer tier_1_combination_id) {
        this.tier_1_combination_id = tier_1_combination_id;
    }

    public void setMedal_2_id(@NonNull Integer medal_2_id) {
        this.medal_2_id = medal_2_id;
    }

    public void setMedal_2_count(@NonNull Integer medal_2_count) {
        this.medal_2_count = medal_2_count;
    }
}
