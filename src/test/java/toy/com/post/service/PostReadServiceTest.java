package toy.com.post.service;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import toy.com.post.domain.Post;
import toy.com.post.domain.PostCategory;
import toy.com.post.domain.PostStatus;
import toy.com.post.domain.Reply;
import toy.com.post.dto.response.PostDetailInfoResponse;
import toy.com.post.dto.response.PostListsResponse;
import toy.com.post.repository.PostRepository;
import toy.com.user.domain.User;
import toy.com.user.repository.UserRepository;

@SpringBootTest
@Transactional
class PostReadServiceTest {

	private static final int PAGE_SIZE = 20;

	@Autowired
	private PostReadService postReadService;

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private UserRepository userRepository;

	@BeforeEach
	void setUp() {

		List<Post> bulkInsert = new ArrayList<>();

		User samplePostWriterUser = User.builder()
			.email("useremail1@naver.com")
			.password("xzcjzkxcjxz1212")
			.nickname("닉네임11111")
			.build();

		User sampleReplyWriterUser = User.builder()
			.email("useremail2@naver.com")
			.password("xzcjzkxcjxz1212")
			.nickname("닉네임22222")
			.build();

		for (int i = 0; i < 100; i++) {
			Reply sampleReply = Reply.builder()
				.content("샘플 답글")
				.replyLikes(1)
				.replyWriter(sampleReplyWriterUser)
				.build();

			Post samplePost = Post.builder()
				.postTitle("샘플 포스트")
				.postContent("샘플내용")
				.postStatus(PostStatus.NORMAL)
				.postCategory(PostCategory.DEFAULT)
				.postWriter(samplePostWriterUser)
				.build();

			sampleReply.applyPost(samplePost);

			bulkInsert.add(samplePost);
		}

		userRepository.save(samplePostWriterUser);
		userRepository.save(sampleReplyWriterUser);

		postRepository.saveAll(bulkInsert);
	}

	@ParameterizedTest
	@ValueSource(ints = {1, 2, 3, 4})
	@DisplayName("게시글이 페이지 네이션 개수만큼 갖고와지는지 확인")
	void getPostList(int page) {

		Pageable pageable = PageRequest.of(page, PAGE_SIZE);

		PostListsResponse response = postReadService.getPostListPerPage(pageable);

		assertThat(response.postResults().size()).isEqualTo(PAGE_SIZE);
	}

	@Test
	@DisplayName("게시글 상세 내용 조회시 답글이 있는 경우 답글이 같이 내려오는지 확인")
	void getPostDetail() {

		Long samplePostId = 1L;

		PostDetailInfoResponse infoResponse = postReadService.getPostDetail(samplePostId);

		System.out.println(infoResponse);
	}

}