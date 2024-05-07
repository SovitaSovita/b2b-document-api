package kosign.b2bdocumentv4.payload.document_favorite;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DocumentFavoriteRequest {

    private Long user_id;
    private Long article_id;
    private Long dept_id;
}
