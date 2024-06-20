package kosign.b2bdocumentv4.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import kosign.b2bdocumentv4.entity.doc_request.RequestForm;
import kosign.b2bdocumentv4.payload.BaseResponse;
import kosign.b2bdocumentv4.service.doc_request.RequestFormServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/requestform")
@CrossOrigin
@RequiredArgsConstructor
public class RequestFormController {
    private final RequestFormServiceImpl requestFormService;
    @PostMapping("/sendRequest")
    public BaseResponse sendFormRequest(@RequestBody RequestForm requestForm){
        return BaseResponse.builder()
                .code("200")
                .message("success")
                .isError(false)
                .rec(requestFormService.sendFormRequest(requestForm))
                .build();
    }
    @GetMapping("/getByUserId")
    public BaseResponse getBy(@RequestParam String userId, @RequestParam String reqStatus){
        return BaseResponse.builder()
                .code("200")
                .message("success")
                .isError(false)
                .rec(requestFormService.getByUserId(userId, reqStatus))
                .build();
    }
}
