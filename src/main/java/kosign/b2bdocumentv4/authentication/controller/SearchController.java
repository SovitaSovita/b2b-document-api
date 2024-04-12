package kosign.b2bdocumentv4.authentication.controller;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/docs")
@CrossOrigin

@AllArgsConstructor
public class SearchController {

    @GetMapping
    public String searchDos() {
        System.out.println("Hi");
        return "welcomes";
    }
}
