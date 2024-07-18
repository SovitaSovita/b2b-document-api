package kosign.b2bdocumentv4.entity.doc_request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "doc_request_data", schema = "stdy")
public class RequestItemsData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    private String itemName;
    private Boolean inputRequire;
    private String inputType;
    private String inputValue;
    private String description;
    private boolean isSelected;

    @ManyToOne
    @JoinColumn(name = "main_items_id")
    @JsonIgnore
    private RequestMainItems requestMainItems;

    @Override
    public String toString() {
        return "RequestItemsData{" +
                "id=" + id +
                ", itemName='" + itemName + '\'' +
                ", inputRequire=" + inputRequire +
                ", inputType='" + inputType + '\'' +
                ", inputValue='" + inputValue + '\'' +
                ", isSelected=" + isSelected +
                '}';
    }


}
