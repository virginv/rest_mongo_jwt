package com.base.mongo.controllers;

import java.util.Optional;

import javax.validation.Valid;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.mongo.config.exception.BusinessServiceException;
import com.base.mongo.config.security.JWTTokenProvider;
import com.base.mongo.models.User;
import com.base.mongo.models.request.LoginRequest;
import com.base.mongo.models.request.SignUpRequest;
import com.base.mongo.models.response.JWTAuthenticationResponse;
import com.base.mongo.services.IUserService;
import com.base.mongo.util.ErrorCode;
import com.base.mongo.util.ResponseUtil;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JWTTokenProvider tokenProvider;

	@Autowired
	IUserService userService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		LOGGER.info("authenticateUser");
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmail(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = tokenProvider.generateToken(authentication);
		return ResponseEntity.ok(new JWTAuthenticationResponse(jwt));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest)
			throws BusinessServiceException {
		LOGGER.info("registerUser");
		if (userService.findByUsername(signUpRequest.getUsername()) != null) {
			throw new BusinessServiceException(ErrorCode.JWT_INVALID_USER);
		}
		
		// Creating user's account
		User result = userService.save(new User(new ObjectId(),signUpRequest.getName(), 
				signUpRequest.getUsername(), signUpRequest.getEmail(), 
				passwordEncoder.encode(signUpRequest.getPassword())));
		
		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
	}

}
