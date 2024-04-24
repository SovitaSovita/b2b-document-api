package kosign.b2bdocumentv4.controller;


import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import kosign.b2bdocumentv4.payload.BaseResponse;

import kosign.b2bdocumentv4.payload.document_articles.DocumentArticlesRequest;
import kosign.b2bdocumentv4.service.doc_articles.DocumentArticlesServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/menu")
@CrossOrigin
@RequiredArgsConstructor
public class GetMenuController {

    private final DocumentArticlesServiceImpl service;

    // articles list by dept_id
    @PostMapping("/doc_menu_home_r01")
    public BaseResponse listMenuByDept_id(String dept_id) {
        return service.getMenuByDept_ID(dept_id);
    }

}
