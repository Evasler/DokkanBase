package com.evasler.dokkanbase.roomentinties;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class card_category_relation {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private Integer card_category_relation_id;
    @NonNull
    private String card_id;
    @NonNull
    private Integer category_id;

    @NonNull
    public Integer getCard_category_relation_id() {
        return card_category_relation_id;
    }

    @NonNull
    public String getCard_id() {
        return card_id;
    }

    @NonNull
    public Integer getCategory_id() {
        return category_id;
    }

    public void setCard_category_relation_id(@NonNull Integer card_category_relation_id) {
        this.card_category_relation_id = card_category_relation_id;
    }

    public void setCard_id(@NonNull String card_id) {
        this.card_id = card_id;
    }

    public void setCategory_id(@NonNull Integer category_id) {
        this.category_id = category_id;
    }
}
