package com.evasler.dokkanbase.roomentinties;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class super_attack_super_attack_extra_effect_relation {

    @NonNull
    @PrimaryKey
    private Integer extra_effect_id;
    @NonNull
    private Integer super_attack_id;

    @NonNull
    public Integer getSuper_attack_id() {
        return super_attack_id;
    }

    @NonNull
    public Integer getExtra_effect_id() {
        return extra_effect_id;
    }

    public void setSuper_attack_id(@NonNull Integer super_attack_id) {
        this.super_attack_id = super_attack_id;
    }

    public void setExtra_effect_id(@NonNull Integer extra_effect_id) {
        this.extra_effect_id = extra_effect_id;
    }
}
