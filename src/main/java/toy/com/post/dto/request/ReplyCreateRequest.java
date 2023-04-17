package toy.com.post.dto.request;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;

public record ReplyCreateRequest(@NotBlank(message = "내용을 입력해주세요")
								 @Schema(description = "댓글 내용")
								 String content) {
}
