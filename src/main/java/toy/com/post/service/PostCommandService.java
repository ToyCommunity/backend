package toy.com.post.service;

import static toy.com.exception.code.ErrorCode.*;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import toy.com.exception.CustomException;
import toy.com.post.domain.Post;
import toy.com.post.domain.PostAdditional;
import toy.com.post.domain.PostStatus;
import toy.com.post.dto.request.PostCreateRequest;
import toy.com.post.dto.request.PostModifyRequest;
import toy.com.post.repository.PostAdditionalRepository;
import toy.com.post.repository.PostRepository;
import toy.com.user.domain.User;

@Service
@Transactional
@RequiredArgsConstructor
public class PostCommandService {

	private final PostRepository postRepository;
	private final PostAdditionalRepository postAdditionalRepository;

	public void createPost(PostCreateRequest request) {

		// TODO(박종빈) 추후 유저 벧리데이션으로 유저값 갖고옴
		// 테스트를 위한 셈플
		User sampleUser = User.builder()
			.email("useremail@naver.com")
			.password("xzcjzkxcjxz1212")
			.nickname("닉네임11111")
			.build();

		postRepository.save(Post.builder()
			.postTitle(request.title())
			.postContent(request.content())
			.postCategory(request.category())
			.postStatus(PostStatus.NORMAL)
			.postWriter(sampleUser)
			.build());
	}

	public void modifyPost(PostModifyRequest request) {

		// TODO(박종빈) 추후 user validation 추가 필요

		Post post = findPostByPostId(request.postId());
		post.modifyPost(request);
	}

	public void deletePost(Long postId) {
		Post post = findPostByPostId(postId);
		post.deletePost();
	}

	public void updateLikePost(Long postId) {

		// TODO(박종빈) 추후 유저 벧리데이션으로 유저값 갖고옴
		// 테스트를 위한 셈플
		User sampleUser = User.builder()
			.email("useremail@naver.com")
			.password("xzcjzkxcjxz1212")
			.nickname("닉네임11111")
			.build();

		Post likePost = findPostByPostId(postId);
		postAdditionalRepository.save(PostAdditional.builder()
			.reactionPost(likePost)
			.reactionPostUser(sampleUser)
			.build());
	}

	public Post findPostByPostId(Long postId) {

		return postRepository.findById(postId)
			.orElseThrow(() -> new CustomException(NOT_FOUND_POST));
	}

	public void updatePostViewCount(Long postId) {
		Post updatePost = findPostByPostId(postId);
		updatePost.updateViewCount();
	}

}
