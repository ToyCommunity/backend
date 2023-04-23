package toy.com.post.domain;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import toy.com.common.entity.BaseTimeEntity;
import toy.com.user.domain.User;

@Entity
@Getter
@ToString(exclude = "post")
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

	@JoinColumn(name = "post_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private Post post;

	@JoinColumn(name = "reply_write_user_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private User replyWriter;

	@JoinColumn(name = "reply_reaction_user_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private User reactionUser;

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
	public Reply(String content, int replyLikes, User replyWriter, User reactionUser) {
		this.content = content;
		this.replyLikes = replyLikes;
		this.replyWriter = replyWriter;
		this.reactionUser = reactionUser;
	}

	public boolean isReactionReply(Long userId) {
		if (Objects.isNull(userId)) return false;
		return reactionUser.getId().equals(userId);
	}

}
