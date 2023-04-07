package toy.com.user.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import toy.com.user.dto.request.UserJoinReq;
import toy.com.user.dto.response.JwtResponse;

@SpringBootTest
@ActiveProfiles("local")
class AuthServiceTest {

	@Autowired
	private AuthService authService;

	private UserJoinReq userJoinReq;

	@BeforeEach
	void createTestUser() {

		userJoinReq = UserJoinReq.builder()
			.id("testId")
			.nickName("테스트닉네임")
			.pass("testPass")
			.build();
	}

	@Test
	@DisplayName("유저 가입시 토큰을 반환하는지 확인하는 테스트 ")
	void createJoinUserInfo() {

		// when
		JwtResponse jwt = authService.createJoinUserInfo(userJoinReq);

		// then
		assertThat(jwt.jwt()).isInstanceOf(String.class);
	}
}