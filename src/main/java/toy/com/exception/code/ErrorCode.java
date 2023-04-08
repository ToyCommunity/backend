package toy.com.exception.code;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
	//2XX
	OK(CommonCode.OK.getCode(), HttpStatus.OK, "요청 성공"),

	//4XX
	DUPLICATED_EMAIL(CommonCode.DUPLICATED_EMAIL.getCode(), HttpStatus.CONFLICT, "이메일이 중복되었습니다"),

	//5XX
	ERROR(CommonCode.ERROR.getCode(), HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러");

	private final String code;
	private final HttpStatus status;
	private final String message;
}
