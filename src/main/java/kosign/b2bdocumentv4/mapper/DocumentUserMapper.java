package kosign.b2bdocumentv4.mapper;

import kosign.b2bdocumentv4.entity.doc_tags.DocumentTag;
import kosign.b2bdocumentv4.entity.doc_users.DocumentUsers;
import kosign.b2bdocumentv4.payload.doc_tags.DocTagUpdateRequest;
import kosign.b2bdocumentv4.payload.doc_users.DocUserResponse;
import kosign.b2bdocumentv4.payload.doc_users.DocUserUpdateRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface DocumentUserMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    DocUserResponse toResponse(DocumentUsers entity);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    DocumentUsers toEntity(DocUserResponse response);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    DocumentUsers update(@MappingTarget DocumentUsers entity , DocUserUpdateRequest updateRequest );
}
