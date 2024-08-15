package kosign.b2bdocumentv4.dto;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ListRequestReq {
    private String userId;
    private String company;
    private String status;
    private LocalDate startDate = LocalDate.now().minusWeeks(1);
    private LocalDate endDate = LocalDate.now();
}
