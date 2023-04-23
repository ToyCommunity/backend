package toy.com.post.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import toy.com.post.dto.request.PostCreateRequest;
import toy.com.post.dto.request.PostModifyRequest;
import toy.com.post.dto.response.PostDetailInfoResponse;
import toy.com.post.dto.response.PostListsResponse;
import toy.com.post.service.PostReadService;
import toy.com.post.service.PostWriteService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
@Tag(name = "post-controller", description = "게시글 관련 API")
public class PostController {

	private static final int DEFAULT_PAGE_COUNT = 20;

	private final PostReadService postReadService;
	private final PostWriteService postWriteService;

	@Operation(summary = "게시글 작성", description = "게시글 작성을 요청한다")
	@PostMapping
	public void createPost(@RequestBody PostCreateRequest postCreateRequest) {
		postWriteService.createPost(postCreateRequest);
	}

	@Operation(summary = "게시글 수정", description = "게시글 수정을 요청한다")
	@PatchMapping
	public void modifyPost(@RequestBody PostModifyRequest postModifyRequest) {
		postWriteService.modifyPost(postModifyRequest);
	}

	@Operation(summary = "게시글 삭제", description = "게시글 삭제를 요청한다")
	@DeleteMapping("/{postId}")
	public void modifyPost(@PathVariable Long postId) {
		postWriteService.deletePost(postId);
	}

	@Operation(summary = "게시글 리스트 조회", description = "게시글 리스트를 요청한다")
	@GetMapping(value = "/list", produces = "application/json")
	public PostListsResponse getPostList(@PageableDefault(size = DEFAULT_PAGE_COUNT) Pageable page) {
		return postReadService.getPostListPerPage(page);
	}

	@Operation(summary = "게시글 상세 조회", description = "게시글 상세 내용을 요청한다")
	@GetMapping(value = "/detail/{postId}", produces = "application/json")
	public PostDetailInfoResponse getPostDetail(@PathVariable Long postId) {
		return postReadService.getPostDetail(postId);
	}

	@Operation(summary = "게시글 좋아요", description = "게시글 좋아요를 한다")
	@PostMapping(value = "/like/{postId}", produces = "application/json")
	public void likePost(@PathVariable Long postId) {
		postWriteService.updateLikePost(postId);
	}

}
