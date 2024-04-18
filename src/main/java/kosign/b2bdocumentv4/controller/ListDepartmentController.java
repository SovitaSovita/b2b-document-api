package kosign.b2bdocumentv4.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import kosign.b2bdocumentv4.payload.BaseResponse;
import kosign.b2bdocumentv4.service.doc_Department.ListDepartmentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/department")
@CrossOrigin
@RequiredArgsConstructor
public class ListDepartmentController {
    private final ListDepartmentServiceImpl listDepartmentService;

    @GetMapping("/AllDepartment")
    public BaseResponse listDepartment(){
        return  listDepartmentService.ListDepartment();
    }
}
