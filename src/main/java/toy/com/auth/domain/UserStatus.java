package toy.com.auth.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserStatus {

    NORMAL("정상"),
    WITHDRAWAL("탈퇴"),
    REPORTED("신고")
    ;

    private final String desc;
}
