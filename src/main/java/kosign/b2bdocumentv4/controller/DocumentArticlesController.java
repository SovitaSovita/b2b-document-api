package kosign.b2bdocumentv4.controller;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import kosign.b2bdocumentv4.payload.BaseResponse;
import kosign.b2bdocumentv4.payload.document_articles.DocumentArticlesRequest;
import kosign.b2bdocumentv4.service.doc_articles.DocumentArticlesServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/articles")
@CrossOrigin
@RequiredArgsConstructor
public class DocumentArticlesController {

    private final DocumentArticlesServiceImpl service;

    @PostMapping("/list")
    public BaseResponse getArticlesList(@RequestBody DocumentArticlesRequest documentArticlesRequest){
        return service.listArticles(documentArticlesRequest.getDep_id());
    }

}
