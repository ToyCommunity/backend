package toy.com.config;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import redis.embedded.RedisServer;

@Profile("local")
@Configuration
public class EmbeddedRedis {

	private final int redisPort;
	private static RedisServer redisServer;

	public EmbeddedRedis(@Value("${spring.redis.port}") final int redisPort) {
		this.redisPort = redisPort;
	}

	@PostConstruct
	public void redisServer() {
		if (redisServer == null) {
			redisServer = RedisServer.builder()
				.port(redisPort)
				.setting("maxmemory 128M")
				.build();

			redisServer.start();
		}
	}

	@PreDestroy
	public void stopRedis() {
		if (redisServer != null) {
			redisServer.stop();
		}
	}
}
