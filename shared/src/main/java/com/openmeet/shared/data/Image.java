package com.openmeet.shared.data;

public class Image {

    private int id;
    private String path;
    private int meeterId;

    public Image(){

    }

    @Override
    public String toString() {
        return "Image{" +
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
