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
	WRONG_FORM_INPUT(CommonCode.WRONG_FORM_INPUT.getCode(), HttpStatus.BAD_REQUEST, "적절하지 않은 폼 입력값입니다."),
	NOT_FOUND_USER(CommonCode.NOT_FOUND_USER.getCode(), HttpStatus.BAD_REQUEST, "존재하지 않는 유저입니다"),
	PASSWORD_NOT_MATCH(CommonCode.PASSWORD_NOT_MATCH.getCode(), HttpStatus.BAD_REQUEST, "이메일 혹은 비밀번호가 일치하지 않습니다."),
	NOT_FOUND_POST_STATUS(CommonCode.NOT_FOUND_POST_STATUS.getCode(), HttpStatus.BAD_REQUEST, "존재하지 않는 게시글 상태입니다"),
	NOT_FOUND_POST(CommonCode.NOT_FOUND_POST.getCode(), HttpStatus.BAD_REQUEST, "존재하지 않은 게시글 입니다"),
	NOT_FOUND_REPLY_STATUS(CommonCode.NOT_FOUND_REPLY_STATUS.getCode(), HttpStatus.BAD_REQUEST, "존재하지 않은 댓글 상태입니다"),
	NOT_FOUND_REPLY(CommonCode.NOT_FOUND_REPLY.getCode(), HttpStatus.BAD_REQUEST, "존재하지 않는 댓글입니다"),

	//5XX
	ERROR(CommonCode.ERROR.getCode(), HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러");

	private final String code;
	private final HttpStatus status;
	private final String message;
}
