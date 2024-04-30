package kosign.b2bdocumentv4.payload.document_articles;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetMenuResponse {
    private Long id;
    private Long tag_id;
    private String title;
    private String content_body;
    private Long user_id;
    private Long status;
    private String file_article_id;
    private String dept_id;
    private String isfavorite;
    private String tag_title; //added
}
