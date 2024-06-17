package com.example.lw2;

import com.example.lw2.repos.UsersRepository;
import com.example.lw2.services.UsersService;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.beans.factory.annotation.Autowired;


@SpringBootApplication
public class Lw2Application implements CommandLineRunner {

  @Autowired
  private UsersRepository usersRepo;

  public static void main(String[] args) {
    SpringApplication.run(Lw2Application.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    UsersService.setRepository(usersRepo);
  }

}
