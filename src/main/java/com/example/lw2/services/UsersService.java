package com.example.lw2.services;

import com.example.lw2.models.User;
import com.example.lw2.repos.UsersRepository;

import java.util.ArrayList;


public class UsersService {

  private static UsersService instance = null;

  private UsersRepository repo;

  private UsersService() {}

  public static synchronized UsersService getInstance() {
    if (instance == null)
      instance = new UsersService();
    return instance;
  }

  public static synchronized void setRepository(UsersRepository repo) {
    if (instance == null)
      instance = new UsersService();
    instance.repo = repo;
  }

  public User saveUser(User user) {
    return repo.saveAndFlush(user);
  }

  public ArrayList<User> getAllUsers() {
    return new ArrayList<>(repo.findAll());
  }

  public User getUserByUsernameAndPassword(String username, String passwordHash) {
    return repo.findAll().stream().filter(x -> x.getUsername().equals(username) &&
        x.getPasswordHash().equals(passwordHash)).findFirst().orElse(null);
  }

  public boolean getUsernameTaken(String username) {
    return repo.findAll().stream().anyMatch(x -> x.getUsername().equals(username));
  }

}