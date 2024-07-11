package kosign.b2bdocumentv4.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import kosign.b2bdocumentv4.payload.BaseResponse;
import kosign.b2bdocumentv4.service.doc_search.DocSearchServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/articles")
@CrossOrigin
@RequiredArgsConstructor
public class SearchController {
    @Autowired
    private final DocSearchServiceImpl service;


    @GetMapping("/doc_search_r01")
    public BaseResponse searchDoc(String title) {
        return service.searchDoc(title);
    }
    @GetMapping("/doc_search_r02")
    public String searchAllCase() {
        return "Work.";
    }

    @GetMapping("/doc_search_all")
    public BaseResponse searchAll(String srch_wd , int dept_id) {
        return service.searchAll(srch_wd,dept_id);
    }

}
