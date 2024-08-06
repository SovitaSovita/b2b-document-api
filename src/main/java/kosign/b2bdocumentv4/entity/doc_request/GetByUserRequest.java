package kosign.b2bdocumentv4.entity.doc_request;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetByUserRequest {
    private String company;
    private String recipient;
    private String proposer;
    private String reqStatus;
    private LocalDate startDate = LocalDate.now().minusWeeks(1);
    private LocalDate endDate = LocalDate.now();
}
