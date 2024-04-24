package kosign.b2bdocumentv4.service.doc_save_tag;


import kosign.b2bdocumentv4.entity.doc_tags.DocumentTag;
import kosign.b2bdocumentv4.entity.doc_tags.DocumentTagRepository;
import kosign.b2bdocumentv4.entity.doc_users.DocumentUsers;
import kosign.b2bdocumentv4.mapper.*;
import kosign.b2bdocumentv4.payload.BaseResponse;
import kosign.b2bdocumentv4.payload.doc_tags.*;
import kosign.b2bdocumentv4.utils.AuthHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

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
        documentTag.setUser_id(documentTag.getUser_id());

        documentTag.setDept_id(documentTag.getDept_id());
        documentTag.setCreate_date(Timestamp.valueOf(LocalDateTime.now()));
        System.out.println(documentTag);
        var newEntity = documentTagRepository.save(documentTag);
        return  BaseResponse.builder().rec(newEntity).code("200").message("success").build();
    }

    // update
    @Override
    public BaseResponse updateTag(DocTagUpdateRequest docTagUpdateRequest) {

        DocumentTag documentTag = documentTagUpdateMapper.requestToEntity(docTagUpdateRequest);

        documentTag.setId(docTagUpdateRequest.getId());
        documentTag.setStatus(1L);
        documentTag.setUser_id(docTagUpdateRequest.getUser_id());

        documentTag.setDept_id(docTagUpdateRequest.getDept_id());
        documentTag.setTitle(docTagUpdateRequest.getTitle());
        documentTag.setModified_date(docTagUpdateRequest.getCreate_date());

        System.out.println("yuth" + documentTag);
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

    // list
    @Override
    public BaseResponse getAllTagByDep_Id(Long dep_d) {
        List<DocumentTag> litDocTagByDep_ID = documentTagRepository.getTagsByDepId(dep_d);
        System.out.println("1" + litDocTagByDep_ID);

        List<DocTagResponse> responseTagList = litDocTagByDep_ID.stream().map(documentTagListMapper::entityToResponse).toList();

        return BaseResponse.builder().code("200").message("Get success").isError(false).rec(responseTagList).build();


    }




}
