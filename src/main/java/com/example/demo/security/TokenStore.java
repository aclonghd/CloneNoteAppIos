package com.example.demo.security;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TokenStore{
    private final Map<String, TokenData> tokenMap = new HashMap<>();
    private final static TokenStore instance = new TokenStore();
    public static TokenStore getInstance(){
        return instance;
    }
    private TokenStore(){}
    public String putToken(String username){
        String token = UUID.randomUUID().toString();
        tokenMap.put(token, new TokenData(username));
        return token;
    }
    public String getUsername(String token){
        if(tokenMap.containsKey(token)){
            if(tokenMap.get(token).expirationTime > System.currentTimeMillis()){
                tokenMap.get(token).expirationTime = System.currentTimeMillis() + 15 * 60 * 1000;
                return tokenMap.get(token).username;
            }
            else{
                tokenMap.remove(token);
            }
        }
        return null;
    }
    public boolean removeToken(String token){
        if(tokenMap.containsKey(token)){
            tokenMap.remove(token);
            return true;
        }
        return false;
    }
    private static class TokenData{
        String username;
        long expirationTime;

        private TokenData(String username){
            this.username = username;
            //15 minutes from now
            expirationTime = System.currentTimeMillis() + 15 * 60 * 1000;
        }
    }
}
