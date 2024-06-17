package com.example.lw2.controllers;

import com.example.lw2.util.Db;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class Home {

  // Website content

  @GetMapping({"", "/", "/index"})
  public String index(Model model, HttpServletRequest req) {
    var user = Db.loginUserWithCookies(req);
    if (user == null)
      return "redirect:/signin";
    model.addAttribute("current_username", user.getUsername());
    model.addAttribute("all_users", Db.getUsers());
    return "index";
  }

  @PostMapping("/username-change")
  public String editLogin(@RequestParam("username") String username, HttpServletRequest req, HttpServletResponse res) {
    var user = Db.loginUserWithCookies(req);
    if (user == null)
      return "redirect:/signin";
    if (Db.updateUsername(res, user, username) == null)
      return "username-change-failed";
    return "redirect:/index";
  }

  @PostMapping("/password-change")
  public String editPassword(@RequestParam("password1") String password1,
                             @RequestParam("password2") String password2,
                             HttpServletRequest req, HttpServletResponse res) {
    var user = Db.loginUserWithCookies(req);
    if (user == null)
      return "redirect:/signin";
    if (password1.isBlank() || password2.isBlank() || !password1.equals(password2) ||
        Db.updateUserPassword(res, user, password1) == null)
      return "password-change-failed";
    return "redirect:/index";
  }

  // Login

  @GetMapping("/signin")
  public String signinForm(HttpServletRequest req) {
    if (Db.loginUserWithCookies(req) != null)
      return "redirect:/index";
    return "signin";
  }

  @PostMapping("/signin")
  public String login(@RequestParam("username") String username,
                      @RequestParam("password") String password, HttpServletResponse res) {
    if (Db.loginUser(res, username, password) == null)
      return "signin-failed";
    return "redirect:/index";
  }

  // Registration

  @GetMapping("/signup")
  public String signupForm(HttpServletRequest req) {
    if (Db.loginUserWithCookies(req) != null)
      return "redirect:/index";
    return "signup";
  }

  @PostMapping("/signup")
  public String register(@RequestParam("username") String username,
                         @RequestParam("password1") String password1,
                         @RequestParam("password2") String password2, HttpServletResponse res) {
    if (username.isBlank() || password1.isBlank() || password2.isBlank() ||
        !password1.equals(password2) || Db.registerUser(res, username, password1) == null)
      return "signup-failed";
    return "redirect:/index";
  }

  // Sign out

  @RequestMapping("/signout")
  public String signout(HttpServletResponse res) {
    Db.signoutUser(res);
    return "redirect:/signin";
  }

}