package com.example.lw2.util;

import java.util.Arrays;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;


public class Web {

  public static void storeCookies(HttpServletResponse res, String key, String value, int daysTillExpiry) {
    var cookie = new Cookie(key, value);
    cookie.setMaxAge(60 * 60 * 24 * daysTillExpiry);
    res.addCookie(cookie);
  }

  public static String getCookie(HttpServletRequest req, String key) {
    Cookie[] cookies = req.getCookies();
    if (cookies == null)
      return null;
    var cookie = Arrays.stream(cookies).filter(c -> c.getName().equals(key)).findFirst();
    if (cookie.isEmpty() || cookie.get().getValue().isBlank())
      return null;
    return cookie.get().getValue();
  }

}