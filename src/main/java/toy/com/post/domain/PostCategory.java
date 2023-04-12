package toy.com.post.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PostCategory {

	DEFAULT(1, "기본"),
	SAMPLE(2, "샘플");

	private final int code;
	private final String desc;
}
