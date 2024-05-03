package kosign.b2bdocumentv4.controller;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import kosign.b2bdocumentv4.entity.doc_users.DocumentUsers;
import kosign.b2bdocumentv4.payload.BaseResponse;
import kosign.b2bdocumentv4.payload.doc_tags.DocTagDeleteRequest;
import kosign.b2bdocumentv4.payload.doc_tags.DocTagRequest;
import kosign.b2bdocumentv4.payload.doc_tags.DocTagUpdateRequest;
import kosign.b2bdocumentv4.service.doc_articles.DocumentArticlesServiceImpl;
import kosign.b2bdocumentv4.service.doc_save_tag.SaveDocumentTagImpl;
import kosign.b2bdocumentv4.service.doc_users.DocUserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/DocTag")
@CrossOrigin
@RequiredArgsConstructor
public class TagController {

    private final SaveDocumentTagImpl service;
    private final DocUserServiceImpl docUserService;
    private final DocumentArticlesServiceImpl documentArticlesService;

    // Tag and Article
    @GetMapping("/getTagByDepId")
    public BaseResponse listTagByDep_Id(@RequestParam Long dept_id) {
        return service.getAllTagByDept_Id(dept_id);
    }

    @PostMapping("/saveTagTitle")
    public BaseResponse saveTagController(@RequestBody DocTagRequest docTagRequest) {
        return service.saveTag(docTagRequest);
    }

    @PostMapping("/updateTag")
    public BaseResponse updateTagController(@RequestBody DocTagUpdateRequest docTagUpdateRequest) {
        return service.updateTag(docTagUpdateRequest);
    }

    @PostMapping("/deleteTag")
    public BaseResponse deleteTagById(@RequestBody DocTagDeleteRequest docTagDeleteRequest) {
        return service.deleteTag(docTagDeleteRequest);
    }

    // New API list tag and article
    @GetMapping("/documentTag")
    public BaseResponse doc_menu_home_r01(@RequestParam Long dept_id) {

        return service.getTag(dept_id);
    }

    // Test
//    @GetMapping("/test")
//    public BaseResponse test() {
//        return service.GetDocumentArticle();
//    }

    @GetMapping("/listTagAndAtricle")
    public BaseResponse test1(@RequestParam Long dept_id) {
        return service.listTagAndArticle(dept_id);
    }



}
