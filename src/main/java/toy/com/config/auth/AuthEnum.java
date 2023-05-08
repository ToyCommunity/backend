package toy.com.config.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthEnum {
	HEADER("Authorization"),
	TOKEN_TYPE("Bearer ");

	private final String value;
}
