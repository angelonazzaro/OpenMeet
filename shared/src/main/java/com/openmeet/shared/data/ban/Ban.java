package com.openmeet.shared.data.ban;

import com.openmeet.shared.data.storage.IEntity;

import java.sql.Timestamp;
import java.util.HashMap;

public class Ban implements IEntity {

    public static final String BAN = "Ban";
    private int id;
    private int moderatorId;
    private String description;
    private Timestamp startTime;
    private Timestamp endTime;
    private int meeterId;

    public Ban(){

    }

    @Override
    public HashMap<String, ?> toHashMap() {

        return new HashMap<>() {
            {
                put("id", id);
                put("moderatorId", moderatorId);
                put("description", description);
                put("startTime", startTime.toString());

                if (endTime != null)
                    put("endTime", endTime.toString());

                put("meeterId", meeterId);
            }
        };
    }

    @Override
    public String toString() {
        return "Ban.Ban{" +
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
