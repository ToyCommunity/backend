package toy.com.post.domain;

import java.util.Arrays;
import java.util.Objects;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import toy.com.exception.CustomException;
import toy.com.exception.code.ErrorCode;

@Getter
@RequiredArgsConstructor
public enum ReplyStatus {

	NORMAL("N", "정상"),
	DELETED("D", "삭제"),
	REPORTED("R", "신고"),
	HIDE("H", "숨김");

	private final String code;
	private final String desc;

	public static ReplyStatus ofCode(String code) {
		return Arrays.stream(values())
			.filter(status -> Objects.equals(status.getCode(), code))
			.findFirst()
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_REPLY_STATUS));
	}
}
