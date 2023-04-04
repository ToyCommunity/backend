package toy.com.auth.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toy.com.auth.exception.UserStatusException;

/*
TODO(박종빈)
 1. 해당 엔티티는 user 도메인으로 이관(패키지 분리 필요)
 2. user class 이름 생각 해보기
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userSeq;

    private String userId;

    private String passWord;

    private String userNickName;

    private String userProfile;

    private UserRole userRole;

    private String userJwt;

    private UserStatus userStatus;

    @Builder
    public Users(Long userSeq,
                String userId,
                String passWord,
                String userNickName,
                String userProfile,
                UserRole userRole,
                String userJwt,
                UserStatus userStatus) {

        this.userSeq = userSeq;
        this.userId = userId;
        this.passWord = passWord;
        this.userNickName = userNickName;
        this.userProfile = userProfile;
        this.userRole = userRole;
        this.userJwt = userJwt;
        this.userStatus = userStatus;
    }

    // 유저 상태를 확인한다
    public void checkUserStatus() {

        if (UserStatus.REPORTED == userStatus) {
            throw new UserStatusException("신고로 등록된 유저입니다");
        }
        if (UserStatus.WITHDRAWAL == userStatus) {
            throw new UserStatusException("탈퇴한 회원입니다");
        }
    }

    // 유저 토큰상태를 업데이트 한다
    public void updateUserJwt(String jwt) {
        this.userJwt = jwt;
    }
}
