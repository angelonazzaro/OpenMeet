package com.openmeet.webapp.dataLayer.moderator;

import com.openmeet.shared.data.storage.IEntity;
import com.openmeet.shared.utils.PasswordEncrypter;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

/**
 * This class represents the Moderator Entity.
 *
 * @author Angelo Nazzaro
 * @see IEntity
 */
public class Moderator implements IEntity {

    public static final String MODERATOR = "Moderator";
    public static final String MODERATOR_ID = MODERATOR + ".id";
    public static final String MODERATOR_EMAIL = MODERATOR + ".email";
    public static final String MODERATOR_MODERATOR_NAME = MODERATOR + ".moderatorName";
    public static final String MODERATOR_MODERATOR_SURNAME = MODERATOR + ".moderatorSurname";
    public static final String MODERATOR_PWD = MODERATOR + ".pwd";
    public static final String MODERATOR_PROFILE_PIC = MODERATOR + ".profilePic";
    private int id;
    private String email;
    private String moderatorName;
    private String moderatorSurname;
    private String pwd;
    private String profilePic;

    public Moderator() {

    }

    /**
     * Returns the Moderator as an hashMap.
     *
     * @return the Moderator as an hashMap.
     * @author Angelo Nazzaro
     * @see IEntity
     */
    @Override
    public HashMap<String, ?> toHashMap() {

        return new HashMap<>() {
            {
                put("id", id);
                put("email", email);
                put("moderatorName", moderatorName);
                put("moderatorSurname", moderatorSurname);
                put("pwd", pwd);

                if (profilePic != null) {
                    put("profilePic", profilePic);
                }
            }
        };
    }

    /**
     * Returns the Moderator as an hashMap.
     *
     * @param fields the fields to be returned.
     * @return the Moderator as an hashMap.
     * @author Angelo Nazzaro
     * @see IEntity
     */
    @Override
    public HashMap<String, ?> toHashMap(String... fields) {

        return new HashMap<>() {
            {
                for (String field : fields) {
                    switch (field) {
                        case MODERATOR_ID:
                            put("id", id);
                            break;
                        case MODERATOR_EMAIL:
                            put("email", email);
                            break;
                        case MODERATOR_MODERATOR_NAME:
                            put("moderatorName", moderatorName);
                            break;
                        case MODERATOR_MODERATOR_SURNAME:
                            put("moderatorSurname", moderatorSurname);
                            break;
                        case MODERATOR_PWD:
                            put("pwd", pwd);
                            break;
                        case MODERATOR_PROFILE_PIC:
                            if (profilePic != null) {
                                put("profilePic", profilePic);
                            }
                            break;
                    }
                }
            }
        };
    }

    @Override
    public String toString() {
        return "Moderator{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", moderatorName='" + moderatorName + '\'' +
                ", moderatorSurname='" + moderatorSurname + '\'' +
                ", pwd='" + pwd + '\'' +
                ", profilePic='" + profilePic + '\'' +
                '}';
    }

    /**
     * Returns the Moderator id.
     *
     * @return the Moderator id.
     * @author Angelo Nazarro
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the Moderator id.
     *
     * @param id the Moderator id.
     * @author Angelo Nazarro
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the Moderator email.
     *
     * @return the Moderator email.
     * @author Angelo Nazarro
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the Moderator email.
     *
     * @param email the Moderator email.
     * @author Angelo Nazarro
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the Moderator name.
     *
     * @return the Moderator name.
     * @author Angelo Nazarro
     */
    public String getModeratorName() {
        return moderatorName;
    }

    /**
     * Sets the Moderator name.
     *
     * @param moderatorName the Moderator name.
     * @author Angelo Nazarro
     */
    public void setModeratorName(String moderatorName) {
        this.moderatorName = moderatorName;
    }

    /**
     * Returns the Moderator surname.
     *
     * @return the Moderator surname.
     * @author Angelo Nazarro
     */
    public String getModeratorSurname() {
        return moderatorSurname;
    }

    /**
     * Sets the Moderator surname.
     *
     * @param moderatorSurname the Moderator surname.
     * @author Angelo Nazarro
     */
    public void setModeratorSurname(String moderatorSurname) {
        this.moderatorSurname = moderatorSurname;
    }

    /**
     * Returns the Moderator password encrypted with SHA1.
     *
     * @return the Moderator password.
     * @author Angelo Nazarro
     */
    public String getPwd() {
        return pwd;
    }

    /**
     * Sets the Moderator password encrypted with SHA1.
     *
     * @param pwd the Moderator password.
     * @author Angelo Nazarro
     */
    public void setPwd(String pwd) {
        try {

            this.pwd = PasswordEncrypter.sha1(pwd);

        } catch (NoSuchAlgorithmException e) {

            e.printStackTrace();
        }
    }

    /**
     * Returns the Moderator profile picture path.
     *
     * @return the Moderator profile picture path.
     * @author Angelo Nazarro
     */
    public String getProfilePic() {
        return profilePic;
    }

    /**
     * Sets the Moderator profile picture path.
     *
     * @param profilePic the Moderator profile picture path.
     * @author Angelo Nazarro
     */
    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

}
