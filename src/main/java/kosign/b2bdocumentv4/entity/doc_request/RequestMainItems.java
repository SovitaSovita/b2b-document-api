package kosign.b2bdocumentv4.entity.doc_request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "doc_request_main_items" , schema = "stdy")

public class RequestMainItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private String itemName;
    private String type;
    private boolean require;
    private String value;

    @OneToMany(mappedBy = "requestMainItems", cascade = CascadeType.ALL)
    private List<RequestItemsData> requestItemsData;

    @ManyToOne
    @JoinColumn(name = "request_id")
    @JsonIgnore
    private RequestForm requestForm;
}
