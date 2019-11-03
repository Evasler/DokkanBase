package com.evasler.dokkanbase.roomentinties;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class card {

    @NonNull
    @PrimaryKey
    private String card_id;
    @NonNull
    private String card_name;
    @NonNull
    private String character_name;
    @NonNull
    private String rarity;
    @NonNull
    private String type;
    @NonNull
    private String cost;
    @NonNull
    private Integer max_sa_level;
    @NonNull
    private String leader_skill;
    @NonNull
    private String passive_skill_name;
    @NonNull
    private String passive_skill;
    @NonNull
    private Integer max_hp;
    @NonNull
    private Integer max_atk;
    @NonNull
    private Integer max_def;
    @NonNull
    private Integer green_ki;
    @NonNull
    private Integer twelve_ki_multiplier;

    @NonNull
    public String getCard_id() {
        return card_id;
    }

    @NonNull
    public String getCard_name() {
        return card_name;
    }

    @NonNull
    public String getCharacter_name() {
        return character_name;
    }

    @NonNull
    public String getRarity() {
        return rarity;
    }

    @NonNull
    public String getType() {
        return type;
    }

    @NonNull
    public String getCost() {
        return cost;
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
    public String getPassive_skill_name() {
        return passive_skill_name;
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

    @NonNull
    public Integer getGreen_ki() {
        return green_ki;
    }

    @NonNull
    public Integer getTwelve_ki_multiplier() {
        return twelve_ki_multiplier;
    }

    public void setCard_id(@NonNull String card_id) {
        this.card_id = card_id;
    }

    public void setCard_name(@NonNull String card_name) {
        this.card_name = card_name;
    }

    public void setCharacter_name(@NonNull String character_name) {
        this.character_name = character_name;
    }

    public void setRarity(@NonNull String rarity) {
        this.rarity = rarity;
    }

    public void setType(@NonNull String type) {
        this.type = type;
    }

    public void setCost(@NonNull String cost) {
        this.cost = cost;
    }

    public void setMax_sa_level(@NonNull Integer max_sa_level) {
        this.max_sa_level = max_sa_level;
    }

    public void setLeader_skill(@NonNull String leader_skill) {
        this.leader_skill = leader_skill;
    }

    public void setPassive_skill_name(@NonNull String passive_skill_name) {
        this.passive_skill_name = passive_skill_name;
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

    public void setGreen_ki(@NonNull Integer green_ki) {
        this.green_ki = green_ki;
    }

    public void setTwelve_ki_multiplier(@NonNull Integer twelve_ki_multiplier) {
        this.twelve_ki_multiplier = twelve_ki_multiplier;
    }
}
