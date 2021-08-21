package com.example.demo.controller;

import com.example.demo.dto.UserDto;
import com.example.demo.module.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "api/v1")
public class UserController {
    @Autowired
    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping(path = "register")
    public ResponseEntity<String> register(@RequestBody User user){
        try {
            String responseJson = "{\n\t" +
                    "\"token\": \"" + userService.userRegister(user) + "\",\n\t" +
                    "\"message\": \"Welcome " + user.getUsername() +"\"" +
                    "}";
            return new ResponseEntity<>(responseJson, HttpStatus.OK);
        } catch (IllegalStateException ie){
            String responseJson = "{\n\t" +
                    "\"token\": \"\",\n\t" +
                    "\"message\": \""+ ie.getMessage() + "\"\n" +
                    "}";
            return new ResponseEntity<>(responseJson, HttpStatus.OK);
        }
    }

    @PostMapping(path = "auth")
    public ResponseEntity<String> authentication(@RequestBody User user){
        try {
            String responseJson = "{\n\t" +
                    "\"token\": \"" + userService.authentication(user) + "\",\n\t" +
                    "\"message\": \"Welcome " + user.getUsername() +"\"" +
                    "}";
            return new ResponseEntity<>(responseJson, HttpStatus.OK);
        } catch (IllegalStateException ie){
            String responseJson = "{\n\t" +
                    "\"token\": \"\",\n\t" +
                    "\"message\": \""+ ie.getMessage() + "\"\n" +
                    "}";
            return new ResponseEntity<>(responseJson, HttpStatus.OK);
        }
    }

    @GetMapping(path = "{token}")
    public ResponseEntity<UserDto> authorization(@PathVariable("token") String token){
        try {
            return new ResponseEntity<>(userService.authorization(token), HttpStatus.OK);
        } catch (IllegalStateException ie){
            HttpHeaders header = new HttpHeaders();
            header.add("message", ie.getMessage());
            return new ResponseEntity<>(null, header, HttpStatus.OK);
        }
    }

    @GetMapping(path = "logout/{token}")
    public ResponseEntity<String> logout(@PathVariable("token") String token){
        try {
            return new ResponseEntity<>(this.userService.logout(token), HttpStatus.OK);
        } catch (IllegalStateException ie){
            HttpHeaders header = new HttpHeaders();
            header.add("message", ie.getMessage());
            if(ie.getMessage().equals("Unauthorized"))
                return new ResponseEntity<>(null, header, HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(null, header, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
