package com.evasler.dokkanbase.roomentinties;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class card_transformation_card_relation {

    @NonNull
    @PrimaryKey
    private String card_id;
    @NonNull
    private String transformation_card_id;

    @NonNull
    public String getCard_id() {
        return card_id;
    }

    @NonNull
    public String getTransformation_card_id() {
        return transformation_card_id;
    }

    public void setCard_id(@NonNull String card_id) {
        this.card_id = card_id;
    }

    public void setTransformation_card_id(@NonNull String transformation_card_id) {
        this.transformation_card_id = transformation_card_id;
    }
}
