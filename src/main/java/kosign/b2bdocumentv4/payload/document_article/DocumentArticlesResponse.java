package kosign.b2bdocumentv4.payload.document_article;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentArticlesResponse {

    private int id;
    private int tag_id;
    private String file_article_id;
    private String tag_title;
    private String title;
    private String content_body;
    private Timestamp modified_date;
    private Timestamp create_date;
    private String created_by;
    private int dept_id;
    private String modified_by;
    private int status;
    private String username;
    private String image;
    private int user_id;
    private ArrayList<FileDetail> fileDetails;
}
