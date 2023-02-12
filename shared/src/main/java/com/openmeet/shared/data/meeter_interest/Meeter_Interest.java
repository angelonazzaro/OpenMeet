package com.openmeet.shared.data.meeter_interest;

import com.openmeet.shared.data.storage.IEntity;

import java.util.HashMap;

/**
 * This class represents the Meeter_Interest Entity.
 *
 * @see IEntity
 *
 * @author Angelo Nazzaro
 * @author Francesco Granozio
 * */
public class Meeter_Interest implements IEntity {
    public static final String MEETER_INTEREST = "Meeter_Interest";
    public static final String MEETER_INTEREST_ID = MEETER_INTEREST + ".id";
    public static final String MEETER_INTEREST_INTEREST_ID = MEETER_INTEREST + ".interestId";
    public static final String MEETER_INTEREST_MEETER_ID = MEETER_INTEREST + ".meeterId";
    private int id;
    private int interestId;
    private int meeterId;

    public Meeter_Interest() {
    }

    /**
     * Returns the Meeter_Interest as an hashMap.
     *
     * @see IEntity
     *
     * @return the Meeter_Interest as an hashMap.
     *
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     * */
    @Override
    public HashMap<String, Object> toHashMap() {
        return new HashMap<>() {{
            put("id", id);
            put("interestId", interestId);
            put("meeterId", meeterId);
        }};
    }

    /**
     * Returns the Meeter_Interest as an hashMap.
     *
     * @see IEntity
     *
     * @param fields the fields to be returned.
     * @return the Meeter_Interest as an hashMap.
     *
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     * */
    @Override
    public HashMap<String, Object> toHashMap(String... fields) {
        return new HashMap<>() {{
            for (String field : fields) {
                switch (field) {
                    case MEETER_INTEREST_ID:
                        put("id", id);
                        break;
                    case MEETER_INTEREST_INTEREST_ID:
                        put("interestId", interestId);
                        break;
                    case MEETER_INTEREST_MEETER_ID:
                        put("meeterId", meeterId);
                        break;
                }
            }
        }};
    }

    /**
     * Returns the Meeter_Interest id.
     *
     * @return the Meeter_Interest id.
     *
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the Meeter_Interest id.
     *
     * @param id the Meeter_Interest id.
     *
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     * */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the Meeter_Interest interestId.
     *
     * @return the Meeter_Interest interestId.
     *
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     */
    public int getInterestId() {
        return interestId;
    }

    /**
     * Sets the Meeter_Interest interestId.
     *
     * @param interestId the Meeter_Interest interestId.
     *
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     * */
    public void setInterestId(int interestId) {
        this.interestId = interestId;
    }

    /**
     * Returns the Meeter_Interest meeterId.
     *
     * @return the Meeter_Interest meeterId.
     *
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     */
    public int getMeeterId() {
        return meeterId;
    }

    /**
     * Sets the Meeter_Interest meeterId.
     *
     * @param meeterId the Meeter_Interest meeterId.
     *
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     * */
    public void setMeeterId(int meeterId) {
        this.meeterId = meeterId;
    }
}