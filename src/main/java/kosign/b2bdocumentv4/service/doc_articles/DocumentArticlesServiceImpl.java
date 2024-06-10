package kosign.b2bdocumentv4.service.doc_articles;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import kosign.b2bdocumentv4.entity.doc_articles.DocumentArticles;
import kosign.b2bdocumentv4.entity.doc_articles.DocumentArticlesRepository;
import kosign.b2bdocumentv4.entity.doc_users.DocumentUsers;
import kosign.b2bdocumentv4.exception.NotFoundExceptionClass;
import kosign.b2bdocumentv4.payload.BaseResponse;

import kosign.b2bdocumentv4.payload.document_article.DocInsertArticleRequest;
import kosign.b2bdocumentv4.payload.document_article.DocUpdateArticleRequest;
import kosign.b2bdocumentv4.payload.document_article.DocumentArticlesResponse;
import kosign.b2bdocumentv4.service.doc_users.DocUserServiceImpl;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DocumentArticlesServiceImpl implements DocumentArticlesService {

    private final DocumentArticlesRepository repository;
    private final DocUserServiceImpl docUserService;
    public BaseResponse updateArticle;

    private ModelMapper modelMapper;
    @Override
    public BaseResponse listArticlesByDeptId(int dept_id) {
        List<DocumentArticles> list = repository.getByDepartmentId(dept_id);

        return BaseResponse.builder()
                .rec(list)
                .build();
    }

    @Override
    public BaseResponse ArticleById(Long id) {

        Map<String, Object> response = repository.findArticlesById(id);

        if (response.isEmpty()) {
            return BaseResponse.builder()
                    .code("404")
                    .message("No Data Found!")
                    .build();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String data;
        try {
            data =  objectMapper.writeValueAsString(BaseResponse.builder()
                    .rec(response)
                    .code("200")
                    .message("successfully fetch users")
                    .build());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Map<String, Object> responseMap = null;
        try {
            responseMap = objectMapper.readValue(data, Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Map<String, Object> recMap = (Map<String, Object>) responseMap.get("rec");
        String fileArticleId = (String) recMap.get("file_article_id");

        List<Map<String, Object>> files = repository.getAllFiles(fileArticleId);
        if (responseMap != null) {
            responseMap.put("data_file", files);
        }

        return BaseResponse.builder()
                .rec(BaseResponse.builder()
                        .rec(responseMap != null ? responseMap : response)
                        .code("200")
                        .message("successfully fetch users")
                        .build())  // Use the JSON string for response data
                .code("200")
                .message("successfully fetch users")
                .build();
    }
    // List menu
    @Override
    public BaseResponse getMenuByDeptID(Long dept_id) {
        List<Map<String, Object>> list = repository.getMenuByDept_ID(String.valueOf(dept_id));
        if (list.isEmpty()) {
            return BaseResponse.builder()
                    .rec(Collections.emptyList()) // Empty list
                    .code("404")
                    .message("No data found for the given department ID: " + dept_id)
                    .build();
        } else {
            return BaseResponse.builder()
                    .rec(list)
                    .code("200")
                    .message("Successfully fetched menu")
                    .build();
        }
    }

    @Override
    public BaseResponse insertArticle(DocInsertArticleRequest articleRequest ,HttpServletRequest request) {

        DocumentUsers documentUsers = docUserService.getCurrentUser(request);

        DocumentArticles articles = new DocumentArticles();
        articles.setTag_id(articleRequest.getTag_id());
        articles.setTitle(articleRequest.getTitle());
        articles.setContent_body(articleRequest.getContent_body());
        articles.setUser_id(documentUsers.getId());
        articles.setFile_article_id(articleRequest.getFile_article_id());
        articles.setDept_id(documentUsers.getDept_id());
        articles.setCreate_date(Timestamp.valueOf(LocalDateTime.now()));
        articles.setCreatedBy(documentUsers.getUsername());
        articles.setStatus(articleRequest.getStatus());
        articles.setIsfavorite("0");

        return BaseResponse.builder()
                .rec(repository.save(articles))
                .code("200")
                .message("Article Inserted Successfully.")
                .build();
    }

    @Override
    public BaseResponse updateArticles(DocUpdateArticleRequest docUpdateArticleRequest) {

        // Find the existing article by ID
        Optional<DocumentArticles> existingArticleOptional = repository.findById(docUpdateArticleRequest.getId());

            // Check if the article exists
            if (existingArticleOptional.isPresent()) {
                DocumentArticles existingArticle = existingArticleOptional.get();

                existingArticle.setTitle(docUpdateArticleRequest.getTitle());
                existingArticle.setId(docUpdateArticleRequest.getId());
                existingArticle.setContent_body(docUpdateArticleRequest.getContent_body());
                existingArticle.setUser_id(docUpdateArticleRequest.getUser_id());
                existingArticle.setDept_id(docUpdateArticleRequest.getDept_id());
                existingArticle.setModifiedBy(docUpdateArticleRequest.getModifiedBy());
                existingArticle.setModified_date(Timestamp.valueOf(LocalDateTime.now()));
                existingArticle.setStatus(docUpdateArticleRequest.getStatus());
                System.out.println("vanda test >>" + existingArticle);

                return BaseResponse.builder()
                        .code("200")
                        .rec(repository.save(existingArticle))
                        .message("Update success for article ID ")
                        .isError(true)
                        .build();

            } else {
                // Article with the given ID not found
                return BaseResponse.builder()
                        .code("404")
                        .message("Article not found for ID: ")
                        .isError(true)
                        .build();
            }
    }

    @Override
    public BaseResponse deleteArticle(Long articleId, HttpServletRequest request) {
        DocumentArticles articles = repository.findById(articleId).orElseThrow(() -> new NotFoundExceptionClass("Article not found."));

        repository.deleteById(articles.getId());

        return BaseResponse.builder()
                .code("200")
                .message("Article Deleted Successfully.")
                .build();

    }

}
