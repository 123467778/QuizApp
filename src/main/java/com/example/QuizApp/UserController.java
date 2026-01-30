package com.example.QuizApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;


//    @PostMapping("/signup")
//    public ResponseEntity<String> signup(@RequestBody User user) {
//    userService.validateUser(user);
//    userRepository.save(user);
//    return ResponseEntity.ok("User registered");
//}
    @PostMapping("/signup")
    public ResponseEntity<String>signup(@RequestBody User user){
        userService.signup(user);
        return  ResponseEntity.ok("User Registered Successfully...");
    }



    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        User user =userService.login(request.getEmail(),request.getPassword());

        return ResponseEntity.ok(user);


    }

}
