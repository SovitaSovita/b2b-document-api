package kosign.b2bdocumentv4.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import kosign.b2bdocumentv4.dto.ArticleEditorDto;
import kosign.b2bdocumentv4.payload.BaseResponse;
import kosign.b2bdocumentv4.payload.document_article.DocInsertArticleRequest;
import kosign.b2bdocumentv4.payload.document_article.DocUpdateArticleRequest;
import kosign.b2bdocumentv4.payload.document_article.DocumentArticlesRequest;
import kosign.b2bdocumentv4.service.doc_articles.DocumentArticlesServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
        return service.listArticlesByDeptId(documentArticlesRequest.getDept_id());
    }

    @GetMapping("/listById")
    public BaseResponse getArticlesById(@RequestParam Long id){
        return service.ArticleById(id);
    }

    @PostMapping("/add")
    public BaseResponse insertArticlesList(@RequestBody DocInsertArticleRequest articleRequest, HttpServletRequest request) throws JsonProcessingException {
         return service.insertArticle(articleRequest, request);
    }

    @PostMapping("/addEditor")
    public BaseResponse assignEditorArticle(@RequestParam Long articleId, @RequestBody List<ArticleEditorDto> articleRequest) throws JsonProcessingException {
        return BaseResponse.builder()
                .rec(service.asssignEditorArticle(articleId, articleRequest))
                .code("200")
                .message("Editors Assigned Successfully.")
                .build();
    }

   @PutMapping("/updateArticle")
    public BaseResponse updateArticle(@RequestBody DocUpdateArticleRequest docUpdateArticleRequest){
        return service.updateArticles(docUpdateArticleRequest);

    }

    @DeleteMapping("/delete")
    public BaseResponse deleteArticle(@RequestParam Long articleId, HttpServletRequest request){
        return service.deleteArticle(articleId, request);
    }
}
