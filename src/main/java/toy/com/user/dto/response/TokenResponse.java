package toy.com.user.dto.response;

public record TokenResponse(
	String accessToken,
	long accessTokenExpiredTime,
	String refreshToken,
	long refreshTokenExpiredTime
) {
}
