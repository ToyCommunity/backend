package toy.com.post.domain;

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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toy.com.common.entity.BaseTimeEntity;
import toy.com.user.domain.User;

@Entity
@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
@Table(name = "reply")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reply extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JoinColumn(name = "post_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private Post post;

	@JoinColumn(name = "reply_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private User replyWriter;

	@Lob
	private String content;

	private int replyLikes;

	@JoinColumn(name = "reaction_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private User reactonUser;

	public boolean isReactionReply(Long userId) {
		return reactonUser.getId().equals(userId);
	}

}
