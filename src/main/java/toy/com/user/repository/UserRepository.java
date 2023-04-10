package toy.com.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import toy.com.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

	boolean existsByEmail(String email);
}
