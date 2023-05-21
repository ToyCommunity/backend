package toy.com.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import toy.com.post.domain.ReplyAdditional;

public interface ReplyAdditionalRepository extends JpaRepository<ReplyAdditional, Long> {
	void deleteByReactionReplyId(Long replyId);
}
