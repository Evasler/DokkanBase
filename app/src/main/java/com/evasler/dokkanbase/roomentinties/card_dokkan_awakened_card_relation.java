package com.evasler.dokkanbase.roomentinties;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class card_dokkan_awakened_card_relation {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private Integer card_category_relation_id;
    @NonNull
    private String card_id;
    @NonNull
    private String dokkan_awakened_card_id;
    @NonNull
    private Integer dokkan_awakening_medal_combination_id;

    @NonNull
    public String getCard_id() {
        return card_id;
    }

    @NonNull
    public String getDokkan_awakened_card_id() {
        return dokkan_awakened_card_id;
    }

    @NonNull
    public Integer getCard_category_relation_id() {
        return card_category_relation_id;
    }

    @NonNull
    public Integer getDokkan_awakening_medal_combination_id() {
        return dokkan_awakening_medal_combination_id;
    }

    public void setCard_category_relation_id(@NonNull Integer card_category_relation_id) {
        this.card_category_relation_id = card_category_relation_id;
    }

    public void setCard_id(@NonNull String card_id) {
        this.card_id = card_id;
    }

    public void setDokkan_awakened_card_id(@NonNull String dokkan_awakened_card_id) {
        this.dokkan_awakened_card_id = dokkan_awakened_card_id;
    }

    public void setDokkan_awakening_medal_combination_id(@NonNull Integer dokkan_awakening_medal_combination_id) {
        this.dokkan_awakening_medal_combination_id = dokkan_awakening_medal_combination_id;
    }
}
