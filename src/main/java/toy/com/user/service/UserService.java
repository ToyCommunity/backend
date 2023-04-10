package toy.com.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import toy.com.exception.CustomException;
import toy.com.exception.code.ErrorCode;
import toy.com.user.domain.User;
import toy.com.user.repository.UserRepository;

/**
 * 조금더 규모가 커진다면  Query, Command Service로 분리할수있다.
 */
@RequiredArgsConstructor
@Service
@Transactional
public class UserService {

	private final UserRepository userRepository;

	public void join(User user) {
		if (userRepository.existsByEmail(user.getEmail())) {
			throw new CustomException(ErrorCode.DUPLICATED_EMAIL);
		}

		userRepository.save(user);
	}

	@Transactional(readOnly = true)
	public User findById(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
	}
}
