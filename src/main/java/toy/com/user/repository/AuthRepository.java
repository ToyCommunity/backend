package toy.com.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import toy.com.user.domain.User;

public interface AuthRepository extends JpaRepository<User, Long> {

	User findByUserId(String id);
}
