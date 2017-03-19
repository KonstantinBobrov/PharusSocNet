package ru.pharus.socnetwork.entity;


import ru.pharus.socnetwork.entity.enums.Role;

import java.time.LocalDate;

public class User {
    private int id;
    private String login;
    private String password;
    private String fullName;
    private String city;
    private LocalDate birthDate;
    private LocalDate registerDate;
    private Role role;

    public User(){

    }

    public User(int id, String login, String password, String fullName, String city, LocalDate birthDate, LocalDate registerDate, Role role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.fullName = fullName;
        this.city = city;
        this.birthDate = birthDate;
        this.registerDate = registerDate;
        this.role = role;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDate getRegisterDate() {
        return registerDate;
    }
    public void setRegisterDate(LocalDate registerDate) {
        this.registerDate = registerDate;
    }

    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }
}
