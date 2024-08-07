package kosign.b2bdocumentv4.service.doc_save_tag;


import kosign.b2bdocumentv4.entity.doc_articles.DocumentArticlesRepository;
import kosign.b2bdocumentv4.entity.doc_favorite.DocumentFavoriteRepository;
import kosign.b2bdocumentv4.entity.doc_tags.DocumentTag;
import kosign.b2bdocumentv4.entity.doc_tags.DocumentTagRepository;
import kosign.b2bdocumentv4.mapper.*;
import kosign.b2bdocumentv4.payload.BaseResponse;
import kosign.b2bdocumentv4.payload.doc_tags.*;
import kosign.b2bdocumentv4.payload.document_favorite.DocumentFavoriteDeleteRequest;
import kosign.b2bdocumentv4.service.doc_articles.DocumentArticlesServiceImpl;
import kosign.b2bdocumentv4.service.doc_favorite.DocFavoriteService;
import kosign.b2bdocumentv4.utils.AuthHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SaveDocumentTagImpl  implements SaveDocumentTagService {

    private final DocumentTagRepository documentTagRepository;
    private final DocumentTagMapper documentTagMapper;
    private final DocumentTagUpdateMapper documentTagUpdateMapper;
    private final DocumentTageDeleteMapper documentTageDeleteMapper;
    private final DocumentTagListMapper documentTagListMapper;
    private final DocumentArticlesServiceImpl documentArticlesService;
    private final DocumentFavoriteRepository documentFavoriteRepository;

    private final DocFavoriteService docFavoriteService;
    private final DocumentArticlesRepository documentArticlesRepository;
    // save
    @Override
    public BaseResponse saveTag(DocTagRequest docTagRequest) {

        // department
        if (docTagRequest.getDept_id() == null) {
            return BaseResponse.builder().code("400").message("Department ID is empty").isError(true).rec(null).build();
        }
        // title
        if (docTagRequest.getTitle() == null || docTagRequest.getTitle().isEmpty()) {
            return BaseResponse.builder().code("400").message("Title is empty").isError(true).rec(null).build();
        }
        // user name
        if (docTagRequest.getUser_name() == null || docTagRequest.getUser_name().isEmpty()) {
            return BaseResponse.builder().code("400").message("User name is empty").isError(true).rec(null).build();
        }
        // Status
        if (docTagRequest.getStatus() == null) {
            return BaseResponse.builder().code("400").message("Status is empty").isError(true).rec(null).build();
        }

        try {
            DocumentTag documentTag = documentTagMapper.requestToEntity(docTagRequest);
            // set fields
            documentTag.setStatus(docTagRequest.getStatus());
            documentTag.setUser_name(docTagRequest.getUser_name());
            documentTag.setTitle(docTagRequest.getTitle());
            documentTag.setDept_id(docTagRequest.getDept_id());
            documentTag.setCreate_date(Timestamp.valueOf(LocalDateTime.now()));

            // save entity
            DocumentTag newEntity = documentTagRepository.save(documentTag);

            return BaseResponse.builder().rec(newEntity).code("200").message("success").isError(false).build();
        } catch (Exception e) {
            return BaseResponse.builder().code("500").message("An error occurred: " + e.getMessage()).isError(true).rec(null).build();
        }
    }


    // update
    @Override
    public BaseResponse updateTag(DocTagUpdateRequest docTagUpdateRequest) {

        // ID
        if (docTagUpdateRequest.getId() == null) {
            return BaseResponse.builder().code("400").message("ID is empty").isError(true).rec(null).build();
        }
        // department ID
        if (docTagUpdateRequest.getDept_id() == null) {
            return BaseResponse.builder().code("400").message("Department ID is empty").isError(true).rec(null).build();
        }
        // title
        if (docTagUpdateRequest.getTitle() == null || docTagUpdateRequest.getTitle().isEmpty()) {
            return BaseResponse.builder().code("400").message("Title is empty").isError(true).rec(null).build();
        }
        // user name
        if (docTagUpdateRequest.getUser_name() == null || docTagUpdateRequest.getUser_name().isEmpty()) {
            return BaseResponse.builder().code("400").message("User name is empty").isError(true).rec(null).build();
        }
        // status
        if (docTagUpdateRequest.getStatus() == null) {
            return BaseResponse.builder().code("400").message("Status is empty").isError(true).rec(null).build();
        }

        try {
            DocumentTag documentTag = documentTagUpdateMapper.requestToEntity(docTagUpdateRequest);
            // set fields
            documentTag.setStatus(docTagUpdateRequest.getStatus());
            documentTag.setId(docTagUpdateRequest.getId());
            documentTag.setUser_name(docTagUpdateRequest.getUser_name());
            documentTag.setDept_id(docTagUpdateRequest.getDept_id());
            documentTag.setTitle(docTagUpdateRequest.getTitle());
            documentTag.setModified_date(Timestamp.valueOf(LocalDateTime.now()));
            // save entity
            DocumentTag updatedEntity = documentTagRepository.save(documentTag);
            return BaseResponse.builder().rec(updatedEntity).code("200").message("success").isError(false).build();
        } catch (Exception e) {
            return BaseResponse.builder().code("500").message("An error occurred: " + e.getMessage()).isError(true).build();
        }

    }

    // delete
    @Override
    public ResponseEntity<?> deleteTag(Long tagId) {
        Map<String,Object> response = new HashMap<>();
        response.put("error",true);
        // Validate the ID field
        if (tagId == null) {
            response.put("Message","Tag id is not found");
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }
        try {
            // Find the entity by ID
            var existingEntity = documentTagRepository.findById(tagId).orElse(null);
            // Find the existing ID doc_favorite
            System.out.println("vimean--------" + existingEntity);


            // Check if the entity exists
            if (existingEntity == null) {

            }
            // Delete the entity
            //documentTagRepository.deleteById(existingEntity.getId());
            System.out.println("meanmean");
            List<Long> articleId = documentTagRepository.findArticlesId(existingEntity.getId());
            System.out.println("Helllllloooo" + articleId);


            for(int i = 0; i < articleId.size(); i++) {
                Long currentArticleId = articleId.get(i);
                System.out.println("[DEBUG] Processing articleId: " + currentArticleId);

                var existingFavoriteID = documentFavoriteRepository.findArticlesId(articleId.get(i));
                System.out.println("vimean" + existingFavoriteID);

                if(!documentFavoriteRepository.findArticlesId(articleId.get(i)).isEmpty()){
                    docFavoriteService.deleteArticleFromFavorite(articleId.get(i));
                }

                if(!documentArticlesRepository.findArticlesById(articleId.get(i)).isEmpty()){
                    documentArticlesService.deleteArticle(articleId.get(i),null);
                }

            }
            try {
                if(documentTagRepository.findById(existingEntity.getId()).isPresent()){
                    documentTagRepository.deleteById(existingEntity.getId());
                    response.put("error",false);
                    response.put("Message","Delete success");
                }else{
                    response.put("Message","Tag Not Found");
                }

            }catch (Exception e){
                System.err.println("[ERROR] Failed to delete tag. Error: " + e.getMessage());
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }


    // New API list document tag and document article
    @Override
    public BaseResponse getTag(Long dept_id ,String username) {

        List<DocumentTag> listTagAndArticle = documentTagRepository.getDocumentTag(dept_id, 1L, username);
        // List<DocTagResponse> response = listTagAndArticle.stream().map(documentTagListMapper::toResponse).toList();
        if (listTagAndArticle.isEmpty()) {
            return BaseResponse.builder()
                    .code("204")
                    .message("no result")
                    .rec(Collections.emptyList())
                    .build();
        } else {
            return BaseResponse.builder().code("200").message("success").isError(false).rec(listTagAndArticle).build();
        }
    }

    // Test
    @Override
    public BaseResponse listTagAndArticle(Long dept_id, Long status, String username) {

        // 1
        List<Map<Object, String>> listAllArticle = documentTagRepository.getDocumentArticleList(dept_id, status, username);
        // 2
        List<DocumentTag> listAllTag = documentTagRepository.getDocumentTag(dept_id, status, username);

        // return BaseResponse.builder().code("200").message("success").isError(false).rec().build();
        TagArticleRespone tagArticleRespone = new TagArticleRespone(listAllTag, listAllArticle);

        return BaseResponse.builder().code("200")
                .message("success")
                .isError(false)
                .rec(tagArticleRespone)
                .build();
    }

}
