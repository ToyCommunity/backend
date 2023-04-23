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

import com.fasterxml.jackson.annotation.JsonBackReference;

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

	@JsonBackReference
	@JoinColumn(name = "user_id")
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private User likeUser;

	@JsonBackReference
	@JoinColumn(name = "reply_id")
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Reply reactionReply;

	@Builder
	public ReplyAdditional(User likeUser, Reply reactionReply) {
		this.reactionReply = reactionReply;
		this.likeUser = likeUser;
	}

}
