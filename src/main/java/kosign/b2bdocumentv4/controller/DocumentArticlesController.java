package kosign.b2bdocumentv4.controller;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import kosign.b2bdocumentv4.entity.doc_articles.DocumentArticles;
import kosign.b2bdocumentv4.payload.BaseResponse;
import kosign.b2bdocumentv4.payload.document_articles.DocumentArticlesRequest;
import kosign.b2bdocumentv4.payload.login.response.ApiResponse;
import kosign.b2bdocumentv4.service.doc_articles.DocumentArticlesService;
import kosign.b2bdocumentv4.service.doc_articles.DocumentArticlesServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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

    @GetMapping("/articlesLis")
    public BaseResponse getArticlesLists(@RequestParam Long id){
        return service.listAllArticles(id);
    }


    public BaseResponse insertArticlesList(@RequestParam Long tag_id, String title,String content_body,Long user_id,String file_article_id,String dep_id ){
        return null;
    }


}
