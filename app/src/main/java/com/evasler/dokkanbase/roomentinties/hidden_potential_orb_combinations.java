package com.evasler.dokkanbase.roomentinties;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class hidden_potential_orb_combinations {

    @NonNull
    @PrimaryKey
    private Integer id;
    @NonNull
    private Integer small_orbs;
    @NonNull
    private Integer medium_orbs;
    @NonNull
    private Integer large_orbs;

    @NonNull
    public Integer getId() {
        return id;
    }

    @NonNull
    public Integer getSmall_orbs() {
        return small_orbs;
    }

    @NonNull
    public Integer getMedium_orbs() {
        return medium_orbs;
    }

    @NonNull
    public Integer getLarge_orbs() {
        return large_orbs;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }

    public void setSmall_orbs(@NonNull Integer small_orbs) {
        this.small_orbs = small_orbs;
    }

    public void setMedium_orbs(@NonNull Integer medium_orbs) {
        this.medium_orbs = medium_orbs;
    }

    public void setLarge_orbs(@NonNull Integer large_orbs) {
        this.large_orbs = large_orbs;
    }
}
