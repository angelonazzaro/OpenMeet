package com.openmeet.shared.data.ban;

import com.openmeet.shared.data.storage.IEntity;

import java.sql.Timestamp;
import java.util.HashMap;

/**
 * This class represents the Ban Entity.
 *
 * @author Angelo Nazzaro
 * @author Francesco Granozio
 * @see IEntity
 */
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

    /**
     * Returns the Ban as an hashMap.
     *
     * @return the Ban as an hashMap.
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     * @see IEntity
     */
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

    /**
     * Returns the Ban as an hashMap.
     *
     * @param fields the fields to be returned.
     * @return the Ban as an hashMap.
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     * @see IEntity
     */
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

    /**
     * Returns the id of the Ban.
     *
     * @return the id of the Ban.
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id of the Ban.
     *
     * @param id the id of the Ban.
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the id of moderator who created the Ban.
     *
     * @return the id of moderator who created the Ban.
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     */
    public int getModeratorId() {
        return moderatorId;
    }

    /**
     * Sets the id of moderator who created the Ban.
     *
     * @param moderatorId the id of moderator who created the Ban.
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     */
    public void setModeratorId(int moderatorId) {
        this.moderatorId = moderatorId;
    }

    /**
     * Returns the description of the Ban.
     *
     * @return the description of the Ban.
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the Ban.
     *
     * @param description the description of the Ban.
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the start time of the Ban.
     *
     * @return the start time of the Ban.
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     */
    public Timestamp getStartTime() {
        return startTime;
    }

    /**
     * Sets the start time of the Ban.
     *
     * @param startTime the start time of the Ban.
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     */
    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    /**
     * Returns the end time of the Ban.
     *
     * @return the end time of the Ban.
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     */
    public Timestamp getEndTime() {
        return endTime;
    }

    /**
     * Sets the end time of the Ban.
     *
     * @param endTime the end time of the Ban.
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     */
    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    /**
     * Returns the id of the meeter banned.
     *
     * @return the id of the meeter banned.
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     */
    public int getMeeterId() {
        return meeterId;
    }

    /**
     * Sets the id of the meeter banned.
     *
     * @param meeterId the id of the meeter banned.
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     */
    public void setMeeterId(int meeterId) {
        this.meeterId = meeterId;
    }
}
