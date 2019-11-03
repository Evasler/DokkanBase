package com.evasler.dokkanbase.roomentinties;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class awakening_medal {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private Integer medal_id;
    @NonNull
    private String medal_name;
    @NonNull
    private String medal_category;

    @NonNull
    public Integer getMedal_id() {
        return medal_id;
    }

    @NonNull
    public String getMedal_name() {
        return medal_name;
    }

    @NonNull
    public String getMedal_category() {
        return medal_category;
    }

    public void setMedal_id(@NonNull Integer medal_id) {
        this.medal_id = medal_id;
    }

    public void setMedal_name(@NonNull String medal_name) {
        this.medal_name = medal_name;
    }

    public void setMedal_category(@NonNull String medal_category) {
        this.medal_category = medal_category;
    }
}
