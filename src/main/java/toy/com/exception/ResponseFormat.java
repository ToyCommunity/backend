package toy.com.exception;

import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import toy.com.exception.code.ErrorCode;

/**
 *  응답형식(실패)
 *  {
 *  "result": "ERROR",
 *  "detail": {
 *  "code": "U-01",
 *  "message": "이메일이 중복되었습니다"
 *  }
 *  }
 */
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL) // null인 필드는 무시합니다.
public class ResponseFormat {

	private static final String ERROR = "ERROR";

	private final String result;
	private final Detail detail;

	public ResponseFormat(String result, ErrorCode errorCode) {
		this.result = result;
		this.detail = new Detail(errorCode);
	}

	public static ResponseEntity<ResponseFormat> toResponseEntity(ErrorCode errorCode) {
		return ResponseEntity.status(errorCode.getStatus()).body(new ResponseFormat(ERROR, errorCode));
	}

	@Getter
	private static class Detail {
		private final String code;
		private final String message;

		public Detail(ErrorCode errorCode) {
			this.code = errorCode.getCode();
			this.message = errorCode.getMessage();
		}
	}
}
