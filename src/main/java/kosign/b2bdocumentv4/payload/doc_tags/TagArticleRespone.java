package kosign.b2bdocumentv4.payload.doc_tags;

import kosign.b2bdocumentv4.entity.doc_tags.DocumentTag;

import java.util.List;

public class TagArticleRespone {

    private List<DocumentTag> tagList;
    private List<DocumentTag> articleList;

    public TagArticleRespone(List<DocumentTag> tagList, List<DocumentTag> articleList) {
        this.tagList = tagList;
        this.articleList = articleList;
    }

    public List<DocumentTag> getTagList() {
        return tagList;
    }

    public List<DocumentTag> getArticleList() {
        return articleList;
    }
}
