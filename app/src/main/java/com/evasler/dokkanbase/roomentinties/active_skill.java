package com.evasler.dokkanbase.roomentinties;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class active_skill {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private Integer active_skill_id;
    @NonNull
    private String active_skill_name;
    @NonNull
    private String active_skill_type;
    @NonNull
    private String active_skill_effect;
    @NonNull
    private String active_skill_condition;

    @NonNull
    public Integer getActive_skill_id() {
        return active_skill_id;
    }

    @NonNull
    public String getActive_skill_name() {
        return active_skill_name;
    }

    @NonNull
    public String getActive_skill_type() {
        return active_skill_type;
    }

    @NonNull
    public String getActive_skill_effect() {
        return active_skill_effect;
    }

    @NonNull
    public String getActive_skill_condition() {
        return active_skill_condition;
    }

    public void setActive_skill_id(@NonNull Integer active_skill_id) {
        this.active_skill_id = active_skill_id;
    }

    public void setActive_skill_name(@NonNull String active_skill_name) {
        this.active_skill_name = active_skill_name;
    }

    public void setActive_skill_type(@NonNull String active_skill_type) {
        this.active_skill_type = active_skill_type;
    }

    public void setActive_skill_effect(@NonNull String active_skill_effect) {
        this.active_skill_effect = active_skill_effect;
    }

    public void setActive_skill_condition(@NonNull String active_skill_condition) {
        this.active_skill_condition = active_skill_condition;
    }
}
