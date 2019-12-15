package com.evasler.dokkanbase.roomentinties;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class invincible_form_card {

    @NonNull
    @PrimaryKey
    private String card_id;
    @NonNull
    private String passive_skill_name;
    @NonNull
    private String passive_skill;
    @NonNull
    private Integer cost;
    @NonNull
    private Integer base_hp;
    @NonNull
    private Integer base_atk;
    @NonNull
    private Integer base_def;

    @NonNull
    public String getCard_id() {
        return card_id;
    }

    @NonNull
    public String getPassive_skill_name() {
        return passive_skill_name;
    }

    @NonNull
    public String getPassive_skill() {
        return passive_skill;
    }

    @NonNull
    public Integer getCost() {
        return cost;
    }

    @NonNull
    public Integer getBase_hp() {
        return base_hp;
    }

    @NonNull
    public Integer getBase_atk() {
        return base_atk;
    }

    @NonNull
    public Integer getBase_def() {
        return base_def;
    }

    public void setCard_id(@NonNull String card_id) {
        this.card_id = card_id;
    }

    public void setPassive_skill_name(@NonNull String passive_skill_name) {
        this.passive_skill_name = passive_skill_name;
    }

    public void setPassive_skill(@NonNull String passive_skill) {
        this.passive_skill = passive_skill;
    }

    public void setCost(@NonNull Integer cost) {
        this.cost = cost;
    }

    public void setBase_hp(@NonNull Integer base_hp) {
        this.base_hp = base_hp;
    }

    public void setBase_atk(@NonNull Integer base_atk) {
        this.base_atk = base_atk;
    }

    public void setBase_def(@NonNull Integer base_def) {
        this.base_def = base_def;
    }
}
