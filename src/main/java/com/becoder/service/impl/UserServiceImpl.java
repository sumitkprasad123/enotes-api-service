package com.becoder.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.becoder.dto.PasswordChangeRequest;
import com.becoder.entity.User;
import com.becoder.repository.UserRepository;
import com.becoder.service.UserService;
import com.becoder.util.CommonUtil;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

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

}
