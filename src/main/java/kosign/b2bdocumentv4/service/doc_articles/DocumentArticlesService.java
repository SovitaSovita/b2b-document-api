package kosign.b2bdocumentv4.service.doc_articles;

import kosign.b2bdocumentv4.entity.doc_articles.DocumentArticles;
import kosign.b2bdocumentv4.payload.BaseResponse;

import java.util.List;

public interface DocumentArticlesService {
    BaseResponse listArticles(String dep_id);
}
