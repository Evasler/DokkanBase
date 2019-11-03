package com.evasler.dokkanbase.roomentinties;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class tier_3_awakening_combination {

    @NonNull
    @PrimaryKey
    private Integer tier_3_combination_id;
    @NonNull
    private Integer tier_2_combination_id;
    @NonNull
    private Integer medal_3_id;
    @NonNull
    private Integer medal_3_count;

    @NonNull
    public Integer getTier_3_combination_id() {
        return tier_3_combination_id;
    }

    @NonNull
    public Integer getTier_2_combination_id() {
        return tier_2_combination_id;
    }

    @NonNull
    public Integer getMedal_3_id() {
        return medal_3_id;
    }

    @NonNull
    public Integer getMedal_3_count() {
        return medal_3_count;
    }

    public void setTier_3_combination_id(@NonNull Integer tier_3_combination_id) {
        this.tier_3_combination_id = tier_3_combination_id;
    }

    public void setTier_2_combination_id(@NonNull Integer tier_2_combination_id) {
        this.tier_2_combination_id = tier_2_combination_id;
    }

    public void setMedal_3_id(@NonNull Integer medal_3_id) {
        this.medal_3_id = medal_3_id;
    }

    public void setMedal_3_count(@NonNull Integer medal_3_count) {
        this.medal_3_count = medal_3_count;
    }
}
