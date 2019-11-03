package com.evasler.dokkanbase.roomentinties;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class hidden_potential_rank_b_special {

    @NonNull
    @PrimaryKey
    private String rank_b_special_id;
    @NonNull
    private String event;
    @NonNull
    private Integer hidden_potential_orbs_combination_id;

    @NonNull
    public String getRank_b_special_id() {
        return rank_b_special_id;
    }

    @NonNull
    public String getEvent() {
        return event;
    }

    @NonNull
    public Integer getHidden_potential_orbs_combination_id() {
        return hidden_potential_orbs_combination_id;
    }

    public void setRank_b_special_id(@NonNull String rank_b_special_id) {
        this.rank_b_special_id = rank_b_special_id;
    }

    public void setEvent(@NonNull String event) {
        this.event = event;
    }

    public void setHidden_potential_orbs_combination_id(@NonNull Integer hidden_potential_orbs_combination_id) {
        this.hidden_potential_orbs_combination_id = hidden_potential_orbs_combination_id;
    }
}
