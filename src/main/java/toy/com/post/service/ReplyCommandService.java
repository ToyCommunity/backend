package toy.com.post.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import toy.com.exception.CustomException;
import toy.com.exception.code.ErrorCode;
import toy.com.post.domain.Post;
import toy.com.post.domain.Reply;
import toy.com.post.domain.ReplyAdditional;
import toy.com.post.domain.ReplyStatus;
import toy.com.post.dto.request.ReplyCreateRequest;
import toy.com.post.dto.request.ReplyModifyRequest;
import toy.com.post.repository.ReplyAdditionalRepository;
import toy.com.post.repository.ReplyRepository;
import toy.com.user.domain.User;

@Service
@Transactional
@RequiredArgsConstructor
public class ReplyCommandService {

	private final PostQueryService postQueryService;
	private final ReplyRepository replyRepository;
	private final ReplyAdditionalRepository replyAdditionalRepository;

	public void createReply(ReplyCreateRequest request) {

		// TODO(박종빈) 추후 유저 벧리데이션으로 유저값 갖고옴
		// 테스트를 위한 셈플
		User sampleUser = User.builder()
			.email("useremail@naver.com")
			.password("xzcjzkxcjxz1212")
			.nickname("댓글유저222")
			.build();

		Post post = postQueryService.findPostByPostId(request.postId());

		replyRepository.save(Reply.builder()
			.replyWriter(sampleUser)
			.post(post)
			.replyStatus(ReplyStatus.NORMAL)
			.content(request.content())
			.build());
	}

	public void modifyReply(ReplyModifyRequest request) {

		// TODO(박종빈) 추후 user validation 추가 필요
		Reply reply = findReplyByReplyId(request.replyId());
		reply.modifyReply(request);
	}

	public void deleteReply(Long replyId) {

		// TODO(박종빈) 추후 user validation 추가 필요
		Reply reply = findReplyByReplyId(replyId);
		reply.deleteReply();
	}

	public void likeReply(Long replyId) {

		// TODO(박종빈) 추후 유저 벧리데이션으로 유저값 갖고옴
		// 테스트를 위한 셈플
		User reactionReplyUser = User.builder()
			.email("useremail@naver.com")
			.password("xzcjzkxcjxz1212")
			.nickname("댓글유저222")
			.build();

		Reply reply = findReplyByReplyId(replyId);

		reply.updateReplyLike();
		replyRepository.save(reply);

		replyAdditionalRepository.save(ReplyAdditional.builder()
			.reactionReply(reply)
			.likeUser(reactionReplyUser)
			.build());
	}

	public Reply findReplyByReplyId(Long replyId) {
		return replyRepository.findById(replyId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_REPLY));
	}

}
