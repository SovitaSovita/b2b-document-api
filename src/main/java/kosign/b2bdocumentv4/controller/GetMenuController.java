package kosign.b2bdocumentv4.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import kosign.b2bdocumentv4.entity.doc_users.DocumentUsers;
import kosign.b2bdocumentv4.entity.doc_users.DocumentUsersRepository;
import kosign.b2bdocumentv4.enums.Role;
import kosign.b2bdocumentv4.exception.NotFoundExceptionClass;
import kosign.b2bdocumentv4.jwt.JwtTokenUtils;
import kosign.b2bdocumentv4.payload.BaseResponse;
import kosign.b2bdocumentv4.service.doc_articles.DocumentArticlesServiceImpl;
import kosign.b2bdocumentv4.service.doc_users.DocUserServiceImpl;
import kosign.b2bdocumentv4.utils.ApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;


@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/menu")
@CrossOrigin
@RequiredArgsConstructor
public class GetMenuController {

    private final DocumentArticlesServiceImpl service;
    private final DocUserServiceImpl docUserService;

    // articles list by dept_id
    @PostMapping("/doc_menu_home_r01")
    public BaseResponse listMenuByDept_id(HttpServletRequest request) {
        DocumentUsers currentUser = docUserService.getCurrentUser(request);
        return service.getMenuByUser(currentUser.getDept_id(), currentUser.getUsername());
    }





}
