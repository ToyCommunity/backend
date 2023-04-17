package toy.com.post.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ReplyStatusConverter implements AttributeConverter<ReplyStatus, String> {

	@Override
	public String convertToDatabaseColumn(ReplyStatus attribute) {
		return attribute.getCode();
	}

	@Override
	public ReplyStatus convertToEntityAttribute(String dbData) {
		return ReplyStatus.ofCode(dbData);
	}
}
