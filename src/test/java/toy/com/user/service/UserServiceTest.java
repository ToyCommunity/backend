package toy.com.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import toy.com.exception.CustomException;
import toy.com.exception.code.ErrorCode;
import toy.com.user.domain.User;
import toy.com.user.repository.UserRepository;

@SpringBootTest
class UserServiceTest {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@BeforeEach
	void setup() {
		userRepository.deleteAll();
	}

	@Test
	@DisplayName("회원가입 성공")
	void join_success() {

		//given
		User user = User.builder()
			.email("useremail@naver.com")
			.password("xzcjzkxcjxz1212")
			.nickname("닉네임11111")
			.build();

		//when
		userService.join(user);

		//then
		assertThat(user.getId()).isEqualTo(userService.findById(user.getId()).getId());
	}

	@Test
	@DisplayName("회원가입 실패: 중복된 이메일")
	void join_fail_wrong_input() {

		//given
		User user = User.builder()
			.email("useremail@naver.com")
			.password("xzcjzkxcjxz1212")
			.nickname("닉네임11111")
			.build();
		userService.join(user);

		User duplicatedUser = User.builder()
			.email("useremail@naver.com")
			.password("password2")
			.nickname("닉네임22222")
			.build();

		//when
		CustomException exception = assertThrows(CustomException.class, () -> userService.join(duplicatedUser));

		//then
		assertThat(exception.getErrorcode()).isEqualTo(ErrorCode.DUPLICATED_EMAIL);
	}
}