package toy.com.post.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class PostStatusConverter implements AttributeConverter<PostStatus, String> {

    @Override
    public String convertToDatabaseColumn(PostStatus attribute) {
        return attribute.getCode();
    }

    @Override
    public PostStatus convertToEntityAttribute(String dbData) {
        return PostStatus.ofCode(dbData);
    }
}
