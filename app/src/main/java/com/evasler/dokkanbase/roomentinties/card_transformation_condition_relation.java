package com.evasler.dokkanbase.roomentinties;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class card_transformation_condition_relation {

    @NonNull
    @PrimaryKey
    private String card_id;
    @NonNull
    private Integer transformation_condition_id;

    @NonNull
    public String getCard_id() {
        return card_id;
    }

    @NonNull
    public Integer getTransformation_condition_id() {
        return transformation_condition_id;
    }

    public void setCard_id(@NonNull String card_id) {
        this.card_id = card_id;
    }

    public void setTransformation_condition_id(@NonNull Integer transformation_condition_id) {
        this.transformation_condition_id = transformation_condition_id;
    }
}
