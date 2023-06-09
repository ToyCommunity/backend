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

import org.hibernate.annotations.DynamicUpdate;

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
@ToString(exclude = {"likeUser", "reactionPost"})
@DynamicUpdate
@EqualsAndHashCode(of = "id", callSuper = false)
@Table(name = "post_additional")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostAdditional extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JoinColumn(name = "user_id")
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private User likeUser;

	@JoinColumn(name = "post_id")
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Post reactionPost;

	@Builder
	public PostAdditional(User reactionPostUser, Post reactionPost) {
		this.reactionPost = reactionPost;
		this.likeUser = reactionPostUser;
	}
}
