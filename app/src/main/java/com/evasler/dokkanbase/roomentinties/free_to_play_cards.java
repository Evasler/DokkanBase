package com.evasler.dokkanbase.roomentinties;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class free_to_play_cards {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private Integer card_category_relation_id;
    @NonNull
    private String card_id;
    @NonNull
    private String availability;
    @NonNull
    private String origin;

    @NonNull
    public Integer getCard_category_relation_id() {
        return card_category_relation_id;
    }

    @NonNull
    public String getCard_id() {
        return card_id;
    }

    @NonNull
    public String getAvailability() {
        return availability;
    }

    @NonNull
    public String getOrigin() {
        return origin;
    }

    public void setCard_category_relation_id(@NonNull Integer card_category_relation_id) {
        this.card_category_relation_id = card_category_relation_id;
    }

    public void setCard_id(@NonNull String card_id) {
        this.card_id = card_id;
    }

    public void setAvailability(@NonNull String availability) {
        this.availability = availability;
    }

    public void setOrigin(@NonNull String origin) {
        this.origin = origin;
    }
}
