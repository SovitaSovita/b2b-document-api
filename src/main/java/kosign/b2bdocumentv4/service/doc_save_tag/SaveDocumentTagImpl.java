package kosign.b2bdocumentv4.service.doc_save_tag;


import kosign.b2bdocumentv4.entity.doc_articles.DocumentArticles;
import kosign.b2bdocumentv4.entity.doc_tags.DocumentTag;
import kosign.b2bdocumentv4.entity.doc_tags.DocumentTagRepository;
import kosign.b2bdocumentv4.entity.doc_users.DocumentUsers;
import kosign.b2bdocumentv4.mapper.*;
import kosign.b2bdocumentv4.payload.BaseResponse;
import kosign.b2bdocumentv4.payload.doc_tags.*;
import kosign.b2bdocumentv4.utils.AuthHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SaveDocumentTagImpl  implements SaveDocumentTagService {

    private final DocumentTagRepository documentTagRepository;
    private final DocumentTagMapper documentTagMapper;
    private final DocumentTagUpdateMapper documentTagUpdateMapper;
    private final DocumentTageDeleteMapper documentTageDeleteMapper;
    private final DocumentTagListMapper documentTagListMapper;


    @Override
    public BaseResponse saveTag(DocTagRequest docTagRequest) {
        DocumentTag documentTag = documentTagMapper.requestToEntity(docTagRequest);

        documentTag.setStatus(1L);
        documentTag.setUser_name(docTagRequest.getUser_name());
        documentTag.setTitle(docTagRequest.getTitle());

        documentTag.setDept_id(docTagRequest.getDept_id());
        documentTag.setCreate_date(Timestamp.valueOf(LocalDateTime.now()));

        var newEntity = documentTagRepository.save(documentTag);
        System.out.println("Save tag " + newEntity) ;
        return  BaseResponse.builder().rec(newEntity).code("200").message("success").build();
    }

    // update
    @Override
    public BaseResponse updateTag(DocTagUpdateRequest docTagUpdateRequest) {

        DocumentTag documentTag = documentTagUpdateMapper.requestToEntity(docTagUpdateRequest);

        documentTag.setId(docTagUpdateRequest.getId());
        documentTag.setStatus(1L);
        documentTag.setUser_name(docTagUpdateRequest.getUser_name());
        documentTag.setDept_id(docTagUpdateRequest.getDept_id());
        documentTag.setTitle(docTagUpdateRequest.getTitle());
        documentTag.setModified_date(Timestamp.valueOf(LocalDateTime.now()));

        var newEntity = documentTagRepository.save(documentTag);
        return  BaseResponse.builder().rec(newEntity).code("200").message("success").build();

    }

    // delete
    @Override
    public BaseResponse deleteTag(DocTagDeleteRequest docTagDeleteRequest) {
        var user = AuthHelper.getUser();
        System.out.println(user);
        var newEntity = documentTagRepository.findById(docTagDeleteRequest.getId()).orElse(null);
        if (null == newEntity) return BaseResponse.builder().isError(true).code("404").message("Document Tag with id ["+docTagDeleteRequest.getId()+"] not exist!").build();
        documentTagRepository.deleteById(newEntity.getId());
        return  BaseResponse.builder().code("200").message("Delete success!").build();
    }

    // list tag, article
    @Override
    public BaseResponse getAllTagByDept_Id(Long dept_id) {
        // Retrieve tags by department ID
        List<DocumentTag> litDocTagByDep_ID = documentTagRepository.getTagsByDepId(dept_id);
        // Retrieve article
        List<DocumentTag> listDocArticleByDept_Id = documentTagRepository.getArticles();
        System.out.println("Tag >>  " + litDocTagByDep_ID);

        // Check if the list is null or empty
        if (litDocTagByDep_ID == null || litDocTagByDep_ID.isEmpty()) {
            return BaseResponse.builder()
                    .code("404")
                    .message("No data found for department ID: " + dept_id)
                    .isError(true)
                    .build();
        }

        // Mapping tags to appropriate response objects
        List<DocTagResponse> responseTagList = litDocTagByDep_ID.stream().map(documentTagListMapper::toResponse).toList();

        return BaseResponse.builder().code("200").message("Get success").isError(false).rec(responseTagList).build();
    }



    // New API list document tag and document article
    @Override
    public BaseResponse getTag(Long dept_id) {
        List<DocumentTag> listTagAndArticle = documentTagRepository.getDocumentTag(dept_id);

//        List<DocTagResponse> response = listTagAndArticle.stream().map(documentTagListMapper::toResponse).toList();


        return BaseResponse.builder().code("200").message("success").isError(false).rec(listTagAndArticle).build();
    }








    // Test
    @Override
    public BaseResponse listTagAndArticle(Long dept_id) {
        // 1
        List<Map<Object, String>> listAllAtricle = documentTagRepository.getDocumentArticleList(dept_id);

        // 2
        List<DocumentTag> listAllTag = documentTagRepository.getDocumentTag(dept_id);

        // return BaseResponse.builder().code("200").message("success").isError(false).rec().build();
        TagArticleRespone tagArticleRespone = new TagArticleRespone(listAllTag, listAllAtricle);

        return BaseResponse.builder().code("200")
                .message("success")
                .isError(false)
                .rec(tagArticleRespone)
                .build();






    }









}
