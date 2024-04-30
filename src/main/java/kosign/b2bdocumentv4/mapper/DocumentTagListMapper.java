package kosign.b2bdocumentv4.mapper;

import kosign.b2bdocumentv4.entity.doc_tags.DocumentTag;
import kosign.b2bdocumentv4.payload.doc_tags.DocArticleResponse;
import kosign.b2bdocumentv4.payload.doc_tags.DocTagResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface DocumentTagListMapper {

    // Tag
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    // DocTagResponse toResponse(DocumentTag entity);
    DocTagResponse toRes(DocumentTag entity);

    // Article
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    DocArticleResponse toResponse(DocumentTag entity);

}
