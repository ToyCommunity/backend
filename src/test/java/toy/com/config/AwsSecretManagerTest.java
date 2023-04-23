package toy.com.config;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AwsSecretManagerTest {

	@Value("${jwt.access-token-expired-time}")
	private String accessExpiredTime;

	@Value("${jwt.refresh-token-expired-time}")
	private String refreshExpiredTime;

	@Test
	@DisplayName("로컬에서 secretManager를 통해 값이 불러와 지는지 확인 ")
	void secretKeyTest() {

		assertThat(accessExpiredTime).isEqualTo("1800000");

		assertThat(refreshExpiredTime).isEqualTo("604800000");
	}
}
