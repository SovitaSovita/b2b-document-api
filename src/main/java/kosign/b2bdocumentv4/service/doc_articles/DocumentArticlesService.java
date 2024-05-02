package kosign.b2bdocumentv4.service.doc_articles;

import jakarta.servlet.http.HttpServletRequest;
import kosign.b2bdocumentv4.entity.doc_articles.DocumentArticles;
import kosign.b2bdocumentv4.payload.BaseResponse;
import kosign.b2bdocumentv4.payload.document_articles.DocInsertArticleRequest;
import kosign.b2bdocumentv4.payload.document_articles.DocumentArticlesRequest;

import java.util.List;

public interface DocumentArticlesService {
    BaseResponse listArticlesByDeptId(String dep_id);
    BaseResponse ArticleById(Long id);

    BaseResponse getMenuByDeptID(Long dept_id);

    BaseResponse insertArticle(DocInsertArticleRequest articleRequest, HttpServletRequest request);
}

