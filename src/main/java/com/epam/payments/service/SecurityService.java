package com.epam.payments.service;

public interface SecurityService {
    String findLoggedInUsername();

    void autoLogin(String email, String password);
}
