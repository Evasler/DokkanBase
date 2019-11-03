package com.evasler.dokkanbase.roomentinties;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class card_exz_awakened_card_relation {

    @NonNull
    @PrimaryKey
    private String card_id;
    @NonNull
    private String exz_awakened_card_id;
    @NonNull
    private Integer exz_awakening_medal_combination_id;

    @NonNull
    public String getCard_id() {
        return card_id;
    }

    @NonNull
    public String getExz_awakened_card_id() {
        return exz_awakened_card_id;
    }

    @NonNull
    public Integer getExz_awakening_medal_combination_id() {
        return exz_awakening_medal_combination_id;
    }

    public void setCard_id(@NonNull String card_id) {
        this.card_id = card_id;
    }

    public void setExz_awakened_card_id(@NonNull String exz_awakened_card_id) {
        this.exz_awakened_card_id = exz_awakened_card_id;
    }

    public void setExz_awakening_medal_combination_id(@NonNull Integer exz_awakening_medal_combination_id) {
        this.exz_awakening_medal_combination_id = exz_awakening_medal_combination_id;
    }
}
