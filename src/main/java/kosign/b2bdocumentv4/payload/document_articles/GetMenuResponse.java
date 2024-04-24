package kosign.b2bdocumentv4.payload.document_articles;

import kosign.b2bdocumentv4.entity.doc_articles.DocumentArticles;
import kosign.b2bdocumentv4.payload.doc_tags.DocTagResponse;
import lombok.Data;

import java.util.List;

@Data
public class GetMenuResponse {

    List<DocumentArticlesResponse> articlesList;
    List<DocTagResponse> tagList;
}
