package toy.com.post.controller;

import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import toy.com.post.domain.Post;
import toy.com.post.domain.PostCategory;
import toy.com.post.domain.PostStatus;
import toy.com.post.dto.request.PostCreateRequest;
import toy.com.post.dto.request.PostModifyRequest;
import toy.com.post.dto.response.PostDetailInfoResponse;
import toy.com.post.repository.PostRepository;
import toy.com.post.service.PostCommandService;
import toy.com.post.service.PostQueryService;

@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private PostQueryService postQueryService;

	@Autowired
	private PostCommandService postCommandService;

	@Autowired
	private PostRepository postRepository;

	@BeforeEach
	void setup() {

		postRepository.deleteAll();

		this.mockMvc = MockMvcBuilders.standaloneSetup(new PostController(postQueryService, postCommandService))
			.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
			.build();
	}

	@Test
	@DisplayName("게시글 작성 테스트")
	void createPost() throws Exception {

		PostCreateRequest request = PostCreateRequest.builder()
			.title("테스트")
			.content("테스트")
			.build();

		mockMvc.perform(post("/api/post/write")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isCreated())
			.andDo(print());
	}

	@Test
	@DisplayName("게시글 수정 테스트")
	void modifyPost() throws Exception {

		Post createPost = Post.builder()
			.postTitle("테스트111")
			.postContent("테스트")
			.postCategory(PostCategory.DEFAULT)
			.postStatus(PostStatus.NORMAL)
			.build();

		postRepository.save(createPost);

		PostModifyRequest request = PostModifyRequest.builder()
			.postId(createPost.getId())
			.title("수정테스트")
			.content("테스트")
			.category(PostCategory.DEFAULT)
			.build();

		mockMvc.perform(patch("/api/post/modify")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isOk())
			.andDo(print());
	}

	@Test
	@DisplayName("게시글 삭제 테스트")
	void deletePost() throws Exception {

		Post createPost = Post.builder()
			.postTitle("테스트111")
			.postContent("테스트")
			.postCategory(PostCategory.DEFAULT)
			.postStatus(PostStatus.NORMAL)
			.build();

		postRepository.save(createPost);

		mockMvc.perform(delete("/api/post/delete/{postId}", createPost.getId()))
			.andExpect(status().isOk())
			.andDo(print());
	}

	@Test
	@DisplayName("게시글 리스트 조회 테스트")
	void getPostList() throws Exception {

		int page = 0;

		PostCreateRequest request = PostCreateRequest.builder()
			.title("테스트")
			.content("테스트")
			.build();

		postCommandService.createPost(request);

		mockMvc.perform(get("/api/post/list")
				.contentType("application/x-www-form-urlencoded")
				.param("page", String.valueOf(page)))
			.andExpect(status().isOk())
			.andDo(print());
	}

	@Test
	@DisplayName("게시글 상세 조회 테스트")
	void getPostDetail() throws Exception {

		PostCreateRequest request = PostCreateRequest.builder()
			.title("테스트")
			.content("테스트")
			.build();

		postCommandService.createPost(request);

		Post post = postRepository.findAll().get(0);

		PostDetailInfoResponse res = postQueryService.getPostDetail(post.getId());

		mockMvc.perform(get("/api/post/detail/{postId}", post.getId())
				.contentType(APPLICATION_JSON))
			.andExpect(jsonPath("postId").value(res.postId()))
			.andExpect(jsonPath("title").value(res.title()))
			.andExpect(jsonPath("userId").value(res.userId()))
			.andExpect(jsonPath("userName").value(res.nickname()))
			.andExpect(jsonPath("category").value(res.category()))
			.andExpect(jsonPath("likeCounts").value(res.likeCounts()))
			.andExpect(jsonPath("viewCounts").value(res.viewCounts() + 1))
			.andExpect(jsonPath("postMyReaction").value(res.postMyReaction()))
			.andExpect(status().isOk())
			.andDo(print());
	}

	@Test
	@DisplayName("게시글 좋아요 테스트")
	void postLike() throws Exception {

		PostCreateRequest request = PostCreateRequest.builder()
			.title("테스트")
			.content("테스트")
			.build();

		postCommandService.createPost(request);

		Post post = postRepository.findAll().get(0);

		mockMvc.perform(put("/api/post/like/{postId}", post.getId()))
			.andExpect(status().isOk())
			.andDo(print());
	}
}