package com.openmeet.shared.data.meeter_interest;

import com.openmeet.shared.data.storage.IEntity;

import java.util.HashMap;

public class Meeter_Interest implements IEntity {
    public static final String MEETER_INTEREST = "Meeter_Interest";
    public static final String MEETER_INTEREST_ID = MEETER_INTEREST + ".id";
    public static final String MEETER_INTEREST_MEETER_ID = MEETER_INTEREST + ".meeterId";
    private int id;
    private int meeterId;

    public Meeter_Interest() {
    }

    @Override
    public HashMap<String, Object> toHashMap() {
        return new HashMap<>() {{
            put("id", id);
            put("meeterId", meeterId);
        }};
    }

    @Override
    public HashMap<String, Object> toHashMap(String... fields) {
        return new HashMap<>() {{
            for (String field : fields) {
                switch (field) {
                    case MEETER_INTEREST_ID:
                        put("id", id);
                        break;
                    case MEETER_INTEREST_MEETER_ID:
                        put("meeterId", meeterId);
                        break;
                }
            }
        }};
    }

    @Override
    public String toString() {
        return "Meeter_Interest{" +
                "id=" + id +
                ", meeterId=" + meeterId +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMeeterId() {
        return meeterId;
    }

    public void setMeeterId(int meeterId) {
        this.meeterId = meeterId;
    }
}