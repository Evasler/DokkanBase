package com.evasler.dokkanbase.roomentinties;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class card_hidden_potential_rank_relation {

    @NonNull
    @PrimaryKey
    private String card_id;
    @NonNull
    private String hidden_potential_rank;

    @NonNull
    public String getCard_id() {
        return card_id;
    }

    @NonNull
    public String getHidden_potential_rank() {
        return hidden_potential_rank;
    }

    public void setCard_id(@NonNull String card_id) {
        this.card_id = card_id;
    }

    public void setHidden_potential_rank(@NonNull String hidden_potential_rank) {
        this.hidden_potential_rank = hidden_potential_rank;
    }
}
