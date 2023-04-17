package toy.com.post.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@ToString
@EqualsAndHashCode(of = "id", callSuper = false)
@Table(name = "reply_additional")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReplyAdditional extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JoinColumn(name = "user_id")
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private User reactionLikeReplyUser;

	@JoinColumn(name = "reply_id")
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Reply reactionReply;

	@Builder
	public ReplyAdditional(User reactionLikeReplyUser, Reply reactionReply) {
		this.reactionReply = reactionReply;
		this.reactionLikeReplyUser = reactionLikeReplyUser;
	}

}
