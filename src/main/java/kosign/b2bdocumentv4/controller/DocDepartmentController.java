package kosign.b2bdocumentv4.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import kosign.b2bdocumentv4.entity.doc_department.DocDepartmentTag;
import kosign.b2bdocumentv4.payload.BaseResponse;
import kosign.b2bdocumentv4.payload.doc_documents.DocDeleteDepartmentRequest;
import kosign.b2bdocumentv4.payload.doc_documents.DocDepartmentRequest;
import kosign.b2bdocumentv4.payload.doc_documents.DocUpdateDocumentRepose;
import kosign.b2bdocumentv4.payload.doc_documents.DocUpdateDocumentRequest;
import kosign.b2bdocumentv4.service.doc_Department.DocDepartmentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/department")
@CrossOrigin
@RequiredArgsConstructor
public class DocDepartmentController {
    private final DocDepartmentServiceImpl listDepartmentService;

    @GetMapping("/AllDepartment")
    public BaseResponse listDepartment(){
        return  listDepartmentService.ListDepartment();
    }

    @PostMapping("/insertDepartment")
    public BaseResponse insertDepartment(@RequestBody DocDepartmentRequest departmentRequest){
        return listDepartmentService.insertDepartment(departmentRequest);
    }
    @PostMapping("/UpdateDepartment")
    public BaseResponse updateDepartment(@RequestBody DocUpdateDocumentRequest docUpdateDocumentRequest){
        return listDepartmentService.updateDepartment(docUpdateDocumentRequest);
    }
    @PostMapping("/deleteDepartment")
    public BaseResponse deleteDepartment(@RequestParam long dept_id){
        return listDepartmentService.deleteDepartment(dept_id);
    }
}
