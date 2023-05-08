package toy.com.user.service;

import java.time.Duration;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

/**
 * <refreshToken, id>
 */
@RequiredArgsConstructor
@Component
public class RedisService {

	private final RedisTemplate<Long, String> redisTemplate;

	public void setToken(Long id, String refreshToken) {
		redisTemplate.opsForValue().set(id, refreshToken, Duration.ofDays(7));
	}

	public String getTokenById(Long id) {
		return redisTemplate.opsForValue().get(id);
	}

	public void deleteToken(Long id) {
		redisTemplate.delete(id);
	}
}
