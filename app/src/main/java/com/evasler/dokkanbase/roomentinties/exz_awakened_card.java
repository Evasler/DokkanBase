package com.evasler.dokkanbase.roomentinties;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class exz_awakened_card {

    @NonNull
    @PrimaryKey
    private String exz_awakened_card_id;
    @NonNull
    private String rarity;
    @NonNull
    private Integer max_sa_level;
    @NonNull
    private String leader_skill;
    @NonNull
    private String passive_skill;
    @NonNull
    private Integer max_hp;
    @NonNull
    private Integer max_atk;
    @NonNull
    private Integer max_def;

    @NonNull
    public String getExz_awakened_card_id() {
        return exz_awakened_card_id;
    }

    @NonNull
    public String getRarity() {
        return rarity;
    }

    @NonNull
    public Integer getMax_sa_level() {
        return max_sa_level;
    }

    @NonNull
    public String getLeader_skill() {
        return leader_skill;
    }

    @NonNull
    public String getPassive_skill() {
        return passive_skill;
    }

    @NonNull
    public Integer getMax_hp() {
        return max_hp;
    }

    @NonNull
    public Integer getMax_atk() {
        return max_atk;
    }

    @NonNull
    public Integer getMax_def() {
        return max_def;
    }

    public void setExz_awakened_card_id(@NonNull String exz_awakened_card_id) {
        this.exz_awakened_card_id = exz_awakened_card_id;
    }

    public void setRarity(@NonNull String rarity) {
        this.rarity = rarity;
    }

    public void setMax_sa_level(@NonNull Integer max_sa_level) {
        this.max_sa_level = max_sa_level;
    }

    public void setLeader_skill(@NonNull String leader_skill) {
        this.leader_skill = leader_skill;
    }

    public void setPassive_skill(@NonNull String passive_skill) {
        this.passive_skill = passive_skill;
    }

    public void setMax_hp(@NonNull Integer max_hp) {
        this.max_hp = max_hp;
    }

    public void setMax_atk(@NonNull Integer max_atk) {
        this.max_atk = max_atk;
    }

    public void setMax_def(@NonNull Integer max_def) {
        this.max_def = max_def;
    }
}
