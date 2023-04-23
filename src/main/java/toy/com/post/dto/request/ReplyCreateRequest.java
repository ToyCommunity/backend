package toy.com.post.dto.request;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;

public record ReplyCreateRequest(@NotBlank(message = "게시글 아이디가 없습니다")
								 @Schema(description = "댓글이 작성될 게시글번호")
								 Long postId,
								 @NotBlank(message = "내용을 입력해주세요")
								 @Schema(description = "댓글 내용")
								 String content) {
}
