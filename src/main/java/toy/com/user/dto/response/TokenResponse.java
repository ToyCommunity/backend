package toy.com.user.dto.response;

public record TokenResponse(
	Long id,
	String nickname,
	String accessToken,
	String refreshToken
) {
}
