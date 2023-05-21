package toy.com.post.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import toy.com.post.dto.request.ReplyCreateRequest;
import toy.com.post.dto.request.ReplyModifyRequest;
import toy.com.post.service.ReplyCommandService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reply")
@Tag(name = "reply-controller", description = "댓글 관련 API")
public class ReplyController {

	private final ReplyCommandService replyCommandService;

	@Operation(summary = "댓글 작성", description = "댓글을 작성한다")
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public void createReply(@RequestBody ReplyCreateRequest request) {
		replyCommandService.createReply(request);
	}

	@Operation(summary = "댓글 수정", description = "댓글을 수정한다")
	@PatchMapping
	public void modifyReply(@RequestBody ReplyModifyRequest request) {
		replyCommandService.modifyReply(request);
	}

	@Operation(summary = "댓글 삭제", description = "댓글을 삭제한다")
	@DeleteMapping("/{replyId}")
	public void deleteReply(@PathVariable Long replyId) {
		replyCommandService.deleteReply(replyId);
	}

	@Operation(summary = "댓글 좋아요", description = "댓글 좋아요를 한다")
	@PostMapping("/like/{replyId}")
	public void likeReply(@PathVariable Long replyId) {
		replyCommandService.likeReply(replyId);
	}

	@Operation(summary = "댓글 좋아요 취소", description = "댓글 좋아요를 취소 한다")
	@DeleteMapping("/like/{replyId}")
	public void disLikeReply(@PathVariable Long replyId) {
		replyCommandService.disLikeReply(replyId);
	}
}
