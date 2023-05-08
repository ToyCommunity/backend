package toy.com.user.service;

import static org.apache.tomcat.util.codec.binary.Base64.decodeBase64;

import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import toy.com.exception.CustomException;
import toy.com.exception.code.ErrorCode;
import toy.com.user.dto.response.TokenResponse;

@Component
public class TokenService {

	private final String secretKey;
	private final long accessTokenExpiredTime;
	private final long refreshTokenExpiredTime;

	public TokenService(@Value("${jwt.secret-key}") String secretKey,
		@Value("${jwt.access-token-expired-time}") long accessTokenExpiredTime,
		@Value("${jwt.refresh-token-expired-time}") long refreshTokenExpiredTime) {
		this.secretKey = secretKey;
		this.accessTokenExpiredTime = accessTokenExpiredTime;
		this.refreshTokenExpiredTime = refreshTokenExpiredTime;
	}

	public TokenResponse issueToken(Long id, String nickname) {
		Claims claims = Jwts.claims();
		claims.put("id", id);

		String accessToken = Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiredTime))
			.signWith(Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey))) // secretKey -> byte -> Key
			.compact();

		String refreshToken = Jwts.builder()
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpiredTime))
			.signWith(Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey)))
			.compact();

		return new TokenResponse(id, nickname, accessToken, refreshToken);
	}

	public Claims validate(String token) {
		Jws<Claims> claims;
		try {
			claims = Jwts.parserBuilder()
				.setSigningKey(decodeBase64(this.secretKey))
				.build()
				.parseClaimsJws(token);

		} catch (ExpiredJwtException e) {
			throw new CustomException(ErrorCode.EXPIRED_TOKEN);
		} catch (JwtException e) {
			throw new CustomException(ErrorCode.TOKEN_ERROR);
		}
		return claims.getBody();
	}
}
