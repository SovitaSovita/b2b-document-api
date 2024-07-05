package kosign.b2bdocumentv4.service.doc_search;

import kosign.b2bdocumentv4.payload.BaseResponse;

public interface DocSearchService {
    BaseResponse searchDoc (String title);
    BaseResponse searchAll(String srch_wd );

    BaseResponse searchAll(String srch_wd, int dept_id);
}
