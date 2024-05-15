package kosign.b2bdocumentv4.service.doc_favorite;

import kosign.b2bdocumentv4.entity.doc_favorite.DocumentFavorite;
import kosign.b2bdocumentv4.entity.doc_favorite.DocumentFavoriteRepository;
import kosign.b2bdocumentv4.mapper.DocumentFavoriteMapper;
import kosign.b2bdocumentv4.payload.BaseResponse;
import kosign.b2bdocumentv4.payload.document_favorite.DocumentFavoriteDeleteRequest;
import kosign.b2bdocumentv4.payload.document_favorite.DocumentFavoriteRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class DocFavoriteServiceImpl implements DocFavoriteService {

    private final DocumentFavoriteRepository repository;

    // Mapper
    private final DocumentFavoriteMapper documentFavoriteMapper;

    // Check is favorite
    public BaseResponse checkIsFavorite(String user_id, Long article_id, Long dept_id) {

        Map<Object, String> favorite = repository.checkIsFavorite(user_id, article_id, dept_id);

        System.out.println(favorite);

        if (favorite.isEmpty()) {
            return BaseResponse.builder()
                    .code("404")
                    .message("Not favorite found")
                    .isError(true)
                    .build();
        } else {
            return BaseResponse.builder()
                    .code("202")
                    .message("success")
                    .isError(false)
                    .rec(favorite)
                    .build();
        }


    }

    // List
    @Override
    public BaseResponse listFavorite(String user_id) {

        List<Map<Object, String>> favorite = repository.listAllFavorite(user_id);

        return BaseResponse.builder()
                .code("202")
                .message("success")
                .isError(false)
                .rec(favorite)
                .build();
    }


    // Add
    @Override
    public BaseResponse saveFavorite(DocumentFavoriteRequest documentFavoriteRequest) {

        DocumentFavorite documentFavorite = documentFavoriteMapper.requestToEntity(documentFavoriteRequest);

        // Article ID
        documentFavorite.setArticle_id(documentFavoriteRequest.getArticle_id());
        // Department ID
        documentFavorite.setDept_id(documentFavoriteRequest.getDept_id());
        // user ID
        documentFavorite.setUser_id(documentFavoriteRequest.getUser_id());

        // Using save method
        var newEntity = repository.save(documentFavorite);
        System.out.println("Insert favorite" + newEntity);

        return BaseResponse.builder()
                .rec(newEntity)
                .code("200")
                .message("success")
                .build();
    }

    // Delete
    @Override
    public BaseResponse deleteFavorite(DocumentFavoriteDeleteRequest documentFavoriteDeleteRequest) {

        String userId  = documentFavoriteDeleteRequest.getUser_id();
        Long articleId = documentFavoriteDeleteRequest.getArticle_id();

        try {
           int rowsDeleted = repository.deleteFavoriteByUserIdAndArticleId(userId, articleId);
           if (rowsDeleted == 0) {
               return BaseResponse.builder()
                       .isError(true)
                       .code("404")
                       .message("Cannot find favorite with user_id " + userId + " and article_id " + articleId)
                       .build();
           }
        } catch (EmptyResultDataAccessException e) {

            return BaseResponse.builder()
                    .isError(true)
                    .code("404")
                    .message("Cannot find favorite with user_id " + userId + " and article_id " + articleId)
                    .build();
        }

        // Return success response
        return BaseResponse.builder()
                .code("200")
                .message("Delete successful")
                .build();
    }





}
