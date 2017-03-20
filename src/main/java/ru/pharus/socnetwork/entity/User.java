package ru.pharus.socnetwork.entity;


import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import ru.pharus.socnetwork.entity.enums.Role;

import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * User entity class
 *
 * Getters and Setters, Equals and Hashcode methods
 * Are made by hand without loombok and IntelliJ IDEA generator
 */
public class User {
    private int id;
    @Email
    @NotBlank(message = "Enter login")
    @Size(max = 64)
    private String login;
    @Size(max = 128)
    private String password;
    @Size(max = 64)
    private String fullName;
    private LocalDate birthDate;
    private LocalDateTime registerDate;
    private Role role;

    // TODO: 19.03.2017 Equals and Heshcode

    public User(){

    }

    public User(int id, String login, String password, String fullName, LocalDate birthDate, LocalDateTime registerDate, Role role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.fullName = fullName;
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

    public LocalDate getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDateTime getRegisterDate() {
        return registerDate;
    }
    public void setRegisterDate(LocalDateTime registerDate) {
        this.registerDate = registerDate;
    }

    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }


}
