package kosign.b2bdocumentv4.payload.document_articles;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DocumentArticlesRequest {
    @NotNull
    private String dep_id;
}
