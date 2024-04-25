package kosign.b2bdocumentv4.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import kosign.b2bdocumentv4.entity.doc_users.DocumentUsers;
import kosign.b2bdocumentv4.payload.doc_users.DocUserProfileRequest;
import kosign.b2bdocumentv4.payload.doc_users.DocUserUpdateRequest;
import kosign.b2bdocumentv4.payload.login.response.ApiResponse;
import kosign.b2bdocumentv4.service.doc_users.DocUserServiceImpl;
import kosign.b2bdocumentv4.payload.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
//@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/user")
@CrossOrigin
@RequiredArgsConstructor
public class UserController {

    private final DocUserServiceImpl docUserService;
    private final ModelMapper  modelMapper;

    @GetMapping("/list-by-department")
    public BaseResponse listUser(@RequestParam Long dept_id) {
        return docUserService.listUsers(dept_id);
    }

    @PutMapping("/update")
    public BaseResponse updateUser(@RequestBody DocUserUpdateRequest updateRequest){
        return docUserService.updateDocumentUser(updateRequest);
    }

    @PutMapping("/update/profile")
    public BaseResponse updateUserProfile(@RequestBody DocUserProfileRequest request){
        return docUserService.updateDocumentUser(modelMapper.map(request,DocUserUpdateRequest.class));
    }

    @DeleteMapping("/deactivate")
    public BaseResponse deactivateUser(@RequestParam Long userId){
        return docUserService.deactivateUser(userId);
    }

}
