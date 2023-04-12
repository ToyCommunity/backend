package toy.com.user.service;

import java.time.Duration;
import java.util.Optional;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

/**
 * <refreshToken, id>
 */
@RequiredArgsConstructor
@Component
public class RedisService {

	private final RedisTemplate<String, Long> token;

	public void setToken(String refreshToken, Long id) {
		token.opsForValue().set(refreshToken, id, Duration.ofDays(7));
	}

	public Optional<Long> getRefreshToken(String refreshToken) {
		return Optional.ofNullable(token.opsForValue().get(refreshToken));
	}
}
