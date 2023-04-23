package toy.com.post.service;

import static toy.com.exception.code.ErrorCode.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import toy.com.exception.CustomException;
import toy.com.post.dto.response.PostDetailInfoResponse;
import toy.com.post.dto.response.PostListsResponse;
import toy.com.post.repository.PostRepository;
import toy.com.user.domain.User;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostQueryService {

	private static final int DEFAULT_PAGE_COUNT = 20;

	private final PostRepository postRepository;
	// TODO(박종빈) 순환참조 주의
	private final PostWriteService postWriteService;

	public PostListsResponse getPostListPerPage(Pageable page) {

		int totalPosts = postRepository.countBy();

		return PostListsResponse.builder()
			.postResults(getPostPerPage(page))
			.page(page.getPageNumber())
			.perCounts(getPostPerPage(page).size())
			.totalPosts(totalPosts)
			.totalPages(totalPosts / DEFAULT_PAGE_COUNT)
			.build();
	}

	private List<PostDetailInfoResponse> getPostPerPage(Pageable page) {
		return postRepository.findAllByOrderByCreatedAtDesc(page).stream()
			.map(post -> PostDetailInfoResponse.builder()
				.postId(post.getId())
				.title(post.getPostTitle())
				.userId(post.getPostWriter().getId())
				.userName(post.getPostWriter().getNickname())
				.category(post.getPostCategory().getCode())
				.createdAt(post.getCreatedAt())
				.likeCounts(post.getLikeCounts())
				.viewCounts(post.getViewCounts())
				.build())
			.collect(Collectors.toList());
	}

	public PostDetailInfoResponse getPostDetail(Long postId) {

		// TODO(박종빈) 추후 유저 벧리데이션으로 유저값 갖고옴
		// 테스트를 위한 셈플
		User searchUser = User.builder()
			.email("useremail@naver.com")
			.password("xzcjzkxcjxz1212")
			.nickname("댓글유저222")
			.build();

		postWriteService.updatePostViewCount(postId);

		return postRepository.findById(postId)
			.map(post -> PostDetailInfoResponse.builder()
				.postId(post.getId())
				.title(post.getPostTitle())
				.userId(post.getPostWriter().getId())
				.userName(post.getPostWriter().getNickname())
				.category(post.getPostCategory().getCode())
				.createdAt(post.getCreatedAt())
				.likeCounts(post.getLikeCounts())
				.viewCounts(post.getViewCounts())
				.reply(post.getReplies(searchUser))
				.postMyReaction(post.isPostHasMyReaction(searchUser))
				.build())
			.orElseThrow(() -> new CustomException(NOT_FOUND_POST));
	}

}
