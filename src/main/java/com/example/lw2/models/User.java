package com.example.lw2.models;

import jakarta.persistence.*;


@Entity
@Table(name="users")
public class User {

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="user_id")
  private int id;

  @Column(name="username", unique = true, nullable = false, length = 50)
  private String username;

  @Column(name="password_hash", nullable = false)
  private String passwordHash;

  public User() {}

  public User(String username, String passwordHash) {
    this.username = username;
    this.passwordHash = passwordHash;
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

  public String getPasswordHash() {
    return passwordHash;
  }

  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }

}