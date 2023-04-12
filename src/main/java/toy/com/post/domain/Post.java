package toy.com.post.domain;

import java.util.ArrayList;
import java.util.List;

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
import toy.com.common.entity.BaseTimeEntity;
import toy.com.user.domain.User;

@Entity
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

	@JoinColumn(name = "user_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private User postWriter;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "post", orphanRemoval = true)
	private List<Reply> replies = new ArrayList<>();

	@JoinColumn(name = "reaction_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private User reactonUser;

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

	public boolean isReactionPost(Long userId) {
		return reactonUser.getId().equals(userId);
	}

}
