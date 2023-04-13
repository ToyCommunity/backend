package toy.com.user.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import toy.com.user.dto.request.LoginRequest;
import toy.com.user.dto.request.UserJoinRequest;
import toy.com.user.dto.response.TokenResponse;
import toy.com.user.dto.response.UserJoinResponse;
import toy.com.user.service.UserService;

@Tag(name = "user-controller", description = "유저 관련 API")
@RequiredArgsConstructor
@RestController
public class UserController {

	private final UserService userService;

	@Operation(summary = "회원 가입", description = "회원 가입을 요청한다")
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/api/users", produces = "application/json")
	public UserJoinResponse join(@Valid @RequestBody UserJoinRequest userJoinRequest) {
		userService.join(userJoinRequest.toEntity());
		return new UserJoinResponse(userJoinRequest.email(), userJoinRequest.nickname());
	}

	@Operation(summary = "로그인", description = "로그인을 요청한다")
	@PostMapping(value = "/api/login", produces = "application/json")
	public TokenResponse login(@Valid @RequestBody LoginRequest loginRequest) {
		return userService.login(loginRequest.toEntity());
	}
}
