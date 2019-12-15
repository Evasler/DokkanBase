package com.evasler.dokkanbase.roomentinties;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class card_link_skill_relation {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private Integer card_link_skill_relation_id;
    @NonNull
    private String card_id;
    @NonNull
    private Integer link_skill_id;

    @NonNull
    public Integer getCard_link_skill_relation_id() {
        return card_link_skill_relation_id;
    }

    @NonNull
    public String getCard_id() {
        return card_id;
    }

    @NonNull
    public Integer getLink_skill_id() {
        return link_skill_id;
    }

    public void setCard_link_skill_relation_id(@NonNull Integer card_link_skill_relation_id) {
        this.card_link_skill_relation_id = card_link_skill_relation_id;
    }

    public void setCard_id(@NonNull String card_id) {
        this.card_id = card_id;
    }

    public void setLink_skill_id(@NonNull Integer link_skill_id) {
        this.link_skill_id = link_skill_id;
    }
}
