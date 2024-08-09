package kosign.b2bdocumentv4.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import kosign.b2bdocumentv4.dto.RequestFormDto;
import kosign.b2bdocumentv4.entity.doc_request.GetByUserRequest;
import kosign.b2bdocumentv4.entity.doc_request.RequestForm;
import kosign.b2bdocumentv4.payload.BaseResponse;
import kosign.b2bdocumentv4.service.doc_request.RequestFormServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/requestform")
@CrossOrigin
@RequiredArgsConstructor
public class RequestFormController {
    private final RequestFormServiceImpl requestFormService;
    @PostMapping("/sendRequest")
    public BaseResponse sendFormRequest(@RequestBody RequestFormDto requestForm) throws JsonProcessingException {
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

    @PostMapping("/getByRequestId")
    public BaseResponse getByRequestId(@RequestParam Long requestId){
        return BaseResponse.builder()
                .code("200")
                .message("success")
                .isError(false)
                .rec(requestFormService.getRequestById(requestId))
                .build();
    }


    @PutMapping("/updateRequest")
    public BaseResponse updateRequest(@RequestParam Long reqId){
        return BaseResponse.builder()
                .code("200")
                .message("success")
                .isError(false)
                .rec(requestFormService.updateRequestById(reqId))
                .build();
    }

    @GetMapping("/getDetail")
    public BaseResponse getBy(@RequestParam Long id){
        return BaseResponse.builder()
                .code("200")
                .message("success")
                .isError(false)
                .rec(requestFormService.getDetailById(id))
                .build();
    }
    @DeleteMapping("/delete")
    public BaseResponse deleteById(@RequestParam Long id){
        requestFormService.deleteById(id);
        return BaseResponse.builder()
                .code("200")
                .message("success")
                .isError(false)
                .build();
    }

    //check a Request that Already "Approved" by all recipient
    @GetMapping("/approvedById")
    public BaseResponse getApproved(@RequestParam Long requestId){
        List<RequestForm> data  = requestFormService.getApprovedById(requestId);
        if(data == null){
            return BaseResponse.builder()
                    .code("201")
                    .message("Your Request is Pending.")
                    .isError(false)
                    .rec(null)
                    .build();
        }
        return BaseResponse.builder()
                .code("200")
                .message("Request successfully Approved.")
                .isError(false)
                .rec(data)
                .build();
    }

    //list all Request that Already "Approved" by all recipient
    @GetMapping("/listApproved")
    public BaseResponse getListApproved(@RequestParam String userId, @RequestParam String company){
        return BaseResponse.builder()
                .code("200")
                .message("successfully.")
                .isError(false)
                .rec(requestFormService.getListApproved(userId, company))
                .build();
    }
    @GetMapping("/listRequest")
    public BaseResponse getListRequest(@RequestParam String userId, @RequestParam String company){
        return BaseResponse.builder()
                .code("200")
                .message("successfully.")
                .isError(false)
                .rec(requestFormService.getListRequest(userId, company))
                .build();
    }
    @PostMapping("/approval")
    public BaseResponse approveRequest(@RequestParam Long id){
        return BaseResponse.builder()
                .code("200")
                .message("Request have been Approved.")
                .isError(false)
                .rec(requestFormService.approveRequest(id))
                .build();
    }
}
