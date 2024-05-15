package kosign.b2bdocumentv4.payload.document_article;

import lombok.Data;

@Data
public class DocUpdateArticleRequest {
    private Long id;
    private String title;
    private String content_body;
    private Long user_id;
    private String dept_id;



}
