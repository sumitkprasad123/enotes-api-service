package com.becoder.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.becoder.dto.EmailRequest;
import com.becoder.dto.PasswordChangeRequest;
import com.becoder.dto.PasswordResetRequest;
import com.becoder.entity.User;
import com.becoder.exception.ResourceNotFoundException;
import com.becoder.repository.UserRepository;
import com.becoder.service.UserService;
import com.becoder.util.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private EmailService emailService;

	@Override
	public void changePassword(PasswordChangeRequest passwordRequest) {

		User logedInUser = CommonUtil.getLoggedInUser();
		if (!passwordEncoder.matches(passwordRequest.getOldPassword(), logedInUser.getPassword())) {
			throw new IllegalArgumentException("Old Password is incorrect !!");
		}
		String encodePassword = passwordEncoder.encode(passwordRequest.getNewPassword());
		logedInUser.setPassword(encodePassword);
		userRepo.save(logedInUser);

	}

	@Override
	public void sendEmailPasswordReset(String eamil, HttpServletRequest request) throws Exception {
		User user = userRepo.findByEmail(eamil);
		if (ObjectUtils.isEmpty(user)) {
			throw new ResourceNotFoundException("Invalid Email.");
		}

		// Generate unique password reset token
		String passwordResetToken = UUID.randomUUID().toString();
		user.getStatus().setPasswordResetToken(passwordResetToken);
		User updateUser = userRepo.save(user);

		String url = CommonUtil.getUrl(request);
		sendEmailRequest(updateUser, url);

	}

	private void sendEmailRequest(User user, String url) throws Exception {

		String message = "Hi,<b>[[username]]</b>" + "<br><p> You have requested to reset your password.<p/>"
				+ "<p> Click the link below to change the password:</p>"
				+ "<p><a href='[[url]]'>Change my password </a></p>"
				+ "<p>Ignore this eamil if you do remember uyour password."
				+ "or you have not made the request</p><br><br>" + "Thanks, <br>Enotes.com";

		message = message.replace("[[username]]", user.getFirstName());

		message = message.replace("[[url]]", url + "/api/v1/home/verify-pswd-link?uid=" + user.getId() + "&&code="
				+ user.getStatus().getPasswordResetToken());

		EmailRequest emailRequest = EmailRequest.builder().to(user.getEmail()).title("Password Reset")
				.subject("Password Reset link").message(message).build();

		// send password reset email to user
		emailService.sendEmail(emailRequest);

	}

	@Override
	public void verifyPasswordResetLink(Integer uid, String code) throws Exception {
		User user = userRepo.findById(uid).orElseThrow(() -> new ResourceNotFoundException("Invalid user."));
		verifyPasswordResetToken(user.getStatus().getPasswordResetToken(), code);

	}

	private void verifyPasswordResetToken(String existToken, String reqToken) {
		// request token not null
		if (StringUtils.hasText(reqToken)) {

			// password already reset
			if (!StringUtils.hasText(existToken)) {
				throw new IllegalArgumentException("Already Password reset");
			}

			// user req token changes
			if (!existToken.equals(reqToken)) {
				throw new IllegalArgumentException("Invalid url");
			}
		} else {
			throw new IllegalArgumentException("Invalid token");
		}

	}

	@Override
	public void resetPassword(PasswordResetRequest passwordRestRequest) throws Exception {
		User user = userRepo.findById(passwordRestRequest.getUid())
				.orElseThrow(() -> new ResourceNotFoundException("Invalid user"));
		String encodePassword = passwordEncoder.encode(passwordRestRequest.getNewPassword());
		user.setPassword(encodePassword);
		userRepo.save(user);

	}

}
