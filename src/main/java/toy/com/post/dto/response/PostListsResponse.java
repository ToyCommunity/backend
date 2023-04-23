package toy.com.post.dto.response;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import toy.com.post.dto.PostDetailInfoResponse;

@Builder
public record PostListsResponse(@Schema(description = "현재 요청된 페이지")
								int page,
								@Schema(description = "현재 요청된 페이지에 반환되는 게시글 개수")
								int perCounts,
								@Schema(description = "게시글 리스트 상세 내역")
								List<PostDetailInfoResponse> postResults,
								@Schema(description = "DB에 저장되있는 전체 게시글 개수")
								int totalPosts,
								@Schema(description = "전체 페이지 개수 (전체 저장게시글 / 페이지당 요청 개수 게시글)")
								int totalPages) {

}
