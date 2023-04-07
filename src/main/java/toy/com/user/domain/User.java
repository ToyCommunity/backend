package toy.com.user.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toy.com.user.exception.UserStatusException;

@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 50)
	private String email;

	@Column(nullable = false, length = 20)
	private String password;

	@Column(nullable = false, length = 10)
	private String nickname;

	@Enumerated(EnumType.STRING)
	private UserRole userRole;

	@Enumerated(EnumType.STRING)
	private UserStatus userStatus;

	@Builder
	public User(String email, String password, String nickname, UserRole userRole, UserStatus userStatus) {
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.userRole = userRole;
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
}
