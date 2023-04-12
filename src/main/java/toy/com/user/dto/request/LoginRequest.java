package toy.com.user.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Builder;
import toy.com.user.domain.User;

@Builder
public record LoginRequest(
	@NotBlank(message = "이메일을 입력해주세요.")
	@Email @Size(min = 8, max = 50) @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
	String email,
	@NotBlank(message = "비밀번호를 입력해주세요")
	@Size(min = 8, max = 20) @Pattern(regexp = "^[a-zA-Z0-9가-힣]+$")
	String password
) {

	public User toEntity() {
		return User.builder()
			.email(this.email())
			.password(this.password())
			.build();
	}
}
