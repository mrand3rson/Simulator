package com.example.simulator.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;


@Entity
public class Statistics {
    @Id(autoincrement = true)
    private Long id;

    private Integer trainingType;
    private String duration;
    private String date;
    private String description;


    @Generated(hash = 1019319219)
    public Statistics(Long id, Integer trainingType, String duration, String date,
            String description) {
        this.id = id;
        this.trainingType = trainingType;
        this.duration = duration;
        this.date = date;
        this.description = description;
    }

    @Generated(hash = 1975114801)
    public Statistics() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(Integer trainingType) {
        this.trainingType = trainingType;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
