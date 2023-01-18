package com.openmeet.shared.data.interest;

import com.openmeet.shared.data.storage.IEntity;

import java.util.HashMap;

public class Interest implements IEntity {
    public static final String INTEREST = "Interest";
    public static final String INTEREST_ID = INTEREST + ".id";
    public static final String INTEREST_DESCRIPTION = INTEREST + ".description";
    private int id;
    private String description;

    public Interest() {
    }

    @Override
    public HashMap<String, Object> toHashMap() {

        return new HashMap<>() {
            {
                put("id", id);
                put("description", description);
            }
        };
    }

    @Override
    public HashMap<String, Object> toHashMap(String... fields) {

        return new HashMap<>() {
            {
                for (String field : fields) {
                    switch (field) {
                        case INTEREST_ID:
                            put("id", id);
                            break;
                        case INTEREST_DESCRIPTION:
                            put("description", description);
                            break;
                    }
                }
            }
        };
    }

    @Override
    public String toString() {
        return "Interest{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
