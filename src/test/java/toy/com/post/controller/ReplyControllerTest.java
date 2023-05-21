package toy.com.post.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import toy.com.post.domain.Post;
import toy.com.post.domain.Reply;
import toy.com.post.dto.request.PostCreateRequest;
import toy.com.post.dto.request.ReplyCreateRequest;
import toy.com.post.dto.request.ReplyModifyRequest;
import toy.com.post.repository.PostRepository;
import toy.com.post.repository.ReplyRepository;
import toy.com.post.service.PostCommandService;
import toy.com.post.service.ReplyCommandService;

@SpringBootTest
@AutoConfigureMockMvc
class ReplyControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ReplyCommandService replyCommandService;

	@Autowired
	private PostCommandService postCommandService;

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private ReplyRepository replyRepository;

	@BeforeEach
	void setup() {
		postRepository.deleteAll();
		replyRepository.deleteAll();
	}

	@Test
	@DisplayName("댓글 작성 테스트")
	void createReply() throws Exception {

		PostCreateRequest request = PostCreateRequest.builder()
			.title("테스트")
			.content("테스트")
			.build();

		postCommandService.createPost(request);

		Post post = postRepository.findAll().get(0);

		ReplyCreateRequest replyCreateRequest = ReplyCreateRequest.builder()
			.postId(post.getId())
			.content("테스트")
			.build();

		mockMvc.perform(post("/api/reply")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(replyCreateRequest)))
			.andExpect(status().isCreated())
			.andDo(print());
	}

	@Test
	@DisplayName("댓글 수정 테스트")
	void modifyReply() throws Exception {

		PostCreateRequest request = PostCreateRequest.builder()
			.title("테스트")
			.content("테스트")
			.build();

		postCommandService.createPost(request);

		Post post = postRepository.findAll().get(0);

		ReplyCreateRequest replyCreateRequest = ReplyCreateRequest.builder()
			.postId(post.getId())
			.content("테스트")
			.build();

		replyCommandService.createReply(replyCreateRequest);

		Reply reply = replyRepository.findAll().get(0);

		ReplyModifyRequest modifyRequest = ReplyModifyRequest.builder()
			.replyId(reply.getId())
			.content("수정테스트")
			.build();

		mockMvc.perform(patch("/api/reply")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(modifyRequest)))
			.andExpect(status().isOk())
			.andDo(print());
	}

	@Test
	@DisplayName("댓글 삭제 테스트")
	void deleteReply() throws Exception {

		PostCreateRequest request = PostCreateRequest.builder()
			.title("테스트")
			.content("테스트")
			.build();

		postCommandService.createPost(request);

		Post post = postRepository.findAll().get(0);

		ReplyCreateRequest replyCreateRequest = ReplyCreateRequest.builder()
			.postId(post.getId())
			.content("테스트")
			.build();

		replyCommandService.createReply(replyCreateRequest);

		Reply reply = replyRepository.findAll().get(0);

		mockMvc.perform(delete("/api/reply/{replyId}", reply.getId()))
			.andExpect(status().isOk())
			.andDo(print());
	}

	@Test
	@DisplayName("댓글 좋아요 테스트")
	void likeReply() throws Exception {

		PostCreateRequest request = PostCreateRequest.builder()
			.title("테스트")
			.content("테스트")
			.build();

		postCommandService.createPost(request);

		Post post = postRepository.findAll().get(0);

		ReplyCreateRequest replyCreateRequest = ReplyCreateRequest.builder()
			.postId(post.getId())
			.content("테스트")
			.build();

		replyCommandService.createReply(replyCreateRequest);

		Reply reply = replyRepository.findAll().get(0);

		mockMvc.perform(post("/api/reply/like/{replyId}", reply.getId()))
			.andExpect(status().isOk())
			.andDo(print());
	}

	@Test
	@DisplayName("댓글 좋아요 취소 테스트")
	void disLikeReply() throws Exception {

		PostCreateRequest request = PostCreateRequest.builder()
			.title("테스트")
			.content("테스트")
			.build();

		postCommandService.createPost(request);

		Post post = postRepository.findAll().get(0);

		ReplyCreateRequest replyCreateRequest = ReplyCreateRequest.builder()
			.postId(post.getId())
			.content("테스트")
			.build();

		replyCommandService.createReply(replyCreateRequest);

		Reply reply = replyRepository.findAll().get(0);
		System.out.println(reply.getId());

		mockMvc.perform(delete("/api/reply/like/{replyId}", reply.getId()))
			.andExpect(status().isOk())
			.andDo(print());
	}

}