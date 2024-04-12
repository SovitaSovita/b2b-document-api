package kosign.b2bdocumentv4.authentication.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import kosign.b2bdocumentv4.authentication.service.docUsers.DocUserServiceImpl;
import kosign.b2bdocumentv4.payload.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/user")
@CrossOrigin
@RequiredArgsConstructor
public class UserController {

    private final DocUserServiceImpl docUserService;

    @GetMapping("/listAll")
    public BaseResponse listUser(@RequestParam Long dep_id) {
        System.out.println("Workkk");
        return docUserService.listUsers(dep_id);
    }


}
