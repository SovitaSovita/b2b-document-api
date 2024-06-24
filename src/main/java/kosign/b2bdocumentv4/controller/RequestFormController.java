package kosign.b2bdocumentv4.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import kosign.b2bdocumentv4.entity.doc_request.GetByUserRequest;
import kosign.b2bdocumentv4.entity.doc_request.RequestForm;
import kosign.b2bdocumentv4.payload.BaseResponse;
import kosign.b2bdocumentv4.service.doc_request.RequestFormServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
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
    @PostMapping("/getBy")
    public BaseResponse getBy(@RequestBody GetByUserRequest request){
        if (StringUtils.isEmpty(request.getReqStatus())) {
            request.setReqStatus(null);
        }
        return BaseResponse.builder()
                .code("200")
                .message("success")
                .isError(false)
                .rec(requestFormService.getByUserId(request))
                .build();
    }

    @PutMapping("updateRequest")
    public BaseResponse updateRequest(@RequestParam Long reqId){
        return BaseResponse.builder()
                .code("200")
                .message("success")
                .isError(false)
                .rec(requestFormService.updateRequestById(reqId))
                .build();
    }
}
