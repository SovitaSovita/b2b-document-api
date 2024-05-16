package kosign.b2bdocumentv4.mapper;


import kosign.b2bdocumentv4.entity.doc_favorite.DocumentFavorite;
import kosign.b2bdocumentv4.payload.document_favorite.DocumentFavoriteDeleteRequest;
import kosign.b2bdocumentv4.payload.document_favorite.DocumentFavoriteRequest;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface DocumentFavoriteMapper {

    // Add
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    DocumentFavorite requestToEntity(DocumentFavoriteRequest documentFavoriteRequest);


    // Delete
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    DocumentFavorite requestToDelete(DocumentFavoriteDeleteRequest documentFavoriteDeleteRequest);

}
