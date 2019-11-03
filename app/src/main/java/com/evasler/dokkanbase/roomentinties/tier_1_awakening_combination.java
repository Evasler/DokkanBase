package com.evasler.dokkanbase.roomentinties;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class tier_1_awakening_combination {

    @NonNull
    @PrimaryKey
    private Integer tier_1_combination_id;
    @NonNull
    private Integer medal_1_id;
    @NonNull
    private Integer medal_1_count;

    @NonNull
    public Integer getTier_1_combination_id() {
        return tier_1_combination_id;
    }

    @NonNull
    public Integer getMedal_1_id() {
        return medal_1_id;
    }

    @NonNull
    public Integer getMedal_1_count() {
        return medal_1_count;
    }

    public void setTier_1_combination_id(@NonNull Integer tier_1_combination_id) {
        this.tier_1_combination_id = tier_1_combination_id;
    }

    public void setMedal_1_id(@NonNull Integer medal_1_id) {
        this.medal_1_id = medal_1_id;
    }

    public void setMedal_1_count(@NonNull Integer medal_1_count) {
        this.medal_1_count = medal_1_count;
    }
}
