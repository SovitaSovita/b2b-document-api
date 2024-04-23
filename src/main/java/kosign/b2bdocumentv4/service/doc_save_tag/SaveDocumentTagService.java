package kosign.b2bdocumentv4.service.doc_save_tag;

import kosign.b2bdocumentv4.entity.doc_tags.DocumentTag;
import kosign.b2bdocumentv4.payload.BaseResponse;
import kosign.b2bdocumentv4.payload.doc_tags.DocTagDeleteRequest;
import kosign.b2bdocumentv4.payload.doc_tags.DocTagGetAllByDepIdRequest;
import kosign.b2bdocumentv4.payload.doc_tags.DocTagRequest;
import kosign.b2bdocumentv4.payload.doc_tags.DocTagUpdateRequest;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SaveDocumentTagService {

    BaseResponse saveTag(DocTagRequest docTagRequest);
    BaseResponse updateTag(DocTagUpdateRequest docTagUpdateRequest);
    BaseResponse deleteTag(DocTagDeleteRequest docTagDeleteRequest);
    // BaseResponse listDocumentTag(DocTagGetAllByDepIdRequest docTagGetAllByDepIdRequest);


    BaseResponse getAllTagByDep_Id(Long dept_id);
    // List<DocumentTag> getAllTagByDep_Id(Long dept_id);

}
