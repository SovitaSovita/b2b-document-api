package kosign.b2bdocumentv4.service.doc_favorite;

import kosign.b2bdocumentv4.payload.BaseResponse;
import kosign.b2bdocumentv4.payload.document_favorite.DocumentFavoriteDeleteRequest;
import kosign.b2bdocumentv4.payload.document_favorite.DocumentFavoriteRequest;

public interface DocFavoriteService {

    // List
    BaseResponse listFavorite(Long user_id);
    // Add
    BaseResponse saveFavorite(DocumentFavoriteRequest documentFavoriteRequest);
    // Delete
    BaseResponse deleteFavorite(DocumentFavoriteDeleteRequest documentFavoriteDeleteRequest);

}
