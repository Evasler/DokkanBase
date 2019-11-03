package com.evasler.dokkanbase.roomentinties;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class tier_5_awakening_combination {

    @NonNull
    @PrimaryKey
    private Integer tier_5_combination_id;
    @NonNull
    private Integer tier_4_combination_id;
    @NonNull
    private Integer medal_5_id;
    @NonNull
    private Integer medal_5_count;

    @NonNull
    public Integer getTier_5_combination_id() {
        return tier_5_combination_id;
    }

    @NonNull
    public Integer getTier_4_combination_id() {
        return tier_4_combination_id;
    }

    @NonNull
    public Integer getMedal_5_id() {
        return medal_5_id;
    }

    @NonNull
    public Integer getMedal_5_count() {
        return medal_5_count;
    }

    public void setTier_5_combination_id(@NonNull Integer tier_5_combination_id) {
        this.tier_5_combination_id = tier_5_combination_id;
    }

    public void setTier_4_combination_id(@NonNull Integer tier_4_combination_id) {
        this.tier_4_combination_id = tier_4_combination_id;
    }

    public void setMedal_5_id(@NonNull Integer medal_5_id) {
        this.medal_5_id = medal_5_id;
    }

    public void setMedal_5_count(@NonNull Integer medal_5_count) {
        this.medal_5_count = medal_5_count;
    }
}
