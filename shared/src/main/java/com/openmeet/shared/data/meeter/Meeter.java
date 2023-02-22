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

    public static final String MEETER_GENDER = MEETER + ".gender";

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

    private String gender;
    private String searchingGender;
    private String city;
    private String biography;
    private Date birthdate;
    private String publicKey;

    public Meeter() {

    }

    /**
     * Returns the Meeter as an hashMap.
     *
     * @return the Meeter as an hashMap.
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     * @see IEntity
     */
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

    /**
     * Returns the Meeter as an hashMap.
     *
     * @param fields the fields to be returned.
     * @return the Meeter as an hashMap.
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     * @see IEntity
     */
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


    /**
     * Returns the id of the Meeter.
     *
     * @return the id of the Meeter.
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id of the Meeter.
     *
     * @param id the id of the Meeter.
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the email of the Meeter.
     *
     * @return the email of the Meeter.
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the Meeter.
     *
     * @param email the email of the Meeter.
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the name of the Meeter.
     *
     * @return the name of the Meeter.
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     */
    public String getMeeterName() {
        return meeterName;
    }

    /**
     * Sets the name of the Meeter.
     *
     * @param meeterName the name of the Meeter.
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     */
    public void setMeeterName(String meeterName) {
        this.meeterName = meeterName;
    }

    /**
     * Returns the surname of the Meeter.
     *
     * @return the surname of the Meeter.
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     */
    public String getMeeterSurname() {
        return meeterSurname;
    }

    /**
     * Sets the surname of the Meeter.
     *
     * @param meeterSurname the surname of the Meeter.
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     */
    public void setMeeterSurname(String meeterSurname) {
        this.meeterSurname = meeterSurname;
    }

    /**
     * Returns the password of the Meeter.
     *
     * @return the password of the Meeter.
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     */
    public String getPwd() {
        return pwd;
    }

    /**
     * Sets the password of the Meeter using SHA1 encryption .
     *
     * @param pwd     the password of the Meeter.
     * @param encrypt true if the password must be encrypted, false otherwise.
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     */
    public void setPwd(String pwd, boolean encrypt) {

        if (encrypt) {
            try {
                this.pwd = PasswordEncrypter.sha1(pwd);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        } else {
            this.pwd = pwd;
        }
    }

    /**
     * Returns the gender of the Meeter.
     *
     * @return the gender of the Meeter.
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the gender of the Meeter.
     *
     * @param gender the gender of the Meeter.
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Returns the searching gender of the Meeter.
     *
     * @return the searching gender of the Meeter.
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     */
    public String getSearchingGender() {
        return searchingGender;
    }

    /**
     * Sets the searching gender of the Meeter.
     *
     * @param searchingGender the searching gender of the Meeter.
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     */
    public void setSearchingGender(String searchingGender) {
        this.searchingGender = searchingGender;
    }

    /**
     * Returns the city of the Meeter.
     *
     * @return the city of the Meeter.
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city of the Meeter.
     *
     * @param city the city of the Meeter.
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Returns the biography of the Meeter.
     *
     * @return the biography of the Meeter.
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     */
    public String getBiography() {
        return biography;
    }

    /**
     * Sets the biography of the Meeter.
     *
     * @param biography the biography of the Meeter.
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     */
    public void setBiography(String biography) {
        this.biography = biography;
    }

    /**
     * Returns the birthdate of the Meeter.
     *
     * @return the birthdate of the Meeter.
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     */
    public Date getBirthdate() {
        return birthdate;
    }

    /**
     * Sets the birthdate of the Meeter.
     *
     * @param birthdate the birthdate of the Meeter.
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     */
    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    /**
     * Returns the age of the Meeter.
     *
     * @return the age of the Meeter.
     * @author Angelo
     * @author Francesco
     */
    public String getPublicKey() {
        return publicKey;
    }

    /**
     * Sets the public key of the Meeter.
     *
     * @param publicKey the public key of the Meeter.
     * @author Angelo
     * @author Francesco
     */
    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

}
