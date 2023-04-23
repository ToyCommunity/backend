package toy.com.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 예외상황에대한 공통코드만을 관리한 클래스
 * 해당클래스가 엄청 무거워진다면 CommonCode만 놔두고 UserCode,BoardCode 등으로 나눠서 관리할것을 염두했습니다.
 */

@Getter
@RequiredArgsConstructor
public enum CommonCode {
	// Common-000
	OK("C-000"),
	ERROR("C-999"),

	// User-000
	DUPLICATED_EMAIL("U-001"),
	WRONG_FORM_INPUT("U-002"),
	NOT_FOUND_USER("U-003"),
	PASSWORD_NOT_MATCH("U-004"),

	// Post-100
	NOT_FOUND_POST_STATUS("P-101");

	private final String code;
}
