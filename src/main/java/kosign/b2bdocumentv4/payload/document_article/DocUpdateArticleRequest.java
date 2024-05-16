package kosign.b2bdocumentv4.payload.document_article;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class DocUpdateArticleRequest {
    private Long id;
    private String title;
    private String content_body;
    private Long user_id;
    private String dept_id;
    private String modifiedBy;
    private Timestamp modified_date;


}
