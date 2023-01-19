package com.openmeet.shared.data.message;

import com.openmeet.shared.data.storage.IEntity;

import java.sql.Timestamp;
import java.util.HashMap;

public class Message implements IEntity {
    public static final String MESSAGE = "Message";
    public static final String MESSAGE_ID = MESSAGE + ".id";
    public static final String MESSAGE_TEXT = MESSAGE + ".text";
    public static final String MESSAGE_SENT_TIME = MESSAGE + ".sentTime";
    public static final String MESSAGE_DELIVERED_TIME = MESSAGE + ".deliveredTime";
    public static final String MESSAGE_READ_TIME = MESSAGE + ".readTime";
    public static final String MESSAGE_MEETER_SENDER = MESSAGE + ".meeterSender";
    public static final String MESSAGE_MEETER_RECEIVER = MESSAGE + ".meeterReceiver";
    private int id;
    private String text;
    private Timestamp sentTime;
    private Timestamp deliveredTime;
    private Timestamp readTime;
    private int meeterSender;
    private int meeterReceiver;

    public Message() {

    }

    @Override
    public HashMap<String, Object> toHashMap() {
        return new HashMap<>() {{
            put("id", id);
            put("text", text);
            put("sentTime", sentTime);
            put("deliveredTime", deliveredTime);
            put("readTime", readTime);
            put("meeterSender", meeterSender);
            put("meeterReceiver", meeterReceiver);
        }};
    }

    @Override
    public HashMap<String, Object> toHashMap(String... fields) {
        return new HashMap<>() {{
            for (String field : fields) {
                switch (field) {
                    case MESSAGE_ID:
                        put("id", id);
                        break;
                    case MESSAGE_TEXT:
                        put("text", text);
                        break;
                    case MESSAGE_SENT_TIME:
                        put("sentTime", sentTime);
                        break;
                    case MESSAGE_DELIVERED_TIME:
                        put("deliveredTime", deliveredTime);
                        break;
                    case MESSAGE_READ_TIME:
                        put("readTime", readTime);
                        break;
                    case MESSAGE_MEETER_SENDER:
                        put("meeterSender", meeterSender);
                        break;
                    case MESSAGE_MEETER_RECEIVER:
                        put("meeterReceiver", meeterReceiver);
                        break;
                }
            }
        }};
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", sentTime=" + sentTime +
                ", deliveredTime=" + deliveredTime +
                ", readTime=" + readTime +
                ", meeterSender=" + meeterSender +
                ", meeterReceiver=" + meeterReceiver +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getSentTime() {
        return sentTime;
    }

    public void setSentTime(Timestamp sentTime) {
        this.sentTime = sentTime;
    }

    public Timestamp getDeliveredTime() {
        return deliveredTime;
    }

    public void setDeliveredTime(Timestamp deliveredTime) {
        this.deliveredTime = deliveredTime;
    }

    public Timestamp getReadTime() {
        return readTime;
    }

    public void setReadTime(Timestamp readTime) {
        this.readTime = readTime;
    }

    public int getMeeterSender() {
        return meeterSender;
    }

    public void setMeeterSender(int meeterSender) {
        this.meeterSender = meeterSender;
    }

    public int getMeeterReceiver() {
        return meeterReceiver;
    }

    public void setMeeterReceiver(int meeterReceiver) {
        this.meeterReceiver = meeterReceiver;
    }
}