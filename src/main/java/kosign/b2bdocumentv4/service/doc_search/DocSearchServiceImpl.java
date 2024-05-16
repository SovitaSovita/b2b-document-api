package kosign.b2bdocumentv4.service.doc_search;

import kosign.b2bdocumentv4.entity.doc_search.DocSearch;
import kosign.b2bdocumentv4.entity.doc_search.DocSearchRepository;
import kosign.b2bdocumentv4.payload.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocSearchServiceImpl implements DocSearchService {

    private final DocSearchRepository repository;
    private final ModelMapper modelMapper;

    @Override
    public BaseResponse searchDoc(String title){

        List<DocSearch> getSearch = repository.findByTitleContaining(title);

        if (getSearch.isEmpty()) {
            return BaseResponse.builder()
                    .code("404")
                    .message("No results found")
                    .isError(true)
                    .rec(Collections.emptyList())
                    .build();
        } else {
            return BaseResponse.builder()
                    .code("200")
                    .message("Success")
                    .isError(false)
                    .rec(getSearch)
                    .build();
        }
    }

}
