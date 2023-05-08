package toy.com.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

	private final String redisHost;
	private final int redisPort;

	public RedisConfig(@Value("${spring.redis.host}") String redisHost,
		@Value("${spring.redis.port}") int redisPort) {
		this.redisHost = redisHost;
		this.redisPort = redisPort;
	}

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		return new LettuceConnectionFactory(redisHost, redisPort);
	}

	@Bean
	public RedisTemplate<Long, String> tokenRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<Long, String> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		redisTemplate.setKeySerializer(new Jackson2JsonRedisSerializer<>(Long.class));
		redisTemplate.setValueSerializer(new StringRedisSerializer());
		return redisTemplate;
	}
}
