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

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostQueryService {

	private static final int DEFAULT_PAGE_COUNT = 20;

	private final PostRepository postRepository;

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
			.map(PostDetailInfoResponse::ofList)
			.collect(Collectors.toList());
	}

	public PostDetailInfoResponse getPostDetail(Long postId) {
		// sample userId;
		Long sampleUserId = 1L;
		return postRepository.findById(postId)
			.map(post -> PostDetailInfoResponse.ofDetail(post, sampleUserId))
			.orElseThrow(() -> new CustomException(NOT_FOUND_POST));
	}

}
