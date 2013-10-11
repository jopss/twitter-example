package com.jopss.twitter.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe "persistente" que mantem os dados de credenciais de usuarios jah acessados.
 * Dessa forma nao eh necessario pedir permissao ao twitter destes usuarios novamente.
 */
public class TwitterCredential {
    
    private Long id;
    private String name;
    private String token;
    private String secret;
            
    private static List<TwitterCredential> base = new ArrayList<TwitterCredential>();
    
    public static void addCredencial(Long id, String name, String token, String secret){
        TwitterCredential cred = new TwitterCredential();
        cred.setId(id);
        cred.setName(name);
        cred.setSecret(secret);
        cred.setToken(token);
        
        addCredencial(cred);
    }
    
    public static void addCredencial(TwitterCredential cred){
        base.add(cred);
    }
    
    public static TwitterCredential findUserCredential(Long userId){
        for(TwitterCredential cred : base){
            if(userId.longValue() == cred.getId().longValue()){
                return cred;
            }
        }
        return null;
    }
    
    public static List<TwitterCredential> findAll(){
        return base;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
