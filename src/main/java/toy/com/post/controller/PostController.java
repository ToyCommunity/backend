package toy.com.post.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import toy.com.post.dto.request.PostCreateRequest;
import toy.com.post.dto.request.PostModifyRequest;
import toy.com.post.dto.response.PostDetailInfoResponse;
import toy.com.post.dto.response.PostListsResponse;
import toy.com.post.service.PostCommandService;
import toy.com.post.service.PostQueryService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
@Tag(name = "post-controller", description = "게시글 관련 API")
public class PostController {

	private final PostQueryService postQueryService;
	private final PostCommandService postCommandService;

	@Operation(summary = "게시글 작성", description = "게시글 작성을 요청한다")
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public void createPost(@RequestBody PostCreateRequest postCreateRequest) {
		postCommandService.createPost(postCreateRequest);
	}

	@Operation(summary = "게시글 수정", description = "게시글 수정을 요청한다")
	@PatchMapping
	public void modifyPost(@RequestBody PostModifyRequest postModifyRequest) {
		postCommandService.modifyPost(postModifyRequest);
	}

	@Operation(summary = "게시글 삭제", description = "게시글 삭제를 요청한다")
	@DeleteMapping("/{postId}")
	public void modifyPost(@PathVariable Long postId) {
		postCommandService.deletePost(postId);
	}

	@Operation(summary = "게시글 리스트 조회", description = "게시글 리스트를 요청한다")
	@GetMapping(value = "/list", produces = "application/json")
	public PostListsResponse getPostList(Pageable page) {
		return postQueryService.getPostListPerPage(page);
	}

	@Operation(summary = "게시글 상세 조회", description = "게시글 상세 내용을 요청한다")
	@GetMapping(value = "/detail/{postId}", produces = "application/json")
	public PostDetailInfoResponse getPostDetail(@PathVariable Long postId) {
		postCommandService.updatePostViewCount(postId);
		return postQueryService.getPostDetail(postId);
	}

	@Operation(summary = "게시글 좋아요", description = "게시글 좋아요를 한다")
	@PostMapping(value = "/like/{postId}")
	public void likePost(@PathVariable Long postId) {
		postCommandService.likePost(postId);
	}

	@Operation(summary = "게시글 좋아요 취소", description = "게시글 좋아요를 취소 한다")
	@DeleteMapping(value = "/like/{postId}")
	public void dislikePost(@PathVariable Long postId) {
		postCommandService.disLikePost(postId);
	}

}
