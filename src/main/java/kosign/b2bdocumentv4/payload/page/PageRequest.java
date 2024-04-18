package kosign.b2bdocumentv4.payload.page;

import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class PageRequest {
    private int page = 1;
    private int limit = 10;
    private String sort = "id";
}
