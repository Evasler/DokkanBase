package com.evasler.dokkanbase.roomentinties;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class super_attack {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private Integer super_attack_id;
    @NonNull
    private String super_attack_name;
    @NonNull
    private String super_attack_launch_condition;
    @NonNull
    private String super_attack_type;
    @NonNull
    private String super_attack_effect;

    @NonNull
    public Integer getSuper_attack_id() {
        return super_attack_id;
    }

    @NonNull
    public String getSuper_attack_name() {
        return super_attack_name;
    }

    @NonNull
    public String getSuper_attack_launch_condition() {
        return super_attack_launch_condition;
    }

    @NonNull
    public String getSuper_attack_type() {
        return super_attack_type;
    }

    @NonNull
    public String getSuper_attack_effect() {
        return super_attack_effect;
    }

    public void setSuper_attack_id(@NonNull Integer super_attack_id) {
        this.super_attack_id = super_attack_id;
    }

    public void setSuper_attack_name(@NonNull String super_attack_name) {
        this.super_attack_name = super_attack_name;
    }

    public void setSuper_attack_launch_condition(@NonNull String super_attack_launch_condition) {
        this.super_attack_launch_condition = super_attack_launch_condition;
    }

    public void setSuper_attack_type(@NonNull String super_attack_type) {
        this.super_attack_type = super_attack_type;
    }

    public void setSuper_attack_effect(@NonNull String super_attack_effect) {
        this.super_attack_effect = super_attack_effect;
    }
}
