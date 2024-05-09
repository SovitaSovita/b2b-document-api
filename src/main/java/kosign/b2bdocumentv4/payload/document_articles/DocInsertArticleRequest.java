package kosign.b2bdocumentv4.payload.document_articles;

import lombok.Data;

@Data
public class DocInsertArticleRequest {
    private Long tag_id;
    private String title;
    private String content_body;
//    private Long user_id;
    private String file_article_id;
    private Long status = 1L;
    private Long user_id;
    private String dept_id;
//    private String dep_id;
}
