package toy.com.post.dto.request;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record PostCreateRequest(@Schema(description = "게시글 제목")
								@NotBlank(message = "제목을 작성해 주세요")
								String title,
								@NotBlank(message = "내용을 작성해 주세요")
								@Schema(description = "게시글 내용")
								String content) {

}
