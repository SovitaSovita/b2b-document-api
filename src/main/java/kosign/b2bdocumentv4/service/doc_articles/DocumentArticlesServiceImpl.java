package kosign.b2bdocumentv4.service.doc_articles;

import kosign.b2bdocumentv4.entity.doc_articles.DocumentArticles;
import kosign.b2bdocumentv4.entity.doc_articles.DocumentArticlesRepository;
import kosign.b2bdocumentv4.payload.BaseResponse;
import kosign.b2bdocumentv4.payload.login.response.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DocumentArticlesServiceImpl implements DocumentArticlesService {

    private final DocumentArticlesRepository repository;
    //private final DocumentArticlesRepository idMapper ;
    @Override
    public BaseResponse listArticles(String dept_id) {
        List<DocumentArticles> list = repository.getByDepartmentId(dept_id);
        return BaseResponse.builder()
                .rec(list)
                .build();
    }

    @Override
    public BaseResponse listAllArticles(Long id) {
        List<DocumentArticles> list = repository.findArticlesById(id);
         return BaseResponse.builder()
                 .rec(list)
                 .code("200")
                 .message("successfully fetch users")
                 .build();
    }

    // List menu
    @Override
    public BaseResponse getMenuByDept_ID(String dept_id) {
        List<DocumentArticles> list = repository.getMenuByDept_ID(dept_id);
        return BaseResponse.builder()
                .rec(list)
                .code("200")
                .message("successfully fetch menu")
                .build();
    }

}
