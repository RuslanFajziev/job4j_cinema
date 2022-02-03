package ru.job4j.cinema.model;

import java.util.Objects;

public class AccountCinema {
    private int id;
    private String username;
    private String email;
    private String phone;

    public AccountCinema(int id, String username, String email, String phone) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "AccountCinema{"
                + "id=" + id
                + ", username='" + username + '\''
                + ", email='" + email + '\''
                + ", phone='" + phone + '\''
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AccountCinema accountCinema = (AccountCinema) o;
        return id == accountCinema.id && Objects.equals(username, accountCinema.username)
                && Objects.equals(email, accountCinema.email) && Objects.equals(phone, accountCinema.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, phone);
    }
}