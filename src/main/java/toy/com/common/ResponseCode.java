package toy.com.common;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseCode {
    OK(0, "요청성공")
    ,ERROR(-1, "에러반환")
    ;
    private final int code;
    private final String message;
}
