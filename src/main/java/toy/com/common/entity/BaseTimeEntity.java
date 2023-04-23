package toy.com.common.entity;

import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

	@CreatedDate
	private LocalDateTime createdAt;

	@LastModifiedDate
	private LocalDateTime modifiedAt;

	@PrePersist
	public void onCreated() {
		this.createdAt = LocalDateTime.now(ZoneId.of("Asia/Seoul", ZoneId.SHORT_IDS));
		this.createdAt = LocalDateTime.now(ZoneId.of("Asia/Seoul", ZoneId.SHORT_IDS));
	}

	@PreUpdate
	public void onUpdate() {
		this.modifiedAt = LocalDateTime.now(ZoneId.of("Asia/Seoul", ZoneId.SHORT_IDS));
	}

}
