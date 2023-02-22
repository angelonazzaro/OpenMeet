package com.openmeet.shared.data.interest;

import com.openmeet.shared.data.storage.IEntity;

import java.util.HashMap;

/**
 * This class represents the Interest Entity.
 *
 * @author Angelo Nazzaro
 * @author Francesco Granozio
 * @see IEntity
 */
public class Interest implements IEntity {
    public static final String INTEREST = "Interest";
    public static final String INTEREST_ID = INTEREST + ".id";
    public static final String INTEREST_DESCRIPTION = INTEREST + ".description";
    private int id;
    private String description;

    public Interest() {
    }

    /**
     * Returns the Interest as an hashMap.
     *
     * @return the Interest as an hashMap.
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     * @see IEntity
     */
    @Override
    public HashMap<String, Object> toHashMap() {

        return new HashMap<>() {{
            put("id", id);
            put("description", description);
        }};
    }

    /**
     * Returns the Interest as an hashMap.
     *
     * @param fields the fields to be returned.
     * @return the Interest as an hashMap.
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     * @see IEntity
     */
    @Override
    public HashMap<String, Object> toHashMap(String... fields) {

        return new HashMap<>() {{
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
        }};
    }

    @Override
    public String toString() {
        return "Interest{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }

    /**
     * Returns the id of the Interest.
     *
     * @return the id of the Interest.
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id of the Interest.
     *
     * @param id the id of the Interest.
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the description of the Interest.
     *
     * @return the description of the Interest.
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the Interest.
     *
     * @param description the description of the Interest.
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
