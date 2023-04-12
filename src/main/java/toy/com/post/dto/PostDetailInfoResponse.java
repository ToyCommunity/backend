package toy.com.post.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import toy.com.post.domain.Post;
import toy.com.post.domain.Reply;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PostDetailInfoResponse(@Schema(description = "게시글 id")
									 Long postId,
									 @Schema(description = "게시글 제목")
									 String title,
									 @Schema(description = "게시글 작성 유저 id")
									 Long userId,
									 @Schema(description = "게시글 작성 유저 name")
									 String userName,
									 @Schema(description = "게시글 작성 유저 프로필 이미지")
									 String userProfileImg,
									 @Schema(description = "게시글 카테고리 코드")
									 int category,
									 @Schema(description = "게시글 좋아요 개수")
									 int likeCounts,
									 @Schema(description = "게시글 조회 개수")
									 int viewCounts,
									 @Schema(description = "현재 접속 유저가 해당 게시글 좋아요 선택 여부")
									 boolean postMyReaction,
									 @Schema(description = "현재 게시글의 답글")
									 List<Reply> reply,
									 @Schema(description = "게시글 생성일자")
									 LocalDateTime createdAt) {

	public static PostDetailInfoResponse of(Post post) {
		return new PostDetailInfoResponseBuilder()
			.postId(post.getId())
			.title(post.getPostTitle())
			.userId(post.getPostWriter().getId())
			.userName(post.getPostWriter().getNickname())
			.category(post.getPostCategory().getCode())
			.createdAt(post.getCreatedAt())
			.likeCounts(post.getLikeCounts())
			.viewCounts(post.getViewCounts())
			.reply(post.getReplies())
			.build();
	}

	public static PostDetailInfoResponse ofDetail(Post post, Long userId) {
		return new PostDetailInfoResponseBuilder()
			.postId(post.getId())
			.title(post.getPostTitle())
			.userId(post.getPostWriter().getId())
			.userName(post.getPostWriter().getNickname())
			.category(post.getPostCategory().getCode())
			.createdAt(post.getCreatedAt())
			.likeCounts(post.getLikeCounts())
			.viewCounts(post.getViewCounts())
			.postMyReaction(post.isReactionPost(userId))
			.build();
	}
}
