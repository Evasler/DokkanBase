package com.evasler.dokkanbase.roomentinties;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class card_super_attack_relation {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private Integer card_super_attack_relation_id;
    @NonNull
    private String card_id;
    @NonNull
    private Integer super_attack_id;

    @NonNull
    public Integer getCard_super_attack_relation_id() {
        return card_super_attack_relation_id;
    }

    @NonNull
    public String getCard_id() {
        return card_id;
    }

    @NonNull
    public Integer getSuper_attack_id() {
        return super_attack_id;
    }

    public void setCard_super_attack_relation_id(@NonNull Integer card_category_relation_id) {
        this.card_super_attack_relation_id = card_category_relation_id;
    }

    public void setCard_id(@NonNull String card_id) {
        this.card_id = card_id;
    }

    public void setSuper_attack_id(@NonNull Integer super_attack_id) {
        this.super_attack_id = super_attack_id;
    }
}
