package kosign.b2bdocumentv4.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@CrossOrigin
@SecurityRequirement(name = "bearerAuth")
@AllArgsConstructor
public class AdminController {

    @GetMapping("/welcome")
    public String adminWel() {
        return "Welcome Admin";
    }

//    private final AdminService adminService;


//    @GetMapping("/getAllUser")
//    public ApiResponse<?> getAllUser(@RequestParam(name = "page", defaultValue = "0") int page,
//                                     @RequestParam(name = "size", defaultValue = "5") int size) {
//        List<User> userList = adminService.getAllUser(page, size);
//        return ApiResponse.builder()
//                .payload(userList)
//                .date(LocalDateTime.now())
//                .status(200)
//                .message("successfully fetch users")
//                .build();
//    }

}
