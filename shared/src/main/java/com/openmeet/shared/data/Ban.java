package com.openmeet.shared.data;

import java.sql.Timestamp;

public class Ban {

    private int id;
    private int moderatorId;
    private String description;
    private Timestamp startTime;
    private Timestamp endTime;
    private int meeterId;

    public Ban(){

    }

    @Override
    public String toString() {
        return "Ban{" +
                "id=" + id +
                ", moderatorId=" + moderatorId +
                ", description='" + description + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", meeterId=" + meeterId +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getModeratorId() {
        return moderatorId;
    }

    public void setModeratorId(int moderatorId) {
        this.moderatorId = moderatorId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public int getMeeterId() {
        return meeterId;
    }

    public void setMeeterId(int meeterId) {
        this.meeterId = meeterId;
    }
}
