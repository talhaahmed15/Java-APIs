package com.petproject.demo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "api/v1/user")
public class UserController {


    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    List<User> getUsers(){
    return userService.getUsers();
    }

    @PostMapping("/signup")
    ResponseEntity<Map<String, Object>> registerUser(@RequestBody User user){
        final ResponseEntity<Map<String, Object>>  response = userService.signup(user);

        System.out.println(response);
        return response;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");

        System.out.println(email + password);

        return userService.login(email, password);
    }
}
