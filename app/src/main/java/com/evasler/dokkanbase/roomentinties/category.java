package com.evasler.dokkanbase.roomentinties;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class category {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private Integer category_id;
    @NonNull
    private String category_name;

    @NonNull
    public Integer getCategory_id() {
        return category_id;
    }

    @NonNull
    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_id(@NonNull Integer category_id) {
        this.category_id = category_id;
    }

    public void setCategory_name(@NonNull String category_name) {
        this.category_name = category_name;
    }
}
