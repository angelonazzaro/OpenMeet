package com.openmeet.shared.data.image;

import com.openmeet.shared.data.storage.IEntity;

import java.util.HashMap;

/**
 * This class represents the Image Entity.
 *
 * @see IEntity
 *
 * @author Angelo Nazzaro
 * @author Francesco Granozio
 * */
public class Image implements IEntity {

    public static final String IMAGE = "Image";
    public static final String IMAGE_ID = IMAGE + ".id";
    public static final String IMAGE_PATH = IMAGE + ".path";
    public static final String IMAGE_MEETER_ID = IMAGE + ".meeterId";
    private int id;
    private String path;
    private int meeterId;

    public Image() {

    }

    /**
     * Returns the Image as an hashMap.
     *
     * @see IEntity
     *
     * @return the Image as an hashMap.
     *
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     * */
    @Override
    public HashMap<String, ?> toHashMap() {

        return new HashMap<>() {{
            put("id", id);
            put("path", path);
            put("meeterId", meeterId);
        }};
    }

    /**
     * Returns the Image as an hashMap.
     *
     * @see IEntity
     *
     * @param fields the fields to be returned.
     * @return the Image as an hashMap.
     *
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     * */
    @Override
    public HashMap<String, ?> toHashMap(String... fields) {

        return new HashMap<>() {{
            for (String field : fields) {
                switch (field) {
                    case IMAGE_ID:
                        put("id", id);
                        break;
                    case IMAGE_PATH:
                        put("path", path);
                        break;
                    case IMAGE_MEETER_ID:
                        put("meeterId", meeterId);
                        break;
                }
            }
        }};
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", path='" + path + '\'' +
                ", meeterId=" + meeterId +
                '}';
    }

    /**
     * Returns the id of the Image.
     *
     * @return the id of the Image.
     *
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     * */
    public int getId() {
        return id;
    }

    /**
     * Sets the id of the Image.
     *
     * @param id the id of the Image.
     *
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     * */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the path of the Image.
     *
     * @return the path of the Image.
     *
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     * */
    public String getPath() {
        return path;
    }

    /**
     * Sets the path of the Image.
     *
     * @param path the path of the Image.
     *
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     * */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Returns the meeterId of the Image.
     *
     * @return the meeterId of the Image.
     *
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     * */
    public int getMeeterId() {
        return meeterId;
    }

    /**
     * Sets the meeterId of the Image.
     *
     * @param meeterId the meeterId of the Image.
     *
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     * */
    public void setMeeterId(int meeterId) {
        this.meeterId = meeterId;
    }
}
