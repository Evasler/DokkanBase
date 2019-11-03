package com.evasler.dokkanbase.roomentinties;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(primaryKeys = {"hidden_potential_rank","type"})
public class hidden_potential {

    @NonNull
    private String hidden_potential_rank;
    @NonNull
    private String type;
    @NonNull
    private Integer hidden_potential_orbs_combination_id;
    @NonNull
    private Integer hp_boost;
    @NonNull
    private Integer atk_boost;
    @NonNull
    private Integer def_boost;

    @NonNull
    public String getHidden_potential_rank() {
        return hidden_potential_rank;
    }

    @NonNull
    public String getType() {
        return type;
    }

    @NonNull
    public Integer getHidden_potential_orbs_combination_id() {
        return hidden_potential_orbs_combination_id;
    }

    @NonNull
    public Integer getHp_boost() {
        return hp_boost;
    }

    @NonNull
    public Integer getAtk_boost() {
        return atk_boost;
    }

    @NonNull
    public Integer getDef_boost() {
        return def_boost;
    }

    public void setHidden_potential_rank(@NonNull String hidden_potential_rank) {
        this.hidden_potential_rank = hidden_potential_rank;
    }

    public void setType(@NonNull String type) {
        this.type = type;
    }

    public void setHidden_potential_orbs_combination_id(@NonNull Integer hidden_potential_orbs_combination_id) {
        this.hidden_potential_orbs_combination_id = hidden_potential_orbs_combination_id;
    }

    public void setHp_boost(@NonNull Integer hp_boost) {
        this.hp_boost = hp_boost;
    }

    public void setAtk_boost(@NonNull Integer atk_boost) {
        this.atk_boost = atk_boost;
    }

    public void setDef_boost(@NonNull Integer def_boost) {
        this.def_boost = def_boost;
    }
}
