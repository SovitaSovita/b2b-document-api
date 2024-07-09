package kosign.b2bdocumentv4.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestToDto {
    private String requestTo;
    private String requestToCompany;
}
