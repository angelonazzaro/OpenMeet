package com.openmeet.shared.data.meeter;

import com.openmeet.shared.data.storage.IEntity;
import com.openmeet.shared.utils.PasswordEncrypter;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

public class Meeter implements IEntity {

    public static final String MEETER = "Meeter";
    private int id;
    private String email;
    private String meeterName;
    private String meeterSurname;
    //TODO: Hash Password
    private String pwd;
    private String biography;
    private Date birthDate;
    private byte[] publicKey = new byte[128];

    public Meeter(){

    }

    public HashMap<String, ?> toHashMap() {

        return new HashMap<>() {
            {
                put("id", id);
                put("email", email);
                put("meeterName", meeterName);
                put("meeterSurname", meeterSurname);
                put("pwd", pwd);
                put("birthDate", birthDate.toString());
                put("publicKey", publicKey);
            }
        };
    }

    @Override
    public String toString() {
        return "Meeter{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", meeterName='" + meeterName + '\'' +
                ", meeterSurname='" + meeterSurname + '\'' +
                ", pwd='" + pwd + '\'' +
                ", biography='" + biography + '\'' +
                ", birthDate=" + birthDate +
                ", publicKey=" + Arrays.toString(publicKey) +
                '}';
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

    public String getMeeterName() {
        return meeterName;
    }

    public void setMeeterName(String meeterName) {
        this.meeterName = meeterName;
    }

    public String getMeeterSurname() {
        return meeterSurname;
    }

    public void setMeeterSurname(String meeterSurname) {
        this.meeterSurname = meeterSurname;
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

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public byte[] getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(byte[] publicKey) {
        this.publicKey = publicKey;
    }

    public String getFullName() {
        return meeterName + " " + meeterSurname;
    }
}
