package com.jopss.twitter.model;

import java.util.List;
import twitter4j.Status;

/**
 * Objeto backing form.
 */
public class TwitterForm {

    private String pin;
    private String urlRequestToken;
    private List<Status> statusList;
    private List<TwitterCredential> credentials;
    private String username;

    public List<Status> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<Status> statusList) {
        this.statusList = statusList;
    }
        
    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getUrlRequestToken() {
        return urlRequestToken;
    }

    public void setUrlRequestToken(String urlRequestToken) {
        this.urlRequestToken = urlRequestToken;
    }

    public List<TwitterCredential> getCredentials() {
        return credentials;
    }

    public void setCredentials(List<TwitterCredential> credentials) {
        this.credentials = credentials;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
}
