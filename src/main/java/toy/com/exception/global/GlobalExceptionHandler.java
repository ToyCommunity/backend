package toy.com.exception.global;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import toy.com.exception.CustomException;
import toy.com.exception.ResponseFormat;
import toy.com.exception.code.ErrorCode;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ResponseFormat<?>> customExceptionHandler(CustomException e) {
		return ResponseFormat.toResponseEntity(e.getErrorcode());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ResponseFormat<?>> ExceptionHandler() {
		return ResponseFormat.toResponseEntity(ErrorCode.ERROR);
	}
}
