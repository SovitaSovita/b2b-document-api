package kosign.b2bdocumentv4.service.doc_articles;

import jakarta.servlet.http.HttpServletRequest;
import kosign.b2bdocumentv4.entity.doc_articles.DocumentArticles;
import kosign.b2bdocumentv4.entity.doc_articles.DocumentArticlesRepository;
import kosign.b2bdocumentv4.entity.doc_users.DocumentUsers;
import kosign.b2bdocumentv4.payload.BaseResponse;

import kosign.b2bdocumentv4.payload.document_articles.DocInsertArticleRequest;
import kosign.b2bdocumentv4.payload.document_articles.DocumentArticlesRequest;
import kosign.b2bdocumentv4.service.doc_users.DocUserServiceImpl;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class DocumentArticlesServiceImpl implements DocumentArticlesService {

    private final DocumentArticlesRepository repository;
    private final DocUserServiceImpl docUserService;

    private ModelMapper modelMapper;
    @Override
    public BaseResponse listArticlesByDeptId(String dept_id) {
        List<DocumentArticles> list = repository.getByDepartmentId(dept_id);

        return BaseResponse.builder()
                .rec(list)
                .build();
    }

    @Override
    public BaseResponse ArticleById(Long id) {

        List<Map<String, Object>> response = repository.findArticlesById(id);

        System.out.println(" >> " + response);

        if(response.isEmpty())
        return BaseResponse.builder()
                .rec(response)
                .code("404")
                .message("No Data found!")
                .build();

        else  return BaseResponse.builder()
                .rec(response)
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

        System.out.println("Hello >>> " + articleRequest);

        DocumentArticles articles = new DocumentArticles();
        articles.setTag_id(articleRequest.getTag_id());
        articles.setTitle(articleRequest.getTitle());
        articles.setContent_body(articleRequest.getContent_body());
        articles.setUser_id(documentUsers.getId());
        articles.setFile_article_id(articleRequest.getFile_article_id());
        articles.setDept_id(String.valueOf(documentUsers.getDept_id()));
        articles.setStatus(articleRequest.getStatus());
        articles.setIsfavorite("0");

        return BaseResponse.builder()
                .rec(repository.save(articles))
                .code("200")
                .message("Article Inserted Successfully.")
                .build();
    }

}
