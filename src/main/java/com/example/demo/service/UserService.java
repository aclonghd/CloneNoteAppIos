package com.example.demo.service;

import com.example.demo.dto.UserDto;
import com.example.demo.mapper.UserMapper;
import com.example.demo.module.User;
import com.example.demo.respository.UserRepository;
import com.example.demo.security.TokenStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public String authentication(User userLogin){
        boolean userExists = userRepository.findByUsername(userLogin.getUsername()).isPresent();
        if(userExists) {
            String password = userLogin.getPassword();
            boolean checkPassword = (userRepository.findByUsername(userLogin.getUsername()).get().getPassword().equals(password));
            if (checkPassword) {
                return getToken(userLogin.getUsername());
            }
        }
        throw new IllegalStateException("Username or password is wrong");
    }


    public String userRegister(User user){
        boolean userExists = userRepository.findByUsername(user.getUsername()).isPresent();
        if(userExists){
            throw new IllegalStateException("Username already taken");
        }
        userRepository.save(user);
        return this.getToken(user.getUsername());
    }

    public String getToken(String username){
        return TokenStore.getInstance().putToken(username);
    }

    public UserDto authorization(String token) {
        String username = TokenStore.getInstance().getUsername(token);
        if(username == null || !this.userRepository.findByUsername(username).isPresent()){
            throw new IllegalStateException("Unauthorized");
        }

        return this.userMapper.entityToDto(this.userRepository.findByUsername(username).get());
    }

    public String logout(String token){
        String username = TokenStore.getInstance().getUsername(token);
        if(username == null || !this.userRepository.findByUsername(username).isPresent()){
            throw new IllegalStateException("Unauthorized");
        }
        if(TokenStore.getInstance().removeToken(token)) return "Successful";
        throw new IllegalStateException("Server error exception");
    }
}
