package com.openmeet.shared.data.message;

import com.openmeet.shared.data.storage.IEntity;

import java.sql.Timestamp;
import java.util.HashMap;

/**
 * This class represents the Message Entity.
 *
 * @see IEntity
 *
 * @author Angelo Nazzaro
 * @author Francesco Granozio
 * */
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

    /**
     * Returns the Message as an hashMap.
     *
     * @see IEntity
     *
     * @return the Message as an hashMap.
     *
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     * */
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

    /**
     * Returns the Message as an hashMap.
     *
     * @see IEntity
     *
     * @param fields the fields to be returned.
     * @return the Message as an hashMap.
     *
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     * */
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

    /**
     * Returns the id of the Message.
     *
     * @return the id of the Message.
     *
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     * */
    public int getId() {
        return id;
    }

    /**
     * Sets the id of the Message.
     *
     * @param id the id of the Message.
     *
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     * */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the text of the Message.
     *
     * @return the text of the Message.
     *
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     * */
    public String getText() {
        return text;
    }

    /**
     * Sets the text of the Message.
     *
     * @param text the text of the Message.
     *
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     * */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Returns the sentTime of the Message.
     *
     * @return the sentTime of the Message.
     *
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     * */
    public Timestamp getSentTime() {
        return sentTime;
    }

    /**
     * Sets the sentTime of the Message.
     *
     * @param sentTime the sentTime of the Message.
     *
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     * */
    public void setSentTime(Timestamp sentTime) {
        this.sentTime = sentTime;
    }

    /**
     * Returns the deliveredTime of the Message.
     *
     * @return the deliveredTime of the Message.
     *
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     * */
    public Timestamp getDeliveredTime() {
        return deliveredTime;
    }

    /**
     * Sets the deliveredTime of the Message.
     *
     * @param deliveredTime the deliveredTime of the Message.
     *
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     * */
    public void setDeliveredTime(Timestamp deliveredTime) {
        this.deliveredTime = deliveredTime;
    }

    /**
     * Returns the readTime of the Message.
     *
     * @return the readTime of the Message.
     *
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     * */
    public Timestamp getReadTime() {
        return readTime;
    }

    /**
     * Sets the readTime of the Message.
     *
     * @param readTime the readTime of the Message.
     *
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     * */
    public void setReadTime(Timestamp readTime) {
        this.readTime = readTime;
    }

    /**
     * Returns the meeter id of the sender of the Message.
     *
     * @return the meeter id of the sender of the Message.
     *
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     * */
    public int getMeeterSender() {
        return meeterSender;
    }

    /**
     * Sets the meeter id of the sender of the Message.
     *
     * @param meeterSender the meeter id of the sender of the Message.
     *
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     * */
    public void setMeeterSender(int meeterSender) {
        this.meeterSender = meeterSender;
    }

    /**
     * Returns the meeter id of the receiver of the Message.
     *
     * @return the meeter id of the receiver of the Message.
     *
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     * */
    public int getMeeterReceiver() {
        return meeterReceiver;
    }

    /**
     * Sets the meeter id of the receiver of the Message.
     *
     * @param meeterReceiver the meeter id of the receiver of the Message.
     *
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     * */
    public void setMeeterReceiver(int meeterReceiver) {
        this.meeterReceiver = meeterReceiver;
    }
}