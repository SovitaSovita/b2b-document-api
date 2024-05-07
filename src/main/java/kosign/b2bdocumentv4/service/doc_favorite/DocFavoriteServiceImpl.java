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


@Service
@RequiredArgsConstructor
public class DocFavoriteServiceImpl implements DocFavoriteService {

    private final DocumentFavoriteRepository repository;

    // Mapper
    private final DocumentFavoriteMapper documentFavoriteMapper;

    @Override
    public BaseResponse listFavorite(Long user_id) {
        List<DocumentFavorite> favorite = repository.listAllFavorite(user_id);
        System.out.println("Favorite" + favorite);
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

        // User
        documentFavorite.setUser_id(documentFavoriteRequest.getUser_id());
        // Article
        documentFavorite.setArticle_id(documentFavoriteRequest.getArticle_id());
        // Department
        documentFavorite.setDept_id(documentFavoriteRequest.getDept_id());

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

        Long articleId = documentFavoriteDeleteRequest.getId();


        try {
            repository.deleteById(articleId);
        } catch (EmptyResultDataAccessException e) {

            return BaseResponse.builder()
                    .isError(true)
                    .code("404")
                    .message("Cannot find favorite with id " + articleId)
                    .build();
        }

        // Return success response
        return BaseResponse.builder()
                .code("200")
                .message("Delete successful")
                .build();
    }


}
