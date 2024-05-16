package kosign.b2bdocumentv4.mapper;


import kosign.b2bdocumentv4.entity.doc_tags.DocumentTag;
import kosign.b2bdocumentv4.entity.doc_users.DocumentUsers;
import kosign.b2bdocumentv4.payload.doc_tags.DocTagRequest;
import kosign.b2bdocumentv4.payload.doc_tags.DocTagUpdateRequest;
import kosign.b2bdocumentv4.payload.doc_tags.DocTagUpdateResponse;
import kosign.b2bdocumentv4.payload.doc_users.DocUserUpdateRequest;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import javax.print.Doc;

@Mapper(componentModel = "spring")
public interface DocumentTagMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    DocTagUpdateResponse entityToResponse(DocumentTag entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    DocumentTag updateDocTag (@MappingTarget DocumentTag entity, DocTagUpdateRequest docTagRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    DocumentTag requestToEntity(DocTagRequest docTagRequest);

    //
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    DocumentTag requestToEntity(DocTagUpdateResponse response);


}
