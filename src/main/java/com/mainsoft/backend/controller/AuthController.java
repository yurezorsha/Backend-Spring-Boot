package com.mainsoft.backend.controller;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mainsoft.backend.entity.Login;
import com.mainsoft.backend.entity.Role;
import com.mainsoft.backend.entity.RoleName;
import com.mainsoft.backend.entity.User;
import com.mainsoft.backend.repository.RoleRepository;
import com.mainsoft.backend.repository.UserRepository;
import com.mainsoft.backend.security.jwt.JwtProvider;
import com.mainsoft.backend.security.jwt.JwtResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@Api("Api for registration and login")
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtProvider jwtProvider;

	@PostMapping("/login")
	@ApiOperation("Returns token if user exist")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody Login login) {

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = jwtProvider.generateJwtToken(authentication);
		return ResponseEntity.ok(new JwtResponse(jwt));
	}

	@PostMapping("/register")
	@ApiOperation("User registration if user not exist")
	public ResponseEntity<String> registerUser(@Valid @RequestBody Login login) {
		if (userRepository.existsByUsername(login.getUsername())) {
			return new ResponseEntity<String>("Fail -> Username is already taken!", HttpStatus.BAD_REQUEST);
		}

		// Creating user's account
		User user = new User(login.getUsername(), encoder.encode(login.getPassword()));

		Set<Role> roles = new HashSet<Role>();

		roles.add(roleRepository.findByName(RoleName.ROLE_USER));

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok().body("User registered successfully!");
	}
}
