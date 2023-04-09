package toy.com.user.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import toy.com.user.dto.request.UserJoinRequest;
import toy.com.user.repository.UserRepository;
import toy.com.user.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

	private static final String ERROR = "ERROR";
	private static final String SUCCESS = "SUCCESS";

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
	void join_fail_duplicatedEmail() throws Exception {

		//given
		User user = User.builder()
			.email("useremail@naver.com")
			.password("xzcjzkxcjxz1212")
			.nickname("닉네임11111")
			.build();
		userService.join(user);

		UserJoinRequest request = UserJoinRequest.builder()
			.email("useremail@naver.com")
			.password("xzcjzkxcjxz1212")
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

}