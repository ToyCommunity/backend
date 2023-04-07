package toy.com.user.dto.response;

/*
TODO(박종빈) : 테스트를 위한 dto임으로 추후 header로 토큰 저장 로직 생성시 삭제
 */

import io.swagger.v3.oas.annotations.media.Schema;

public record JwtResponse(@Schema(description = "생성된 jwt") String jwt) {
}
