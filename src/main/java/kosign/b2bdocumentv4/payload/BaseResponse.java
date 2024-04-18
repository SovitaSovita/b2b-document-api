package kosign.b2bdocumentv4.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import kosign.b2bdocumentv4.enums.IResponseMessage;
import kosign.b2bdocumentv4.enums.ResponseMessage;
import kosign.b2bdocumentv4.payload.page.Page;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BaseResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Page page;
    @JsonIgnore
    private org.springframework.data.domain.Page<?> pageable;
    private Object rec;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String inqCnt;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object inqRec;
    private String message;
    private String code;
    @JsonIgnore
    @Builder.Default
    private IResponseMessage responseMessage = ResponseMessage.OK;
    @Builder.Default
    private boolean isError = false;
    @JsonIgnore
    private org.springframework.data.domain.Page<?> rawData;

    public String getMessage() {
        if(this.message != null)
            return this.message;
        if (this.responseMessage != null)
            return this.responseMessage.getMessage();
        return null;
    }

    public String getCode() {
        if(this.code != null)
            return this.code;
        if (this.responseMessage != null)
            return this.responseMessage.getCode();
        return null;
    }

    public Object getRec() {
        if(this.rawData != null)
            return this.rawData.getContent();
        return rec;
    }

    public Page getPage() {
        if(this.rawData != null)
            return Page.builder()
                    .page(this.rawData.getNumber())
                    .limit(this.rawData.getSize())
                    .totalCount((int) this.rawData.getTotalElements())
                    .totalPages(this.rawData.getTotalPages())
                    .build();
        if (this.pageable != null)
            return Page.builder()
                    .page(this.pageable.getNumber())
                    .limit(this.pageable.getSize())
                    .totalCount((int)this.pageable.getTotalElements())
                    .totalPages(this.pageable.getTotalPages())
                    .build();
        return page;
    }
}
