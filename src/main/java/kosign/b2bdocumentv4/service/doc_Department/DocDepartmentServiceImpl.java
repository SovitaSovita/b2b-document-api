package kosign.b2bdocumentv4.service.doc_Department;

import kosign.b2bdocumentv4.entity.doc_department.DocDepartmentTag;
import kosign.b2bdocumentv4.entity.doc_department.DocumentDepartmentRepository;
import kosign.b2bdocumentv4.mapper.DocumentDepartmentMapper;
import kosign.b2bdocumentv4.payload.BaseResponse;
import kosign.b2bdocumentv4.payload.doc_documents.DocDeleteDepartmentRequest;
import kosign.b2bdocumentv4.payload.doc_documents.DocDepartmentRequest;
import kosign.b2bdocumentv4.payload.doc_documents.DocUpdateDocumentRequest;
import kosign.b2bdocumentv4.payload.doc_tags.DocTagRequest;
import kosign.b2bdocumentv4.utils.AuthHelper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocDepartmentServiceImpl implements DocDepartmentService {
    private final DocumentDepartmentRepository repository;
    private final ModelMapper modelMapper;
    private final DocumentDepartmentMapper documentDepartmentMapper;

    @Override
    public BaseResponse ListDepartment(){
        List<DocDepartmentTag> getAllDepartment = repository.findAll();
        System.out.println("REC"+getAllDepartment);
        return BaseResponse.builder()
                .code("200")
                .message("Fetch Data successfully")
                .isError(false)
                .rec(getAllDepartment)
                .build();
    }

    @Override
    public BaseResponse insertDepartment(DocDepartmentRequest documentDepartment) {
        DocDepartmentTag docDepartmentTag = documentDepartmentMapper.toEntity(documentDepartment);

        docDepartmentTag.setDep_status("1");
        //docDepartmentTag.setDep_id(documentDepartment.getDep_id());
        docDepartmentTag.setDep_name(documentDepartment.getDep_name());
        docDepartmentTag.setCreated_by(documentDepartment.getCreated_by());
/*        if(!documentDepartment.getDep_status().equals(("0")) && !documentDepartment.getDep_status().equals("1")){
            docDepartmentTag.setDep_status(d);

        }*/
        docDepartmentTag.setCreate_date(Timestamp.valueOf(LocalDateTime.now()));

        var DeptRec = repository.save(docDepartmentTag);
        return BaseResponse.builder()
                .rec(DeptRec)
                .code("201")
                .message("Created")
                .build();
    }

    @Override
    public BaseResponse updateDepartment(DocUpdateDocumentRequest docUpdateDocumentRequest) {

        DocDepartmentTag docDepartmentTag = documentDepartmentMapper.update(docUpdateDocumentRequest);
        docDepartmentTag.setDept_id(docDepartmentTag.getDept_id());
        docDepartmentTag.setDep_status(docUpdateDocumentRequest.getDep_status());
        docDepartmentTag.setDep_name(docUpdateDocumentRequest.getDep_name());
        docDepartmentTag.setModified_by(docUpdateDocumentRequest.getModified_by());
        docDepartmentTag.setModified_date(Timestamp.valueOf(LocalDateTime.now()));

        var DeptRec = repository.save(docDepartmentTag);
        return BaseResponse.builder()
                .rec(DeptRec)
                .code("200")
                .message("Updated")
                .build();
    }

    @Override
    public BaseResponse deleteDepartment(long dept_id) {
        repository.deleteById(dept_id);

        return BaseResponse
                .builder()
                .code("200")
                .message("Delete success!")
                .build();
    }


}
