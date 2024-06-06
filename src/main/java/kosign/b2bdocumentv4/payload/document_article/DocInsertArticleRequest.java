package kosign.b2bdocumentv4.payload.document_article;

import lombok.Data;

@Data
public class DocInsertArticleRequest {
    private Long tag_id;
    private String title;
    private String content_body;
    private String file_article_id;
    private Long status = 1L;
}
