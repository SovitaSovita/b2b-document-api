package kosign.b2bdocumentv4.controller;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/articles")
@CrossOrigin
@RequiredArgsConstructor
public class DeleteTagController {

    @PostMapping("/doc_tag_delete")
    public String deleteTagController() {
        return "delete tag success";
    }
}
