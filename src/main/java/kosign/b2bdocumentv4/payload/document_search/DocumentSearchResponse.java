package kosign.b2bdocumentv4.payload.document_search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentSearchResponse {
    private int id;
    private int tag_id;
    private String tag_title;
    private String title;
    private String content_body;
}
