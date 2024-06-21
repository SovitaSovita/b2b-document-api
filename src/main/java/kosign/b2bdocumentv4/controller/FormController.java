package kosign.b2bdocumentv4.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import kosign.b2bdocumentv4.entity.doc_form.Form;
import kosign.b2bdocumentv4.payload.BaseResponse;
import kosign.b2bdocumentv4.service.doc_form.FormServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/form/")
@CrossOrigin
@RequiredArgsConstructor
public class FormController {
    private final FormServiceImpl formService;

    @PostMapping("create")
    public BaseResponse insertForm(@RequestBody Form request) {
        return BaseResponse.builder()
                .code("200")
                .message("success")
                .isError(false)
                .rec(formService.createForm(request))
                .build();
    }

    @PutMapping("modify")
    public BaseResponse updateForm(@RequestParam Long id, @RequestParam String content) {
        return BaseResponse.builder()
                .code("200")
                .message("updated success")
                .isError(false)
                .rec(formService.updateForm(id, content))
                .build();
    }

    @DeleteMapping("delete")
    public BaseResponse deleteForm(@RequestParam Long id) {
        formService.removeForm(id);
        return BaseResponse.builder()
                .code("200")
                .message("deleted success")
                .isError(false)
                .build();
    }

    @GetMapping("getBy")
    public BaseResponse getByStatusOrUser(@RequestParam int status, @RequestParam(required = false) String username) {
        return BaseResponse.builder()
                .code("200")
                .message("success")
                .isError(false)
                .rec(formService.getAll(status, username))
                .build();
    }
}
