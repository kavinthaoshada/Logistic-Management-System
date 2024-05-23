package com.ozzz.ejb.remote;

import jakarta.ejb.Remote;

@Remote
public interface LoginService {
    public String login(String email, String password);
}
