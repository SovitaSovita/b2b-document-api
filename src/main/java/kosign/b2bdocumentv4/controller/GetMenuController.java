package kosign.b2bdocumentv4.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import kosign.b2bdocumentv4.entity.doc_users.DocumentUsers;
import kosign.b2bdocumentv4.payload.BaseResponse;
import kosign.b2bdocumentv4.service.doc_articles.DocumentArticlesServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public BaseResponse listMenuByDept_id() {
        DocumentUsers currentUser = (DocumentUsers) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return service.getMenuByUser(currentUser.getDept_id(), currentUser.getUsername());
    }





}
