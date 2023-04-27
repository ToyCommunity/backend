package toy.com.user.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import toy.com.user.dto.request.LoginRequest;
import toy.com.user.dto.request.UserJoinRequest;
import toy.com.user.dto.response.TokenResponse;
import toy.com.user.dto.response.UserJoinResponse;

@Tag(name = "user-controller", description = "유저 API")
public interface UserControllerSwagger {
	@Operation(summary = "회원 가입", description = "회원 가입을 요청한다")
	UserJoinResponse join(@Valid @RequestBody UserJoinRequest userJoinRequest);

	@Operation(summary = "로그인", description = "로그인 요청")
	TokenResponse login(@Valid @RequestBody LoginRequest loginRequest);
}
