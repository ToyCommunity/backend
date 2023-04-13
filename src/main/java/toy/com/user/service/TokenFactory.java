package toy.com.user.service;

import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import toy.com.user.dto.response.TokenResponse;

@Component
public class TokenFactory {

	private final String secretKey;
	private final long accessTokenExpiredTime;
	private final long refreshTokenExpiredTime;

	public TokenFactory(@Value("${jwt.secret-key}") String secretKey,
		@Value("${jwt.access-token-expired-time}") long accessTokenExpiredTime,
		@Value("${jwt.refresh-token-expired-time}") long refreshTokenExpiredTime) {
		this.secretKey = secretKey;
		this.accessTokenExpiredTime = accessTokenExpiredTime;
		this.refreshTokenExpiredTime = refreshTokenExpiredTime;
	}

	public TokenResponse issueToken(Long id) {
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

		return new TokenResponse(accessToken, this.accessTokenExpiredTime, refreshToken, this.refreshTokenExpiredTime);
	}
}
