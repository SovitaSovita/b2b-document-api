package kosign.b2bdocumentv4.service.doc_Department;

import kosign.b2bdocumentv4.entity.doc_department.DocDepartment;
import kosign.b2bdocumentv4.entity.doc_department.DocumentDepartmentRepository;
import kosign.b2bdocumentv4.payload.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListDepartmentServiceImpl implements ListDepartmentService {
    private final DocumentDepartmentRepository repository;
    private final ModelMapper modelMapper;

    @Override
    public BaseResponse ListDepartment(){
        List<DocDepartment> getAllDepartment = repository.findAll();
        return BaseResponse.builder()
                .code("200")
                .message("Fetch Data successfully")
                .isError(false)
                .rec(getAllDepartment)
                .build();
    }


}
