package com.becoder.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.becoder.dto.LoginRequest;
import com.becoder.dto.UserRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@Tag(name = "Authentication", description = "All the users Authentication Apis")
@RequestMapping("/api/v1/auth")
public interface AuthEndpoint {

	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Register Success"),
			@ApiResponse(responseCode = "500", description = "Internal server error"),
			@ApiResponse(responseCode = "400", description = "Bad Request") })
	@Operation(summary = "User Register", tags = { "Authentication" }, description = "User register api")
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody UserRequest userDto, HttpServletRequest request) throws Exception;

	@Operation(summary = "User login", tags = { "Authentication" }, description = "User login api")
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest);

}
