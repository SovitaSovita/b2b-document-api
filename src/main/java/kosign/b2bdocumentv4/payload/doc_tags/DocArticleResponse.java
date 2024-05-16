package kosign.b2bdocumentv4.payload.doc_tags;


import lombok.Data;

@Data
public class DocArticleResponse {

    private Long id;
    private String status;
    private String title;
    private Long dept_id;
    private Long user_id;
}
