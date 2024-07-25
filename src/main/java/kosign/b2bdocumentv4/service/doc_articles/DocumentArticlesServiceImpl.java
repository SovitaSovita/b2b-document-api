package kosign.b2bdocumentv4.service.doc_articles;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import kosign.b2bdocumentv4.dto.ArticleEditorDto;
import kosign.b2bdocumentv4.entity.doc_articles.*;
import kosign.b2bdocumentv4.exception.NotFoundExceptionClass;
import kosign.b2bdocumentv4.jwt.JwtTokenUtils;
import kosign.b2bdocumentv4.payload.BaseResponse;

import kosign.b2bdocumentv4.payload.doc_users.UserDetiailPayload;
import kosign.b2bdocumentv4.payload.document_article.DocInsertArticleRequest;
import kosign.b2bdocumentv4.payload.document_article.DocUpdateArticleRequest;
import kosign.b2bdocumentv4.service.doc_users.DocUserServiceImpl;
import kosign.b2bdocumentv4.utils.ApiService;
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
    private final ApiService apiService;
    private final JwtTokenUtils jwtTokenUtils;
    private final ArticleUsersRepository articleUsersRepository;


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

        DocumentArticles response = repository.findById(id).orElseThrow(
                () -> new NotFoundExceptionClass("Article " + id + " Not Found."));

        return BaseResponse.builder()
                .rec(response)  // Use the JSON string for response data
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
    public BaseResponse insertArticle(DocInsertArticleRequest articleRequest, HttpServletRequest request) throws JsonProcessingException {

        //get current user
        String username = jwtTokenUtils.getUsernameFromToken(request.getHeader("Authorization").substring(7));
        String useInttId = jwtTokenUtils.getUseInttIdFromToken(request.getHeader("Authorization").substring(7));
        UserDetiailPayload userDetiailPayload = apiService.getUserDetailFromBizLogin(username, useInttId);

        DocumentArticles articles = new DocumentArticles();
        articles.setTag_id(articleRequest.getTag_id());
        articles.setTitle(articleRequest.getTitle());
        articles.setContent_body(articleRequest.getContent_body());
        articles.setUser_id(userDetiailPayload.getUsername());
        articles.setFile_article_id(articleRequest.getFile_article_id());
        articles.setDept_id(Long.valueOf(userDetiailPayload.getDepartmentId()));
        articles.setCreate_date(Timestamp.valueOf(LocalDateTime.now()));
        articles.setCreatedBy(userDetiailPayload.getUsername());
        articles.setStatus(articleRequest.getStatus());
        articles.setIsfavorite("0");

        List<ArticleUsers> usersList = new ArrayList<>();


        ArticleUsers articleUser = new ArticleUsers();
        articleUser.setUserId(userDetiailPayload.getUsername());
        articleUser.setCompany(userDetiailPayload.getCompany());
        articleUser.setDepartment(userDetiailPayload.getDepartment());
        articleUser.setFullName(userDetiailPayload.getFullName());
        articleUser.setImage(userDetiailPayload.getProfile());
        articleUser.setArticleRole(String.valueOf(ArticleRole.OWNER));
        articleUser.setDocumentArticles(articles);
        usersList.add(articleUser);

        articles.setArticleUsers(usersList);

        DocumentArticles savedArticle = repository.save(articles); //save article
        return BaseResponse.builder()
                .rec(savedArticle)
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

    public List<ArticleUsers> asssignEditorArticle(Long articleId, List<ArticleEditorDto> articleEditorDtos) throws JsonProcessingException {

        DocumentArticles articlesExist = repository.findById(articleId)
                .orElseThrow(() -> new NotFoundExceptionClass("Article " + articleId + " Not Found."));

        List<ArticleUsers> articleUsersList = new ArrayList<>();

        for (ArticleEditorDto dto : articleEditorDtos) {
            if (!articlesExist.getArticleUsers().isEmpty()) {
                for(ArticleUsers users : articlesExist.getArticleUsers()){
                    System.out.println("users >>" + users);
                    System.out.println(dto.getUserId() + " <users> " +  users.getUserId());

                    if (Objects.equals(dto.getUserId(), users.getUserId())) {
                        throw new IllegalArgumentException("User [" + dto.getUserId() + "] already Exist.");
                    }
                }
            }
            ArticleUsers articleUser = new ArticleUsers();
            articleUser.setUserId(dto.getUserId());
            articleUser.setCompany(dto.getCompany());
            UserDetiailPayload userDetiailPayload = apiService.getUserDetailFromBizLogin(dto.getUserId(), dto.getCompany());
            articleUser.setDepartment(userDetiailPayload.getDepartment());
            articleUser.setFullName(userDetiailPayload.getFullName());
            articleUser.setImage(userDetiailPayload.getProfile());

            articleUser.setDocumentArticles(articlesExist);
            articleUser.setArticleRole(String.valueOf(ArticleRole.EDITOR)); // Set role to EDITOR or any other role if necessary

            articleUsersList.add(articleUser);

        }

        return articleUsersRepository.saveAll(articleUsersList);
    }
}
