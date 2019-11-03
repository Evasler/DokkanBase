package com.evasler.dokkanbase.roomentinties;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class link_skill {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private Integer link_skill_id;
    @NonNull
    private String link_skill_name;
    @NonNull
    private String link_skill_effect;

    @NonNull
    public Integer getLink_skill_id() {
        return link_skill_id;
    }

    @NonNull
    public String getLink_skill_name() {
        return link_skill_name;
    }

    @NonNull
    public String getLink_skill_effect() {
        return link_skill_effect;
    }

    public void setLink_skill_id(@NonNull Integer link_skill_id) {
        this.link_skill_id = link_skill_id;
    }

    public void setLink_skill_name(@NonNull String link_skill_name) {
        this.link_skill_name = link_skill_name;
    }

    public void setLink_skill_effect(@NonNull String link_skill_effect) {
        this.link_skill_effect = link_skill_effect;
    }
}
