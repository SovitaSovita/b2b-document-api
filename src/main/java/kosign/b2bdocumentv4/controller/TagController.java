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
import org.springframework.http.ResponseEntity;
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

    // Insert Tag (Use)
    @PostMapping("/saveTagTitle")
    public BaseResponse saveTagController(@RequestBody DocTagRequest docTagRequest) {
        return service.saveTag(docTagRequest);
    }

    // Update Tag (Use)
    @PostMapping("/updateTag")
    public BaseResponse updateTagController(@RequestBody DocTagUpdateRequest docTagUpdateRequest) {
        return service.updateTag(docTagUpdateRequest);
    }

    // Delete Tag (Use)
    @PostMapping("/deleteTag")
    public ResponseEntity<?> deleteTagById(@RequestParam Long tag_id) {
        return service.deleteTag(tag_id);
    }

    // New API list tag and Title (Use)
    @GetMapping("/documentTag")
    public BaseResponse doc_menu_home_r01(@RequestParam Long dept_id, String username) {
        return service.getTag(dept_id, username);
    }

    // New API list Tag and Title (Not use)
    @GetMapping("/listTagAndAtricle")
    public BaseResponse test1(@RequestParam Long status,
                              @RequestParam(required = false) Long dept_id,
                              @RequestParam(required = false) String username, HttpServletRequest request) {
        docUserService.getCurrentUser(request);
        return service.listTagAndArticle(dept_id , status, username);
    }



}
