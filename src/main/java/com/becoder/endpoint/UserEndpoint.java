package com.becoder.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.becoder.dto.PasswordChangeRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "User", description = "All the user releted apis")
@RequestMapping("/api/v1/user")
public interface UserEndpoint {

	@Operation(summary = "get user profile", tags = { "User" }, description = "Able to get user profile")
	@GetMapping("/profile")
	public ResponseEntity<?> getProfile();

	@Operation(summary = "change user password", tags = { "User" }, description = "login user can change own password.")
	@PostMapping("/change-password")
	public ResponseEntity<?> changePassword(@RequestBody PasswordChangeRequest passwordChangerequest);

}
