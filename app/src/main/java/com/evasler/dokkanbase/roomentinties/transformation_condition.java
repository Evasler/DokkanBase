package com.evasler.dokkanbase.roomentinties;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class transformation_condition {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private Integer transformation_condition_id;
    @NonNull
    private String transformation_condition_details;

    @NonNull
    public Integer getTransformation_condition_id() {
        return transformation_condition_id;
    }

    @NonNull
    public String getTransformation_condition_details() {
        return transformation_condition_details;
    }

    public void setTransformation_condition_id(@NonNull Integer transformation_condition_id) {
        this.transformation_condition_id = transformation_condition_id;
    }

    public void setTransformation_condition_details(@NonNull String transformation_condition_details) {
        this.transformation_condition_details = transformation_condition_details;
    }
}
