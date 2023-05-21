package toy.com.post.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import toy.com.common.entity.BaseTimeEntity;
import toy.com.post.dto.request.ReplyModifyRequest;
import toy.com.user.domain.User;

@Entity
@Getter
@DynamicUpdate
@DynamicInsert
@ToString(exclude = {"post", "replyWriter", "replyAdditionalList"})
@EqualsAndHashCode(of = "id", callSuper = false)
@Table(name = "reply")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reply extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Lob
	private String content;

	private int replyLikes;

	@Convert(converter = ReplyStatusConverter.class)
	private ReplyStatus replyStatus;

	@JsonBackReference
	@JoinColumn(name = "post_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private Post post;

	@JoinColumn(name = "reply_write_user_id")
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private User replyWriter;

	@JsonManagedReference
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "reactionReply")
	private List<ReplyAdditional> replyAdditionalList = new ArrayList<>();

	public void applyPost(Post post) {
		if (this.post != null) {
			this.post.getReplies().remove(this);
		}
		this.post = post;
		if (!post.getReplies().contains(this)) {
			post.getReplies().add(this);
		}
	}

	@Builder
	public Reply(String content, int replyLikes, User replyWriter, ReplyStatus replyStatus, Post post) {
		this.content = content;
		this.replyStatus = replyStatus;
		this.replyLikes = replyLikes;
		this.replyWriter = replyWriter;
		this.post = post;
	}

	public boolean isReplyHasMyReaction(User reactionUser) {
		if (Objects.isNull(reactionUser))
			return false;
		return replyAdditionalList.contains(reactionUser);
	}

	public void modifyReply(ReplyModifyRequest request) {
		this.content = request.content();
	}

	public void deleteReply() {
		this.replyStatus = ReplyStatus.DELETED;
	}

	public void plusReplyLikeCount() {
		this.replyLikes += 1;
	}

	public void minusReplyLikeCount() {
		this.replyLikes -= 1;
	}

}
