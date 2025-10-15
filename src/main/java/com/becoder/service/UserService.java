package com.becoder.service;

import com.becoder.dto.PasswordChangeRequest;
import com.becoder.dto.PasswordResetRequest;

import jakarta.servlet.http.HttpServletRequest;

public interface UserService {

	public void changePassword(PasswordChangeRequest passwordRequest);

	public void sendEmailPasswordReset(String eamil, HttpServletRequest request) throws Exception;

	public void verifyPasswordResetLink(Integer uid, String code) throws Exception;

	public void resetPassword(PasswordResetRequest passwordResetRequest) throws Exception;

}
