package kosign.b2bdocumentv4.mapper;

import kosign.b2bdocumentv4.entity.doc_department.DocDepartmentTag;
import kosign.b2bdocumentv4.payload.doc_documents.DocDepartmentRequest;
import kosign.b2bdocumentv4.payload.doc_documents.DocUpdateDocumentRequest;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface DocumentDepartmentMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    DocDepartmentTag requestToEntity(DocDepartmentRequest departmentRequest);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    DocDepartmentTag requestToEntityUpdate(DocUpdateDocumentRequest docUpdateDocumentRequest);
}
