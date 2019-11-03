package com.evasler.dokkanbase.roomentinties;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class rarity_max_level_relationship {

    @NonNull
    @PrimaryKey
    private String rarity;
    @NonNull
    private String max_level;

    @NonNull
    public String getRarity() {
        return rarity;
    }

    @NonNull
    public String getMax_level() {
        return max_level;
    }

    public void setRarity(@NonNull String rarity) {
        this.rarity = rarity;
    }

    public void setMax_level(@NonNull String max_level) {
        this.max_level = max_level;
    }
}
