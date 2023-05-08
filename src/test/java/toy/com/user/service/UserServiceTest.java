package toy.com.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static toy.com.user.PasswordEncrypt.encrypt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import toy.com.exception.CustomException;
import toy.com.exception.code.ErrorCode;
import toy.com.user.domain.User;
import toy.com.user.dto.request.LoginRequest;
import toy.com.user.dto.response.TokenResponse;
import toy.com.user.repository.UserRepository;

@SpringBootTest
class UserServiceTest {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RedisService redisService;

	@BeforeEach
	void setup() {
		userRepository.deleteAll();
	}

	@Test
	@DisplayName("회원가입 성공")
	void join_success() {

		//given
		User user = makeUser("useremail@naver.com", encrypt("asdf1234"), "닉네임11111");

		//when
		userService.join(user);

		//then
		assertThat(user.getId()).isEqualTo(userService.findById(user.getId()).getId());
	}

	@Test
	@DisplayName("회원가입 실패: 중복된 이메일")
	void join_fail_wrong_input() {

		//given
		userService.join(makeUser("useremail@naver.com", encrypt("asdf1234"), "닉네임11111"));

		//when
		User duplicatedUser = makeUser("useremail@naver.com", "asdf1234", "닉네임11111");
		CustomException exception = assertThrows(CustomException.class, () -> userService.join(duplicatedUser));

		//then
		assertThat(exception.getErrorcode()).isEqualTo(ErrorCode.DUPLICATED_EMAIL);
	}

	@Test
	@DisplayName("로그인 성공: 토큰을 응답한다")
	void login_success() {

		//given
		User user = makeUser("useremail@naver.com", encrypt("asdf1234"), "닉네임11111");
		userService.join(user);

		//when
		LoginRequest loginRequest = LoginRequest.builder()
			.email("useremail@naver.com")
			.password("asdf1234")
			.build();
		TokenResponse loginResponse = userService.login(loginRequest.toEntity());

		//then
		assertThat(redisService.getTokenById(user.getId())).isNotNull();
		assertThat(loginResponse.accessToken()).isNotNull();
		assertThat(loginResponse.refreshToken()).isNotNull();
	}

	@Test
	@DisplayName("로그인 실패: 존재하지 않는 유저")
	void login_fail_not_found_user() {

		//given
		LoginRequest loginRequest = LoginRequest.builder()
			.email("useremail@naver.com")
			.password("asdf1234")
			.build();

		//when
		CustomException exception = assertThrows(CustomException.class,
			() -> userService.login(loginRequest.toEntity()));

		//then
		assertThat(exception.getErrorcode()).isEqualTo(ErrorCode.NOT_FOUND_USER);
	}

	@Test
	@DisplayName("로그인 실패: 잘못된 비밀번호")
	void login_fail_wrong_password() {

		//given
		userService.join(makeUser("useremail@naver.com", encrypt("asdf1234"), "닉네임11111"));

		//when
		LoginRequest loginRequest = LoginRequest.builder()
			.email("useremail@naver.com")
			.password("wrongpassword")
			.build();

		CustomException exception = assertThrows(CustomException.class,
			() -> userService.login(loginRequest.toEntity()));

		//then
		assertThat(exception.getErrorcode()).isEqualTo(ErrorCode.PASSWORD_NOT_MATCH);
	}

	@Test
	@DisplayName("로그아웃 성공: redis 토큰을 삭제한다")
	void logout_success() {

		//given
		User user = makeUser("useremail@naver.com", encrypt("asdf1234"), "닉네임11111");
		userService.join(user);

		//when
		LoginRequest loginRequest = LoginRequest.builder()
			.email("useremail@naver.com")
			.password("asdf1234")
			.build();
		userService.login(loginRequest.toEntity());

		assertThat(redisService.getTokenById(user.getId())).isNotNull();
		userService.logout(user.getId());

		//then
		assertThat(redisService.getTokenById(user.getId())).isNull();
	}

	private User makeUser(String email, String password, String nickname) {
		return User.builder()
			.email(email)
			.password(password)
			.nickname(nickname)
			.build();
	}
}