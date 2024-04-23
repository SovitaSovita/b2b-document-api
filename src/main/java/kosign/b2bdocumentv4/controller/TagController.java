package kosign.b2bdocumentv4.controller;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import kosign.b2bdocumentv4.payload.BaseResponse;
import kosign.b2bdocumentv4.payload.doc_tags.DocTagDeleteRequest;
import kosign.b2bdocumentv4.payload.doc_tags.DocTagRequest;
import kosign.b2bdocumentv4.payload.doc_tags.DocTagUpdateRequest;
import kosign.b2bdocumentv4.service.doc_save_tag.SaveDocumentTagImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/saveTag")
@CrossOrigin
@RequiredArgsConstructor
public class TagController {

    private final SaveDocumentTagImpl service;

    @GetMapping("/getTagByDepId")
    public BaseResponse listTagByDep_Id(@RequestParam Long dept_id) {
        return service.getAllTagByDep_Id(dept_id);
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



}
