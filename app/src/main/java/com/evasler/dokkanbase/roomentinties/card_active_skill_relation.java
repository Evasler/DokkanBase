package com.evasler.dokkanbase.roomentinties;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class card_active_skill_relation {

    @NonNull
    @PrimaryKey
    private String card_id;
    @NonNull
    private Integer active_skill_id;

    @NonNull
    public String getCard_id() {
        return card_id;
    }

    @NonNull
    public Integer getActive_skill_id() {
        return active_skill_id;
    }

    public void setCard_id(@NonNull String card_id) {
        this.card_id = card_id;
    }

    public void setActive_skill_id(@NonNull Integer active_skill_id) {
        this.active_skill_id = active_skill_id;
    }
}
