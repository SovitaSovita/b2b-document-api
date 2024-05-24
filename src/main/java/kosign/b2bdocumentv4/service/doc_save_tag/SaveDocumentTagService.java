package kosign.b2bdocumentv4.service.doc_save_tag;

import kosign.b2bdocumentv4.payload.BaseResponse;
import kosign.b2bdocumentv4.payload.doc_tags.DocTagDeleteRequest;
import kosign.b2bdocumentv4.payload.doc_tags.DocTagRequest;
import kosign.b2bdocumentv4.payload.doc_tags.DocTagUpdateRequest;

public interface SaveDocumentTagService {

    // Save
    BaseResponse saveTag(DocTagRequest docTagRequest);
    // Update
    BaseResponse updateTag(DocTagUpdateRequest docTagUpdateRequest);
    // Delete
    BaseResponse deleteTag(DocTagDeleteRequest docTagDeleteRequest);
    // Get
    BaseResponse getTag(Long dept_id, String username);
    // Call 2 repo ( not use )
    BaseResponse listTagAndArticle(Long dept_id, String status, String username);

}
