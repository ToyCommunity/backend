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

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import toy.com.common.entity.BaseTimeEntity;
import toy.com.user.domain.User;

@Entity
@ToString
@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
@Table(name = "post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String postTitle;

	@Lob
	private String postContent;

	@Convert(converter = PostStatusConverter.class)
	private PostStatus postStatus;

	private int likeCounts;

	private int viewCounts;

	private PostCategory postCategory;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "post")
	private List<Reply> replies = new ArrayList<>();

	@JoinColumn(name = "post_writer_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private User postWriter;

	@JoinColumn(name = "reaction_user_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private User reactionUser;

	public void addReplies(Reply reply) {
		this.replies.add(reply);
		if (reply.getPost() != this) {
			reply.applyPost(this);
			}
	}

	@Builder
	public Post(String postTitle,
		PostStatus postStatus,
		String postContent,
		User postWriter,
		int likeCounts,
		int viewCounts,
		PostCategory postCategory,
		Reply reply,
		User reactionUser) {
		this.postTitle = postTitle;
		this.postStatus = postStatus;
		this.postContent = postContent;
		this.postWriter = postWriter;
		this.likeCounts = likeCounts;
		this.viewCounts = viewCounts;
		this.postCategory = postCategory;
		this.reactionUser = reactionUser;
	}

	public boolean isReactionPost(Long userId) {
		if (Objects.isNull(userId)) return false;
		return reactionUser.getId().equals(userId);
	}

}
