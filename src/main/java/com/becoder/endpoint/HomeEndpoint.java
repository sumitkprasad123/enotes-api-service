package com.becoder.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.becoder.dto.PasswordResetRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@Tag(name = "Home", description = "All the user validation related apis")
@RequestMapping("/api/v1/home")
public interface HomeEndpoint {

	@Operation(summary = "verify user account", tags = {
			"Home" }, description = "After register user can verify its account.")
	@GetMapping("/verify")
	public ResponseEntity<?> verifyUserAccount(@RequestParam Integer uid, @RequestParam String code) throws Exception;

	@Operation(summary = "send email for password reset", tags = {
			"Home" }, description = "send email for password reset.")
	@GetMapping("/send-email-reset")
	public ResponseEntity<?> sendEmailForPasswordreset(@RequestParam String email, HttpServletRequest request)
			throws Exception;

	@Operation(summary = "verify password link", tags = {
			"Home" }, description = "User can verify its account by clicking the password link.")
	@GetMapping("/verify-pswd-link")
	public ResponseEntity<?> verifyPasswordResetLink(@RequestParam Integer uid, @RequestParam String code)
			throws Exception;

	@Operation(summary = "change password", tags = {
			"Home" }, description = "User will be able to change its password here.")
	@PostMapping("/reset-pswd")
	public ResponseEntity<?> resetPassword(@RequestBody PasswordResetRequest passwordResetRequest) throws Exception;
}
