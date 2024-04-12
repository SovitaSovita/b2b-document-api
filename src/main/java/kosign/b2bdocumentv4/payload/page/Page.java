package kosign.b2bdocumentv4.payload.page;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Page {
    private int page;
    private int limit;
    private int totalCount;
    private int totalPages;
    @JsonIgnore
    private int offset;
    public Page() {
        this(0,10,0,0);
    }

    public Page(int page, int limit) {
        this.page = page;
        this.limit = limit;
    }

    public Page(int page, int limit, int totalCount, int totalPages){
        this.page= page;
        this.limit= limit;
        this.totalCount=totalCount;
        this.totalPages=totalPages;
    }

    public void setPage(int currentPage) {
        this.page = Math.max(currentPage, 1);
    }

    public int getTotalPages() {
        return (int )Math.ceil((double)this.totalCount/limit);
    }

    public int getOffset() {
        return (this.page)*this.limit;
    }
}
