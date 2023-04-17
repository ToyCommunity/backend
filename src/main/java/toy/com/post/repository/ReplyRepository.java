package toy.com.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import toy.com.post.domain.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
}
