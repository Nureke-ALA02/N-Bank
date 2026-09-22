package com.bank.model;

import java.time.LocalDate;

public class User {

    private Long id;
    private String firsname;
    private String lastname;
    private String iin;
    private LocalDate brithDate;
    private String email;
    private String password;

    public User(Long id, String firsname, String lastname, String iin, LocalDate brithDate, String email, String password) {
        this.id = id;
        this.firsname = firsname;
        this.lastname = lastname;
        this.iin = iin;
        this.brithDate = brithDate;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirsname() {
        return firsname;
    }

    public void setFirsname(String firsname) {
        this.firsname = firsname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getIin() {
        return iin;
    }

    public void setIin(String iin) {
        this.iin = iin;
    }

    public LocalDate getBrithDate() {
        return brithDate;
    }

    public void setBrithDate(LocalDate brithDate) {
        this.brithDate = brithDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
