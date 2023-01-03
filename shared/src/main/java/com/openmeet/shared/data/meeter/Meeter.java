package com.openmeet.shared.data.meeter;

import com.openmeet.shared.data.storage.IEntity;
import com.openmeet.shared.utils.PasswordEncrypter;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;

public class Meeter implements IEntity {

    public static final String MEETER = "Meeter";
    private int id;
    private String email;
    private String name;
    private String surname;
    //TODO: Hash Password
    private String pwd;
    private String biography;
    private Date birthDate;

    public Meeter(){

    }

    public HashMap<String, ?> toHashMap() {

        return new HashMap<>() {
            {
                put("id", id);
                put("email", email);
                put("name", name);
                put("surname", surname);
                put("pwd", pwd);
                put("birthDate", birthDate.toString());
            }
        };
    }

    @Override
    public String toString() {
        return "Meeter.Meeter{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", password='" + pwd + '\'' +
                ", biography='" + biography + '\'' +
                ", birthDate=" + birthDate +
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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
}
