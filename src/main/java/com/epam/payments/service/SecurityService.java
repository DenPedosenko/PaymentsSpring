package com.epam.payments.service;

import javax.servlet.http.HttpServletRequest;

public interface SecurityService {
	String findLoggedInUsername();

	void autoLogin(HttpServletRequest request, String email, String password);
}
