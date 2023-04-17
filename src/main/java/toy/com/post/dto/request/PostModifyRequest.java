package toy.com.post.dto.request;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import toy.com.post.domain.PostCategory;

public record PostModifyRequest(@NotBlank(message = "수정하려는 게시글 번호는 비어있을 수 없습니다")
								@Schema(description = "게시글 번호")
								Long postId,
								@Schema(description = "게시글 제목")
								String title,
								@Schema(description = "게시글 내용")
								String content,
								@Schema(description = "게시글 카테고리")
								PostCategory category) {
}
