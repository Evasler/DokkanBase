package com.evasler.dokkanbase.roomentinties;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class super_attack_extra_effect {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private Integer extra_effect_id;
    @NonNull
    private String extra_effect;

    @NonNull
    public Integer getExtra_effect_id() {
        return extra_effect_id;
    }

    @NonNull
    public String getExtra_effect() {
        return extra_effect;
    }

    public void setExtra_effect_id(@NonNull Integer extra_effect_id) {
        this.extra_effect_id = extra_effect_id;
    }

    public void setExtra_effect(@NonNull String extra_effect) {
        this.extra_effect = extra_effect;
    }
}
