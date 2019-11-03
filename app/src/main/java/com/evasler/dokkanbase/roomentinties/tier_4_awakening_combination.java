package com.evasler.dokkanbase.roomentinties;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class tier_4_awakening_combination {

    @NonNull
    @PrimaryKey
    private Integer tier_4_combination_id;
    @NonNull
    private Integer tier_3_combination_id;
    @NonNull
    private Integer medal_4_id;
    @NonNull
    private Integer medal_4_count;

    @NonNull
    public Integer getTier_4_combination_id() {
        return tier_4_combination_id;
    }

    @NonNull
    public Integer getTier_3_combination_id() {
        return tier_3_combination_id;
    }

    @NonNull
    public Integer getMedal_4_id() {
        return medal_4_id;
    }

    @NonNull
    public Integer getMedal_4_count() {
        return medal_4_count;
    }

    public void setTier_4_combination_id(@NonNull Integer tier_4_combination_id) {
        this.tier_4_combination_id = tier_4_combination_id;
    }

    public void setTier_3_combination_id(@NonNull Integer tier_3_combination_id) {
        this.tier_3_combination_id = tier_3_combination_id;
    }

    public void setMedal_4_id(@NonNull Integer medal_4_id) {
        this.medal_4_id = medal_4_id;
    }

    public void setMedal_4_count(@NonNull Integer medal_4_count) {
        this.medal_4_count = medal_4_count;
    }
}
