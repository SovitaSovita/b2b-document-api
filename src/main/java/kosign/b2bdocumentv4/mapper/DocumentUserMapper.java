package kosign.b2bdocumentv4.mapper;

import kosign.b2bdocumentv4.entity.doc_tags.DocumentTag;
import kosign.b2bdocumentv4.entity.doc_users.DocumentUsers;
import kosign.b2bdocumentv4.payload.doc_tags.DocTagUpdateRequest;
import kosign.b2bdocumentv4.payload.doc_users.DocUserResponse;
import kosign.b2bdocumentv4.payload.doc_users.DocUserUpdateRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface DocumentUserMapper {

//    @Mapping(target = "id", source = "id")
//    @Mapping(target = "image", source = "image")
//    @Mapping(target = "dept_id", source = "dept_id")
//    @Mapping(target = "username", source = "username")
//    @Mapping(target = "role", source = "role")
//    @Mapping(target = "status", source = "status")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    DocUserResponse toResponse(DocumentUsers entity);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    DocumentUsers responseToEntity(DocUserResponse response);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    DocumentUsers update(@MappingTarget DocumentUsers entity , DocUserUpdateRequest updateRequest );



}
