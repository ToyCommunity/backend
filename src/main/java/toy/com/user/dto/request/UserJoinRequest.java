package toy.com.user.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import toy.com.common.util.PasswordEncrypt;
import toy.com.user.domain.User;

@Builder
public record UserJoinRequest(
	@NotBlank(message = "이메일을 입력해 주세요")
	@Email @Size(min = 8, max = 50) @Pattern(regexp = "^[\\\\w!#$%&'*+/=?`{|}~^-]+(?:\\\\.[\\\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\\\.)+[a-zA-Z]{2,6}$\"")
	@Schema(description = "유저 이메일")
	String email,

	@NotBlank(message = "password를 입력해 주세요")
	@Size(min = 8, max = 20) @Pattern(regexp = "^[a-zA-Z0-9가-힣]+$")
	@Schema(description = "유저 password")
	String password,

	@NotBlank(message = "닉네임을 입력해주세요")
	@Size(min = 2, max = 10) @Pattern(regexp = "^[a-zA-Z0-9가-힣]+$")
	@Schema(description = "유저 nickname")
	String nickname
) {

	public User toEntity() {
		return User.builder()
			.email(this.email())
			.password(PasswordEncrypt.encrypt(this.password()))
			.nickname(this.nickname())
			.build();
	}
}
