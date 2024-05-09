package kosign.b2bdocumentv4.payload.doc_tags;

import kosign.b2bdocumentv4.entity.doc_articles.DocumentArticles;
import kosign.b2bdocumentv4.entity.doc_tags.DocumentTag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagArticleRespone {

    private List<DocumentTag> tagList;
    private List<Map<Object, String>> articleList;

}
