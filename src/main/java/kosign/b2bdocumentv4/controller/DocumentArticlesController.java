package kosign.b2bdocumentv4.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import kosign.b2bdocumentv4.entity.doc_articles.DocumentArticles;
import kosign.b2bdocumentv4.payload.BaseResponse;
import kosign.b2bdocumentv4.payload.document_articles.DocInsertArticleRequest;
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
        return service.listArticlesByDeptId(documentArticlesRequest.getDept_id());
    }

    @GetMapping("/listById")
    public BaseResponse getArticlesById(@RequestParam Long id){
        return service.ArticleById(id);
    }


    @PostMapping("/add")
    public BaseResponse insertArticlesList(@RequestBody DocInsertArticleRequest articleRequest, HttpServletRequest request){
         return service.insertArticle(articleRequest, request);
    }

   @PutMapping("/updateArticle")
    public BaseResponse updateArticle(@RequestBody DocumentArticles articleSaveRequest){
        return service.updateArticles(articleSaveRequest);
    }


}
