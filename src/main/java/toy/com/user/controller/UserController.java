package toy.com.user.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import toy.com.config.auth.UserAuthentication;
import toy.com.user.dto.request.LoginRequest;
import toy.com.user.dto.request.UserJoinRequest;
import toy.com.user.dto.response.TokenResponse;
import toy.com.user.dto.response.UserJoinResponse;
import toy.com.user.service.UserService;

@RequiredArgsConstructor
@RestController
public class UserController implements UserControllerSwagger {

	private final UserService userService;

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/api/users")
	public UserJoinResponse join(@Valid @RequestBody UserJoinRequest userJoinRequest) {
		userService.join(userJoinRequest.toEntity());
		return new UserJoinResponse(userJoinRequest.email(), userJoinRequest.nickname());
	}

	@PostMapping("/api/login")
	public TokenResponse login(@Valid @RequestBody LoginRequest loginRequest) {
		return userService.login(loginRequest.toEntity());
	}

	@PostMapping("/api/logout")
	public void logout(UserAuthentication userAuthentication) {
		userService.logout(userAuthentication.id());
	}
}
