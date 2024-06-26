package kosign.b2bdocumentv4.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import kosign.b2bdocumentv4.payload.BaseResponse;
import kosign.b2bdocumentv4.payload.document_article.DocInsertArticleRequest;
import kosign.b2bdocumentv4.payload.document_article.DocUpdateArticleRequest;
import kosign.b2bdocumentv4.payload.document_article.DocumentArticlesRequest;
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
    public BaseResponse updateArticle(@RequestBody DocUpdateArticleRequest docUpdateArticleRequest){
        return service.updateArticles(docUpdateArticleRequest);

    }

    @DeleteMapping("/delete")
    public BaseResponse deleteArticle(@RequestParam Long articleId, HttpServletRequest request){
        return service.deleteArticle(articleId, request);
    }

    // New
    @GetMapping("/listBayTagId")
    public BaseResponse getArticleBayTagId(@RequestParam int tag_id) {
        return service.listArticlesByTagId(tag_id);
    }
}
