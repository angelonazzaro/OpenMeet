package com.openmeet.webapp.dataLayer.moderator;

import com.openmeet.shared.data.storage.IEntity;
import com.openmeet.shared.utils.PasswordEncrypter;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class Moderator implements IEntity {

    public static final String MODERATOR = "Moderator";

    private int id;

    private String email;

    private String moderatorName;

    private String moderatorSurname;

    private String pwd;

    private String profilePic;

    public Moderator() {

    }

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

    @Override
    public String toString() {
        return "Moderator{" + "id=" + id + ", email='" + email + '\'' + ", moderatorName='" + moderatorName + '\'' + ", moderatorSurname='" + moderatorSurname + '\'' + ", pwd='" + pwd + '\'' + ", profilePic='" + profilePic + '\'' + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getModeratorName() {
        return moderatorName;
    }

    public void setModeratorName(String moderatorName) {
        this.moderatorName = moderatorName;
    }

    public String getModeratorSurname() {
        return moderatorSurname;
    }

    public void setModeratorSurname(String moderatorSurname) {
        this.moderatorSurname = moderatorSurname;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        try {

            this.pwd = PasswordEncrypter.sha1(pwd);

        } catch (NoSuchAlgorithmException e) {

            e.printStackTrace();
        }
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getFullName() {
        return moderatorName + " " + moderatorSurname;
    }
}
