package toy.com.user.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import toy.com.common.ResponseFormat;
import toy.com.user.dto.request.UserJoinReq;
import toy.com.user.dto.response.JwtResponse;
import toy.com.user.service.AuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "auth-controller", description = "인증 관련 API")
public class AuthController {

	private final AuthService authService;

	/**
	 * 유저 회원가입 완료 후 토큰을 발급한다
	 *
	 * @param userJoinReq
	 * @return jwt;
	 */
	@Operation(summary = "회원 가입", description = "회원 가입을 요청한다")
	@PostMapping(value = "/join", produces = "application/json")
	public ResponseFormat<JwtResponse> userJoin(@Valid @RequestBody UserJoinReq userJoinReq) {
		return ResponseFormat.normal(authService.createJoinUserInfo(userJoinReq));
	}
}
