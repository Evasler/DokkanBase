package com.evasler.dokkanbase.roomentinties;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class card_awakening_medal_combination_relation {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private Integer card_awakening_medal_combination_relation_id;
    @NonNull
    private String card_id;
    @NonNull
    private Integer awakening_medal_combination_id;

    @NonNull
    public Integer getCard_awakening_medal_combination_relation_id() {
        return card_awakening_medal_combination_relation_id;
    }

    @NonNull
    public String getCard_id() {
        return card_id;
    }

    @NonNull
    public Integer getAwakening_medal_combination_id() {
        return awakening_medal_combination_id;
    }

    public void setCard_awakening_medal_combination_relation_id(@NonNull Integer card_awakening_medal_combination_relation_id) {
        this.card_awakening_medal_combination_relation_id = card_awakening_medal_combination_relation_id;
    }

    public void setCard_id(@NonNull String card_id) {
        this.card_id = card_id;
    }

    public void setAwakening_medal_combination_id(@NonNull Integer awakening_medal_combination_id) {
        this.awakening_medal_combination_id = awakening_medal_combination_id;
    }
}
