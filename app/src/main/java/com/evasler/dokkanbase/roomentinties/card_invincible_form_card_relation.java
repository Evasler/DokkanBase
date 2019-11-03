package com.evasler.dokkanbase.roomentinties;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class card_invincible_form_card_relation {

    @NonNull
    @PrimaryKey
    private String card_id;
    @NonNull
    private String invincible_form_card_id;

    @NonNull
    public String getCard_id() {
        return card_id;
    }

    @NonNull
    public String getInvincible_form_card_id() {
        return invincible_form_card_id;
    }

    public void setCard_id(@NonNull String card_id) {
        this.card_id = card_id;
    }

    public void setInvincible_form_card_id(@NonNull String invincible_form_card_id) {
        this.invincible_form_card_id = invincible_form_card_id;
    }
}
