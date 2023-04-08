package toy.com.exception;

import lombok.Getter;
import toy.com.exception.code.ErrorCode;

@Getter
public class CustomException extends RuntimeException {

	private final ErrorCode errorcode;

	public CustomException(ErrorCode errorcode) {
		this.errorcode = errorcode;
	}

	public CustomException(String message, ErrorCode errorcode) {
		super(message);
		this.errorcode = errorcode;
	}
}
