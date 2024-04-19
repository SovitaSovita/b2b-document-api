package kosign.b2bdocumentv4.service.doc_save_tag;


import kosign.b2bdocumentv4.entity.doc_tags.DocumentTag;
import kosign.b2bdocumentv4.entity.doc_tags.DocumentTagRepository;
import kosign.b2bdocumentv4.mapper.DocumentTagMapper;
import kosign.b2bdocumentv4.mapper.DocumentUserMapper;
import kosign.b2bdocumentv4.payload.BaseResponse;
import kosign.b2bdocumentv4.payload.doc_tags.DocTagRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SaveDocumentTagImpl  implements SaveDocumentTagService {

    private final DocumentTagRepository documentTagRepository;
    private final DocumentTagMapper documentTagMapper;

    @Override
    public BaseResponse saveTag(DocTagRequest docTagRequest) {
        DocumentTag documentTag = documentTagMapper.requestToEntity(docTagRequest);

        // documentTag.setId(documentTag.getId());
        documentTag.setStatus(1L);
        // documentTag.setId(docTagRequest.getUser_id());
        documentTag.setUser_id(docTagRequest.getUser_id());
        documentTag.setDep_id(docTagRequest.getDept_id());
        documentTag.setTitle(docTagRequest.getTitle());
        documentTag.setCreate_date(Timestamp.valueOf(LocalDateTime.now()));

        System.out.println(documentTag);
        var newEntity = documentTagRepository.save(documentTag);
        // return BaseResponse.builder().rec(documentTag).code("200").message("success").build();
        return  BaseResponse.builder().rec(newEntity).code("200").message("success").build();
    }
}
