package com.evasler.dokkanbase.roomentinties;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class exchange_card {

    @NonNull
    @PrimaryKey
    private String exchange_card_id;
    @NonNull
    private String character_name;
    @NonNull
    private String passive_skill_name;
    @NonNull
    private String passive_skill;

    @NonNull
    public String getExchange_card_id() {
        return exchange_card_id;
    }

    @NonNull
    public String getCharacter_name() {
        return character_name;
    }

    @NonNull
    public String getPassive_skill_name() {
        return passive_skill_name;
    }

    @NonNull
    public String getPassive_skill() {
        return passive_skill;
    }

    public void setExchange_card_id(@NonNull String exchange_card_id) {
        this.exchange_card_id = exchange_card_id;
    }

    public void setCharacter_name(@NonNull String character_name) {
        this.character_name = character_name;
    }

    public void setPassive_skill_name(@NonNull String passive_skill_name) {
        this.passive_skill_name = passive_skill_name;
    }

    public void setPassive_skill(@NonNull String passive_skill) {
        this.passive_skill = passive_skill;
    }
}
