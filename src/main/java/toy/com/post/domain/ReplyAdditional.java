package toy.com.post.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import toy.com.common.entity.BaseTimeEntity;

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



}
