package com.openmeet.shared.data.rating;

import com.openmeet.shared.data.storage.IEntity;

import java.sql.Timestamp;
import java.util.HashMap;

import java.sql.Timestamp;
import java.util.HashMap;

public class Rating implements IEntity {
    public static final String RATING = "Rating";
    public static final String RATING_ID = RATING + ".id";
    public static final String RATING_MEETER_RATER = RATING + ".meeterRater";
    public static final String RATING_MEETER_RATED = RATING + ".meeterRated";
    public static final String RATING_TYPE = RATING + ".type";
    public static final String RATING_CREATION_DATE = RATING + ".creationDate";
    private int id;
    private int meeterRater;
    private int meeterRated;
    private boolean type;
    private Timestamp creationDate;

    public Rating() {

    }

    @Override
    public HashMap<String, Object> toHashMap() {
        return new HashMap<>() {{
            put("id", id);
            put("meeterRater", meeterRater);
            put("meeterRated", meeterRated);
            put("type", type);
            put("creationDate", creationDate);
        }};
    }

    @Override
    public HashMap<String, Object> toHashMap(String... fields) {
        return new HashMap<>() {{
            for (String field : fields) {
                switch (field) {
                    case RATING_ID:
                        put("id", id);
                        break;
                    case RATING_MEETER_RATER:
                        put("meeterRater", meeterRater);
                        break;
                    case RATING_MEETER_RATED:
                        put("meeterRated", meeterRated);
                        break;
                    case RATING_TYPE:
                        put("type", type);
                        break;
                    case RATING_CREATION_DATE:
                        put("creationDate", creationDate);
                        break;
                }
            }
        }};
    }

    @Override
    public String toString() {
        return "Rating{" +
                "id=" + id +
                ", meeterRater=" + meeterRater +
                ", meeterRated=" + meeterRated +
                ", type=" + type +
                ", creationDate=" + creationDate +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMeeterRater() {
        return meeterRater;
    }

    public void setMeeterRater(int meeterRater) {
        this.meeterRater = meeterRater;
    }

    public int getMeeterRated() {
        return meeterRated;
    }

    public void setMeeterRated(int meeterRated) {
        this.meeterRated = meeterRated;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }
}
