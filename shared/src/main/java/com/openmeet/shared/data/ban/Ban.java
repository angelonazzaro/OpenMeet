package com.openmeet.shared.data.ban;

import com.openmeet.shared.data.storage.IEntity;

import java.sql.Timestamp;
import java.util.HashMap;

public class Ban implements IEntity {

    public static final String BAN = "Ban";
    public static final String BAN_ID = BAN + ".id";
    public static final String BAN_MODERATOR_ID = BAN + ".moderatorId";
    public static final String BAN_DESCRIPTION = BAN + ".description";
    public static final String BAN_START_TIME = BAN + ".startTime";
    public static final String BAN_END_TIME = BAN + ".endTime";
    public static final String BAN_MEETER_ID = BAN + ".meeterId";

    private int id;
    private int moderatorId;
    private String description;
    private Timestamp startTime;
    private Timestamp endTime;
    private int meeterId;

    public Ban() {

    }

    @Override
    public HashMap<String, ?> toHashMap() {

        return new HashMap<>() {{
            put("id", id);
            put("moderatorId", moderatorId);
            put("description", description);
            put("startTime", startTime.toString());

            if (endTime != null) {
                put("endTime", endTime.toString());
            }

            put("meeterId", meeterId);
        }};
    }

    @Override
    public HashMap<String, ?> toHashMap(String... fields) {

        return new HashMap<>() {{
            for (String field : fields) {
                switch (field) {
                    case BAN_ID:
                        put("id", id);
                        break;
                    case BAN_MODERATOR_ID:
                        put("moderatorId", moderatorId);
                        break;
                    case BAN_DESCRIPTION:
                        put("description", description);
                        break;
                    case BAN_START_TIME:
                        put("startTime", startTime.toString());
                        break;
                    case BAN_END_TIME:
                        if (endTime != null) {
                            put("endTime", endTime.toString());
                        }
                        break;
                    case BAN_MEETER_ID:
                        put("meeterId", meeterId);
                        break;
                }
            }
        }};
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
