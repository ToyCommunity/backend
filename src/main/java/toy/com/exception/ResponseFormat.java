package toy.com.exception;

import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import toy.com.exception.code.ErrorCode;

/**
 * 응답형식(성공)
 *  {
 *  "result": "SUCCESS",
 *  "data": {
 *  "email": "asdsafasf@naver.com"
 *  "nickname": "sadman"
 *  }
 *  }
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
public class ResponseFormat<T> {

	private final String result;
	private final Detail detail;
	private final T data;

	// 실패시 사용하는 생성자
	public ResponseFormat(String result, ErrorCode errorCode) {
		this.result = result;
		this.detail = new Detail(errorCode);
		this.data = null;
	}

	// 성공시 사용하는 생성자
	public ResponseFormat(String result, T data) {
		this.result = result;
		this.detail = null;
		this.data = data;
	}

	public static <T> ResponseFormat<T> success(T data) {
		return new ResponseFormat<>("SUCCESS", data);
	}

	public static ResponseEntity<ResponseFormat<?>> toResponseEntity(ErrorCode errorCode) {
		return ResponseEntity.status(errorCode.getStatus()).body(new ResponseFormat<>("ERROR", errorCode));
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
