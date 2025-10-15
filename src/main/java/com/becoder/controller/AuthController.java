package com.becoder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.becoder.dto.LoginRequest;
import com.becoder.dto.LoginResponse;
import com.becoder.dto.UserRequest;
import com.becoder.service.AuthService;
import com.becoder.util.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	@PostMapping("/save")
	public ResponseEntity<?> register(@RequestBody UserRequest userDto, HttpServletRequest request) throws Exception {
		String url = CommonUtil.getUrl(request);
		Boolean register = authService.register(userDto, url);

		if (register) {
			return CommonUtil.createBuildResponse("Registered Success", HttpStatus.CREATED);
		}
		return CommonUtil.createErrorResponseMessage("Register failed", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
		LoginResponse loginResponse = authService.login(loginRequest);
		if (ObjectUtils.isEmpty(loginResponse)) {
			return CommonUtil.createErrorResponseMessage("Invalid credential", HttpStatus.BAD_GATEWAY);
		}
		return CommonUtil.createBuildResponse(loginResponse, HttpStatus.OK);
	}

}
