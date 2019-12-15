package com.evasler.dokkanbase.roomentinties;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class free_to_play_cards {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private Integer free_to_play_cards_id;
    @NonNull
    private String card_id;
    @NonNull
    private String availability;
    @NonNull
    private String origin;

    @NonNull
    public Integer getFree_to_play_cards_id() {
        return free_to_play_cards_id;
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

    public void setFree_to_play_cards_id(@NonNull Integer free_to_play_cards_id) {
        this.free_to_play_cards_id = free_to_play_cards_id;
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
