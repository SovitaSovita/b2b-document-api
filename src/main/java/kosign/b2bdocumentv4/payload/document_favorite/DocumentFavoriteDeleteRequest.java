package kosign.b2bdocumentv4.payload.document_favorite;


import lombok.Data;

@Data
public class DocumentFavoriteDeleteRequest {

    private String user_id;
    private Long article_id;

}
