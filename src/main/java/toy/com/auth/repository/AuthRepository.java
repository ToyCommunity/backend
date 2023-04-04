package toy.com.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import toy.com.auth.domain.Users;

public interface AuthRepository extends JpaRepository<Users, Long> {

    Users findByUserId(String id);
}
