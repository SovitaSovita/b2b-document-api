package kosign.b2bdocumentv4.service.doc_save_tag;

import kosign.b2bdocumentv4.payload.BaseResponse;
import kosign.b2bdocumentv4.payload.doc_tags.DocTagRequest;

public interface SaveDocumentTagService {

    BaseResponse saveTag(DocTagRequest docTagRequest);
}
