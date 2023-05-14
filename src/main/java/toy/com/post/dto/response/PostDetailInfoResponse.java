package toy.com.post.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import toy.com.post.dto.ReplyDto;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PostDetailInfoResponse(@Schema(description = "게시글 id")
									 Long postId,
									 @Schema(description = "게시글 제목")
									 String title,
									 @Schema(description = "게시글 작성 유저 id")
									 Long userId,
									 @Schema(description = "게시글 작성 유저 nickname")
									 String nickname,
									 @Schema(description = "게시글 작성 유저 프로필 이미지")
									 String userProfileImg,
									 @Schema(description = "게시글 내용")
									 String content,
									 @Schema(description = "게시글 카테고리 코드")
									 int category,
									 @Schema(description = "게시글 좋아요 개수")
									 int likeCounts,
									 @Schema(description = "게시글 조회 개수")
									 int viewCounts,
									 @Schema(description = "현재 접속 유저가 해당 게시글 좋아요 선택 여부")
									 boolean postMyReaction,
									 @Schema(description = "현재 게시글의 답글")
									 List<ReplyDto> replies,
									 @Schema(description = "게시글 생성일자")
									 LocalDateTime createdAt) {

	@Builder
	public PostDetailInfoResponse {}
}
