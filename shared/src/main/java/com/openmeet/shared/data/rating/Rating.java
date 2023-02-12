package com.openmeet.shared.data.rating;

import com.openmeet.shared.data.storage.IEntity;

import java.sql.Timestamp;
import java.util.HashMap;

import java.sql.Timestamp;
import java.util.HashMap;

/**
 * This class represents the Rating Entity.
 *
 * @see IEntity
 *
 * @author Angelo Nazzaro
 * @author Francesco Granozio
 * */
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

    /**
     * Returns the Rating as an hashMap.
     *
     * @see IEntity
     *
     * @return the Rating as an hashMap.
     *
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     * */
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

    /**
     * Returns the Rating as an hashMap.
     *
     * @see IEntity
     *
     * @param fields the fields to be returned.
     * @return the Rating as an hashMap.
     *
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     * */
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

    /**
     * Returns the id of the Rating.
     *
     * @return the id of the Rating.
     *
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     * */
    public int getId() {
        return id;
    }

    /**
     * Sets the id of the Rating.
     *
     * @param id the id of the Rating.
     *
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     * */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the id of the meeter who created the Rating.
     *
     * @return the id of the meeter who created the Rating.
     *
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     * */
    public int getMeeterRater() {
        return meeterRater;
    }

    /**
     * Sets the id of the meeter who created the Rating.
     *
     * @param meeterRater the id of the meeter who created the Rating.
     *
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     * */
    public void setMeeterRater(int meeterRater) {
        this.meeterRater = meeterRater;
    }

    /**
     * Returns the id of the meeter who is rated.
     *
     * @return the id of the meeter who is rated.
     *
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     * */
    public int getMeeterRated() {
        return meeterRated;
    }

    /**
     * Sets the id of the meeter who is rated.
     *
     * @param meeterRated the id of the meeter who is rated.
     *
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     * */
    public void setMeeterRated(int meeterRated) {
        this.meeterRated = meeterRated;
    }

    /**
     * Returns the type of the Rating.
     *
     * @return the type of the Rating.
     *
     * @apiNote true if the meeter is liked, false otherwise.
     *
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     * */
    public boolean isType() {
        return type;
    }

    /**
     * Sets the type of the Rating.
     *
     * @param type the type of the Rating.
     *
     * @apiNote true if the meeter is liked, false otherwise.
     *
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     * */
    public void setType(boolean type) {
        this.type = type;
    }

    /**
     * Returns the creation date of the Rating.
     *
     * @return the creation date of the Rating.
     *
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     * */
    public Timestamp getCreationDate() {
        return creationDate;
    }

    /**
     * Sets the creation date of the Rating.
     *
     * @param creationDate the creation date of the Rating.
     *
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     * */
    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }
}
