package kosign.b2bdocumentv4.service.doc_articles;

import kosign.b2bdocumentv4.entity.doc_articles.DocumentArticles;
import kosign.b2bdocumentv4.entity.doc_articles.DocumentArticlesRepository;
import kosign.b2bdocumentv4.payload.BaseResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DocumentArticlesServiceImpl implements DocumentArticlesService {

    private final DocumentArticlesRepository repository;
    @Override
    public BaseResponse listArticles(String dep_id) {
        List<DocumentArticles> list = repository.getByDepartmentId(dep_id);
        return BaseResponse.builder()
                .rec(list)
                .build();
    }
}
