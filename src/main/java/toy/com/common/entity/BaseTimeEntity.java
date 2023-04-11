package toy.com.common.entity;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.context.event.EventListener;

@Getter
@MappedSuperclass
public abstract class BaseTimeEntity {

}
