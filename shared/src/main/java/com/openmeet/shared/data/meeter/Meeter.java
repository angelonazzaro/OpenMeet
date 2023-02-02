package com.openmeet.shared.data.meeter;

import com.openmeet.shared.data.storage.IEntity;
import com.openmeet.shared.utils.PasswordEncrypter;

import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.HashMap;

public class Meeter implements IEntity {

    public static final String MEETER = "Meeter";
    public static final String MEETER_ID = MEETER + ".id";
    public static final String MEETER_EMAIL = MEETER + ".email";
    public static final String MEETER_MEETER_NAME = MEETER + ".meeterName";
    public static final String MEETER_MEETER_SURNAME = MEETER + ".meeterSurname";

    public static final String MEETER_GENDER = MEETER  + ".gender";

    public static final String MEETER_SEARCHING_GENDER = MEETER + ".searchingGender";

    public static final String MEETER_CITY = MEETER + ".city";
    public static final String MEETER_PWD = MEETER + ".pwd";
    public static final String MEETER_BIOGRAPHY = MEETER + ".biography";
    public static final String MEETER_BIRTHDATE = MEETER + ".birthdate";
    public static final String MEETER_PUBLIC_KEY = MEETER + ".publicKey";
    private int id;
    private String email;
    private String meeterName;
    private String meeterSurname;
    private String pwd;

    private char gender;
    private char searchingGender;
    private String city;
    private String biography;
    private Date birthdate;
    private String publicKey;

    public Meeter() {

    }

    public HashMap<String, ?> toHashMap() {

        return new HashMap<>() {{
            put("id", id);
            put("email", email);
            put("meeterName", meeterName);
            put("meeterSurname", meeterSurname);

            if (biography != null) {
                put("biography", biography);
            }
            put("pwd", pwd);
            put("gender", gender);
            put("searchingGender", searchingGender);
            put("city", city);
            put("birthdate", birthdate.toString());

            if (publicKey != null && publicKey.length() > 0) {
                put("publicKey", publicKey);
            }
        }};
    }

    @Override
    public HashMap<String, ?> toHashMap(String... fields) {

        return new HashMap<>() {{
            for (String field : fields) {
                switch (field) {
                    case MEETER_ID:
                        put("id", id);
                        break;
                    case MEETER_EMAIL:
                        put("email", email);
                        break;
                    case MEETER_MEETER_NAME:
                        put("meeterName", meeterName);
                        break;
                    case MEETER_MEETER_SURNAME:
                        put("meeterSurname", meeterSurname);
                        break;
                    case MEETER_GENDER:
                        put("gender", gender);
                        break;
                    case MEETER_SEARCHING_GENDER:
                        put("searchingGender", searchingGender);
                        break;
                    case MEETER_CITY:
                        put("city", city);
                        break;
                    case MEETER_PWD:
                        put("pwd", pwd);
                        break;
                    case MEETER_BIOGRAPHY:
                        if (biography != null) {
                            put("biography", biography);
                        }
                        break;
                    case MEETER_BIRTHDATE:
                        put("birthdate", birthdate.toString());
                        break;
                    case MEETER_PUBLIC_KEY:
                        if (publicKey != null && publicKey.length() > 0) {
                            put("publicKey", publicKey);
                        }
                        break;
                }
            }
        }};
    }

    @Override
    public String toString() {
        return "Meeter{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", meeterName='" + meeterName + '\'' +
                ", meeterSurname='" + meeterSurname + '\'' +
                ", pwd='" + pwd + '\'' +
                ", gender='" + gender + '\'' +
                ", searchingGender='" + searchingGender + '\'' +
                ", city='" + city + '\'' +
                ", biography='" + biography + '\'' +
                ", birthdate=" + birthdate +
                ", publicKey=" + publicKey +
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

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public char getSearchingGender() {
        return searchingGender;
    }

    public void setSearchingGender(char searchingGender) {
        this.searchingGender = searchingGender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

}
