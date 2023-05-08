package toy.com.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import toy.com.exception.CustomException;
import toy.com.exception.code.ErrorCode;
import toy.com.user.PasswordEncrypt;
import toy.com.user.domain.User;
import toy.com.user.dto.response.TokenResponse;
import toy.com.user.repository.UserRepository;

/**
 * 조금더 규모가 커진다면  Query, Command Service로 분리할수있다.
 */
@RequiredArgsConstructor
@Service
@Transactional
public class UserService {

	private final UserRepository userRepository;
	private final TokenService tokenService;
	private final RedisService redisService;

	public void join(User user) {
		if (userRepository.existsByEmail(user.getEmail())) {
			throw new CustomException(ErrorCode.DUPLICATED_EMAIL);
		}

		userRepository.save(user);
	}

	public TokenResponse login(User user) {
		User joinUser = userRepository.findByEmail(user.getEmail())
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

		if (!PasswordEncrypt.isMatch(user.getPassword(), joinUser.getPassword())) {
			throw new CustomException(ErrorCode.PASSWORD_NOT_MATCH);
		}

		TokenResponse tokenResponse = tokenService.issueToken(joinUser.getId(), joinUser.getNickname());
		redisService.setToken(joinUser.getId(), tokenResponse.refreshToken());
		return tokenResponse;
	}

	@Transactional(readOnly = true)
	public User findById(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
	}

	public void logout(Long id) {
		User user = userRepository.findById(id)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

		redisService.deleteToken(user.getId());
	}
}
