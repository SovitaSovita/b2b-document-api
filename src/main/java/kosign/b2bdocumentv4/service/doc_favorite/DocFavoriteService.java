package kosign.b2bdocumentv4.service.doc_favorite;

import kosign.b2bdocumentv4.payload.BaseResponse;
import kosign.b2bdocumentv4.payload.document_favorite.DocumentFavoriteDeleteRequest;
import kosign.b2bdocumentv4.payload.document_favorite.DocumentFavoriteRequest;

public interface DocFavoriteService {

    // List
    BaseResponse listFavorite(String user_id);
    // Add
    BaseResponse saveFavorite(DocumentFavoriteRequest documentFavoriteRequest);
    // Delete
    BaseResponse deleteFavorite(DocumentFavoriteDeleteRequest documentFavoriteDeleteRequest);

    // List favorite base on user
    BaseResponse checkIsFavorite(String user_id,Long article_id, Long dept_id);

}
