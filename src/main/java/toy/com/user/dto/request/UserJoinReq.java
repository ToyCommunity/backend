package toy.com.user.dto.request;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

public record UserJoinReq(@NotBlank(message = "id 를 입력해 주세요")
						  @Schema(description = "유저 Id")
						  String id,
						  @NotBlank(message = "pass 를 입력 해 주세요")
						  @Schema(description = "유저 passWord")
						  String pass,
						  @NotBlank(message = "닉네임을 입력하세요")
						  @Schema(description = "유저 nickName")
						  String nickName) {
	@Builder
	public UserJoinReq(String id, String pass, String nickName) {
		this.id = id;
		this.pass = pass;
		this.nickName = nickName;
	}
}
