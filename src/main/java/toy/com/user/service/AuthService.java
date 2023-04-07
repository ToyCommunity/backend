package toy.com.user.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import toy.com.user.domain.User;
import toy.com.user.domain.UserStatus;
import toy.com.user.dto.request.UserJoinReq;
import toy.com.user.dto.response.JwtResponse;
import toy.com.user.repository.AuthRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final AuthRepository authRepository;

	@Value("${spring.auth.secret}")
	private String secretKey;

	/**
	 * 새로운 유저 생성
	 *
	 * @param req
	 * @return
	 */
	public JwtResponse createJoinUserInfo(UserJoinReq req) {
		// 1. userId로 user validation 진행 및 반환
		User user = validateReqJoinUser(req);
		// 2. 유저 토큰 생성
		String jwt = createJwt(user);
		// 3. 유저 정보 및 갱신
		createAndUpdateUser(user, jwt);
		// 4. 토큰 객체 반환
		return new JwtResponse(jwt);
	}

	/**
	 * 유저 밸리데이션 체크 및 새로운 유저 반환
	 *
	 * @param req
	 * @return
	 */
	private User validateReqJoinUser(UserJoinReq req) {
		User alreadyJoinUser = authRepository.findByUserId(req.id());
		// 이미 가입된 유저라면 밸리데이션 체크
		if (Objects.nonNull(alreadyJoinUser)) {
			alreadyJoinUser.checkUserStatus();
			return alreadyJoinUser;
		}
		// 새로운 유저라면 유저 객체 생성 및 반환
		return User.builder()
			.email(req.id())
			.nickname(req.nickName())
			.password(req.pass())
			.userStatus(UserStatus.NORMAL)
			.build();
	}

	/**
	 * 유저 정보를 바탕으로 Jwt 생성 -> TODO(박종빈) 추후 서비스클래스 변경
	 *
	 * @param user
	 */
	private String createJwt(User user) {
		Date issuedDate = new Date(System.currentTimeMillis());
		Date expireDate = java.sql.Timestamp.valueOf(LocalDateTime.now().plusYears(1));
		Claims claims = Jwts.claims().setSubject(user.getEmail());
		// 현재 유저 정보로 토큰 생성
		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(issuedDate)
			.setExpiration(expireDate)
			.signWith(SignatureAlgorithm.HS256, secretKey)
			.compact();

	}

	/**
	 * 유저 생성 및 정보 갱신
	 *
	 * @param user
	 * @param jwt
	 */
	private void createAndUpdateUser(User user, String jwt) {

		authRepository.save(user); // TODO(박종빈) -> 추후 user repository 생성시 리포지토리  변경
	}
}
