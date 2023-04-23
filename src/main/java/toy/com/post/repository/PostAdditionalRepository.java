package toy.com.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import toy.com.post.domain.PostAdditional;

public interface PostAdditionalRepository extends JpaRepository<PostAdditional, Long> {

}
