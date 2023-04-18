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

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import toy.com.common.entity.BaseTimeEntity;
import toy.com.post.dto.request.PostModifyRequest;
import toy.com.user.domain.User;

@Entity
@ToString
@Getter
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(of = "id", callSuper = false)
@Table(name = "post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Lob
	private String postContent;

	@Convert(converter = PostStatusConverter.class)
	private PostStatus postStatus;

	private String postTitle;

	private int likeCounts;

	private int viewCounts;

	private PostCategory postCategory;

	@JsonManagedReference
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "post")
	private List<Reply> replies = new ArrayList<>();

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "reactionPost")
	private List<PostAdditional> postAdditionalList = new ArrayList<>();

	@JoinColumn(name = "post_writer_id")
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private User postWriter;

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
		PostCategory postCategory) {
		this.postTitle = postTitle;
		this.postStatus = postStatus;
		this.postContent = postContent;
		this.postWriter = postWriter;
		this.likeCounts = likeCounts;
		this.viewCounts = viewCounts;
		this.postCategory = postCategory;
	}

	public boolean isPostHasMyReaction(User reactionUser) {
		if (Objects.isNull(reactionUser)) {
			return false;
		}
		return postAdditionalList.contains(reactionUser);
	}

	public void modifyPost(PostModifyRequest postModifyRequest) {
		this.postTitle = postModifyRequest.title();
		this.postContent = postModifyRequest.content();
		this.postCategory = postModifyRequest.category();
	}

	public void updateViewCount() {
		this.viewCounts += 1;
	}

	public void updatePostLikeCount() {
		this.likeCounts += 1;
	}

	public void deletePost() {
		this.postStatus = PostStatus.DELETED;
	}
}
