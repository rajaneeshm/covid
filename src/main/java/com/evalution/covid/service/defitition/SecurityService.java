package com.evalution.covid.service.defitition;

public interface SecurityService {
    String findLoggedInUsername();

    void autoLogin(String username, String password);
}
