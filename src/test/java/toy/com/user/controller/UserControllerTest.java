package toy.com.user.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static toy.com.user.PasswordEncrypt.encrypt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import toy.com.exception.code.ErrorCode;
import toy.com.user.domain.User;
import toy.com.user.dto.request.LoginRequest;
import toy.com.user.dto.request.UserJoinRequest;
import toy.com.user.repository.UserRepository;
import toy.com.user.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

	private static final String ERROR = "ERROR";

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

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
	void join_success() throws Exception {

		//given
		UserJoinRequest request = UserJoinRequest.builder()
			.email("useremail@naver.com")
			.password("asdf1234")
			.nickname("닉네임11111")
			.build();

		mockMvc.perform(post("/api/users")
				.contentType(APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("email").value(request.email()))
			.andExpect(jsonPath("nickname").value(request.nickname()))
			.andDo(print());
	}

	@Test
	@DisplayName("회원가입 실패 : 잘못된 필드값 존재시 ")
	void join_fail_wrong_input() throws Exception {

		//given
		UserJoinRequest request = UserJoinRequest.builder()
			.email("useremail@naver.com")
			.password("")
			.nickname("닉네임11111")
			.build();

		mockMvc.perform(post("/api/users")
				.contentType(APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("result").value(ERROR))
			.andExpect(jsonPath("detail.code").value(ErrorCode.WRONG_FORM_INPUT.getCode()))
			.andExpect(jsonPath("detail.message").value(ErrorCode.WRONG_FORM_INPUT.getMessage()))
			.andDo(print());
	}

	@Test
	@DisplayName("회원가입 실패 : 중복 이메일")
	void join_fail_duplicated_email() throws Exception {

		//given
		userService.join(makeUser("useremail@naver.com", encrypt("asdf1234"), "닉네임11111"));

		//when
		UserJoinRequest request = UserJoinRequest.builder()
			.email("useremail@naver.com")
			.password("asdf1234")
			.nickname("닉네임11111")
			.build();

		mockMvc.perform(post("/api/users")
				.contentType(APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isConflict())
			.andExpect(jsonPath("result").value(ERROR))
			.andExpect(jsonPath("detail.code").value(ErrorCode.DUPLICATED_EMAIL.getCode()))
			.andExpect(jsonPath("detail.message").value(ErrorCode.DUPLICATED_EMAIL.getMessage()))
			.andDo(print());
	}

	@Test
	@DisplayName("로그인 성공 : 토큰을 발급한다")
	void login_success() throws Exception {

		//given
		userService.join(makeUser("useremail@naver.com", encrypt("asdf1234"), "닉네임11111"));

		//when
		LoginRequest loginRequest = LoginRequest.builder()
			.email("useremail@naver.com")
			.password("asdf1234")
			.build();

		mockMvc.perform(post("/api/login")
				.contentType(APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(loginRequest)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.accessToken").isNotEmpty())
			.andExpect(jsonPath("$.refreshToken").isNotEmpty())
			.andDo(print());
	}

	@Test
	@DisplayName("로그인 실패 : 잘못된 폼 입력")
	void login_fail_wrong_input() throws Exception {

		//given
		LoginRequest loginRequest = LoginRequest.builder()
			.email("useremail@naver.com")
			.password("")
			.build();

		mockMvc.perform(post("/api/login")
				.contentType(APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(loginRequest)))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("result").value(ERROR))
			.andExpect(jsonPath("detail.code").value(ErrorCode.WRONG_FORM_INPUT.getCode()))
			.andExpect(jsonPath("detail.message").value(ErrorCode.WRONG_FORM_INPUT.getMessage()))
			.andDo(print());
	}

	@Test
	@DisplayName("로그인 실패 : 존재하지 않는 유저")
	void login_fail_duplicated_Email() throws Exception {

		//given
		LoginRequest loginRequest = LoginRequest.builder()
			.email("useremail@naver.com")
			.password("asdf1234")
			.build();

		mockMvc.perform(post("/api/login")
				.contentType(APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(loginRequest)))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("result").value(ERROR))
			.andExpect(jsonPath("detail.code").value(ErrorCode.NOT_FOUND_USER.getCode()))
			.andExpect(jsonPath("detail.message").value(ErrorCode.NOT_FOUND_USER.getMessage()))
			.andDo(print());
	}

	@Test
	@DisplayName("로그인 실패 : 패스워드가 틀린경우")
	void login_fail_wrong_password() throws Exception {

		//given
		userService.join(makeUser("useremail@naver.com", encrypt("asdf1234"), "닉네임11111"));

		//when
		LoginRequest loginRequest = LoginRequest.builder()
			.email("useremail@naver.com")
			.password("wrongpassword")
			.build();

		mockMvc.perform(post("/api/login")
				.contentType(APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(loginRequest)))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("result").value(ERROR))
			.andExpect(jsonPath("detail.code").value(ErrorCode.PASSWORD_NOT_MATCH.getCode()))
			.andExpect(jsonPath("detail.message").value(ErrorCode.PASSWORD_NOT_MATCH.getMessage()))
			.andDo(print());
	}

	private User makeUser(String email, String password, String nickname) {
		return User.builder()
			.email(email)
			.password(password)
			.nickname(nickname)
			.build();
	}
}