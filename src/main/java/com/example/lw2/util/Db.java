package com.example.lw2.util;

import com.example.lw2.models.User;
import com.example.lw2.services.UsersService;

import java.util.ArrayList;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class Db {

  public static String getMD5Hash(String value) {
    try {
      MessageDigest md5 = MessageDigest.getInstance("MD5");
      md5.update(StandardCharsets.UTF_8.encode(value));
      return String.format("%032x", new BigInteger(1, md5.digest()));
    } catch (NoSuchAlgorithmException ex) {
      return null;
    }
  }

  public static User registerUser(HttpServletResponse res, String username, String password) {
    var usersService = UsersService.getInstance();
    if (password == null || password.isBlank() || username == null || username.isBlank() ||
        !username.matches("[a-zA-Z][a-zA-Z0-9_\\-]{5,15}") ||
        !password.matches("(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9_\\-]{8,}") ||
        usersService.getUsernameTaken(username))
      return null;
    var passwordHash = getMD5Hash(password);
    if (passwordHash == null)
      return null;
    var user = usersService.saveUser(new User(username, passwordHash));
    if (user == null)
      return null;
    Web.storeCookies(res, "username", username, 14);
    Web.storeCookies(res, "password_hash", passwordHash, 14);
    return user;
  }

  public static User loginUser(HttpServletResponse res, String username, String password) {
    var usersService = UsersService.getInstance();
    if (password == null || password.isBlank() || username == null || username.isBlank())
      return null;
    var passwordHash = getMD5Hash(password);
    if (passwordHash == null)
      return null;
    var user = usersService.getUserByUsernameAndPassword(username, passwordHash);
    if (user == null)
      return null;
    Web.storeCookies(res, "username", username, 14);
    Web.storeCookies(res, "password_hash", passwordHash, 14);
    return user;
  }

  public static User loginUserWithCookies(HttpServletRequest req) {
    var username = Web.getCookie(req, "username");
    var passwordHash = Web.getCookie(req, "password_hash");
    var usersService = UsersService.getInstance();
    if (username == null || username.isBlank() || passwordHash == null || passwordHash.isBlank())
      return null;
    return usersService.getUserByUsernameAndPassword(username, passwordHash);
  }

  public static void signoutUser(HttpServletResponse res) {
    Web.storeCookies(res, "username", "", 0);
    Web.storeCookies(res, "password_hash", "", 0);
  }

  public static User updateUserPassword(HttpServletResponse res, User user, String password) {
    var usersService = UsersService.getInstance();
    if (user == null || password == null || password.isBlank() ||
        !password.matches("(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9_\\-]{8,}"))
      return null;
    var passwordHash = getMD5Hash(password);
    user.setPasswordHash(passwordHash);
    usersService.saveUser(user);
    Web.storeCookies(res, "password_hash", passwordHash, 14);
    return user;
  }

  public static User updateUsername(HttpServletResponse res, User user, String username) {
    var usersService = UsersService.getInstance();
    if (user == null || username == null || username.isBlank()
        || !username.matches("[a-zA-Z][a-zA-Z0-9_\\-]{5,15}") || usersService.getUsernameTaken(username))
      return null;
    user.setUsername(username);
    usersService.saveUser(user);
    Web.storeCookies(res, "username", username, 14);
    return user;
  }

  public static ArrayList<User> getUsers() {
    var usersService = UsersService.getInstance();
    return usersService.getAllUsers();
  }

}