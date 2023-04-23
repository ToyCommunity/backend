package toy.com.post.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import toy.com.post.dto.response.PostDetailInfoResponse;
import toy.com.post.dto.response.PostListsResponse;
import toy.com.post.service.PostQueryService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
@Tag(name = "post-controller", description = "게시글 관련 API")
public class PostController {

	private static final int DEFAULT_PAGE_COUNT = 20;

	private final PostQueryService postQueryService;

	@Operation(summary = "게시글 리스트 조회", description = "게시글 리스트를 요청한다")
	@GetMapping(value = "/list", produces = "application/json")
	public PostListsResponse getPostList(@PageableDefault(page = 1, size = DEFAULT_PAGE_COUNT) Pageable page) {
		return postQueryService.getPostListPerPage(page);
	}

	@Operation(summary = "게시글 상세 조회", description = "게시글 상세 내용을 요청한다")
	@GetMapping(value = "/detail/{postId}", produces = "application/json")
	public PostDetailInfoResponse getPostDetail(@PathVariable Long postId) {
		return postQueryService.getPostDetail(postId);
	}

}
