package kosign.b2bdocumentv4.service.doc_save_tag;

import kosign.b2bdocumentv4.payload.BaseResponse;
import kosign.b2bdocumentv4.payload.doc_tags.DocTagDeleteRequest;
import kosign.b2bdocumentv4.payload.doc_tags.DocTagRequest;
import kosign.b2bdocumentv4.payload.doc_tags.DocTagUpdateRequest;
import org.springframework.http.ResponseEntity;

public interface SaveDocumentTagService {

    // Save
    BaseResponse saveTag(DocTagRequest docTagRequest);
    // Update
    BaseResponse updateTag(DocTagUpdateRequest docTagUpdateRequest);
    // Delete
    ResponseEntity<?> deleteTag(Long tag_id);
    // Get
    BaseResponse getTag(Long dept_id, String username);
    // Call 2 repo ( not use )
    BaseResponse listTagAndArticle(Long dept_id, Long status, String username);

}
