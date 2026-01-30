package com.example.QuizApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public  void signup (User user){
        if(user.getUsername()==null || user.getUsername().isBlank()){
            throw new IllegalArgumentException("Name  is required");

        }
        if(user.getEmail()==null || user.getEmail().isBlank()){
            throw new IllegalArgumentException("Email is required");

        }
        if(!user.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")){
            throw  new IllegalArgumentException("Invalid email Format");
        }

        if(userRepository.existsByEmail(user.getEmail())){
            throw new IllegalArgumentException("Email Already exists");
        }
        if(user.getPassword()==null ||user.getPassword().length()<6){
            throw new IllegalArgumentException("Password must contain at least 6 character");
        }

        userRepository.save(user);

    }

    public User login(String email, String password) {

        User user =userRepository.findByEmail(email).orElseThrow(()->new RuntimeException("User  Not Found"));

        if(!user.getPassword().equals(password)){
            throw new RuntimeException("Invalid Password");
        }

        return user;

    }







}
