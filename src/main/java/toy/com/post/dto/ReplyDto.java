package toy.com.post.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import toy.com.post.domain.Reply;
import toy.com.post.domain.ReplyStatus;

public record ReplyDto(String content,
					   int replyLikes,
					   ReplyStatus replyStatus,
					   Long replyWriterId,
					   String replyWriterEmai,
					   String replyWriterNickName,
					   LocalDateTime createdAt) {

	@Builder
	public ReplyDto {}

	public static ReplyDto entityToDto(Reply reply) {
		return ReplyDto.builder()
			.content(reply.getContent())
			.replyLikes(reply.getReplyLikes())
			.replyStatus(reply.getReplyStatus())
			.replyWriterId(reply.getReplyWriter().getId())
			.replyWriterNickName(reply.getReplyWriter().getNickname())
			.replyWriterEmai(reply.getReplyWriter().getEmail())
			.createdAt(reply.getCreatedAt())
			.build();

	}
}
