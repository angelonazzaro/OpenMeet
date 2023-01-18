package com.openmeet.shared.data.image;

import com.openmeet.shared.data.storage.IEntity;

import java.util.HashMap;

public class Image implements IEntity {

    public static final String IMAGE = "Image";
    public static final String IMAGE_ID = IMAGE + ".id";
    public static final String IMAGE_PATH = IMAGE + ".path";
    public static final String IMAGE_MEETER_ID = IMAGE + ".meeterId";
    private int id;
    private String path;
    private int meeterId;

    public Image(){

    }

    @Override
    public HashMap<String, ?> toHashMap() {

        return new HashMap<>() {
            {
                put("id", id);
                put("path", path);
                put("meeterId", meeterId);
            }
        };
    }

    @Override
    public String toString() {
        return "Image.Image{" +
                "id=" + id +
                ", path='" + path + '\'' +
                ", meeterId=" + meeterId +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getMeeterId() {
        return meeterId;
    }

    public void setMeeterId(int meeterId) {
        this.meeterId = meeterId;
    }
}
