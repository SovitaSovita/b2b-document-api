package kosign.b2bdocumentv4.mapper;

import kosign.b2bdocumentv4.entity.doc_users.DocumentUsers;
import kosign.b2bdocumentv4.payload.doc_users.DocUserResponse;
import kosign.b2bdocumentv4.payload.doc_users.DocUserUpdateRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface DocumentUserMapper {
    DocUserResponse entityToResponse(DocumentUsers entity);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    DocumentUsers responseToEntity(DocUserResponse response);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    DocumentUsers updateDocUser(@MappingTarget DocumentUsers entity , DocUserUpdateRequest updateRequest );
}