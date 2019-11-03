package com.evasler.dokkanbase.roomentinties;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class transformation_card {

    @NonNull
    @PrimaryKey
    private String transformation_card_id;
    @NonNull
    private String character_name;
    @NonNull
    private String passive_skill_name;
    @NonNull
    private String passive_skill;

    @NonNull
    public String getTransformation_card_id() {
        return transformation_card_id;
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

    public void setTransformation_card_id(@NonNull String transformation_card_id) {
        this.transformation_card_id = transformation_card_id;
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
