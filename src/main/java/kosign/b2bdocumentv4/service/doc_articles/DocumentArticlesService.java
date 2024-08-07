package kosign.b2bdocumentv4.service.doc_articles;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import kosign.b2bdocumentv4.payload.BaseResponse;
import kosign.b2bdocumentv4.payload.document_article.DocInsertArticleRequest;
import kosign.b2bdocumentv4.payload.document_article.DocUpdateArticleRequest;

public interface DocumentArticlesService {
    BaseResponse listArticlesByDeptId(int dep_id);
    BaseResponse ArticleById(Long id);

    BaseResponse getMenuByDeptID(Long dept_id);

    BaseResponse insertArticle(DocInsertArticleRequest articleRequest, HttpServletRequest request) throws JsonProcessingException;

    BaseResponse updateArticles(DocUpdateArticleRequest docUpdateArticleRequest);

    BaseResponse deleteArticle(Long articleId, HttpServletRequest request);
}

