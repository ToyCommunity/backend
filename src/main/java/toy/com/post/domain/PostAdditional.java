package toy.com.post.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
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
@Table(name = "post_additional")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostAdditional extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "post_additional")
    private User reactionPostUser;

}
