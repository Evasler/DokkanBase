package com.evasler.dokkanbase.roomentinties;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class card_exchange_card_relation {

    @NonNull
    @PrimaryKey
    private String card_id;
    @NonNull
    private String exchange_card_id;

    @NonNull
    public String getCard_id() {
        return card_id;
    }

    @NonNull
    public String getExchange_card_id() {
        return exchange_card_id;
    }

    public void setCard_id(@NonNull String card_id) {
        this.card_id = card_id;
    }

    public void setExchange_card_id(@NonNull String exchange_card_id) {
        this.exchange_card_id = exchange_card_id;
    }
}
