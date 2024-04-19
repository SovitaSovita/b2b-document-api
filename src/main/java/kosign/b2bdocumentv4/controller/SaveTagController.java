package kosign.b2bdocumentv4.controller;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import kosign.b2bdocumentv4.payload.BaseResponse;
import kosign.b2bdocumentv4.payload.doc_tags.DocTagRequest;
import kosign.b2bdocumentv4.service.doc_save_tag.SaveDocumentTagImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/saveTag")
@CrossOrigin
@RequiredArgsConstructor
public class SaveTagController {

    private final SaveDocumentTagImpl server;

    @PostMapping("/saveTagTitle")
    public BaseResponse saveTagController(@RequestBody DocTagRequest docTagRequest) {

        return server.saveTag(docTagRequest);


    }

}
