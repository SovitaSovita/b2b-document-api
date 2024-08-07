package kosign.b2bdocumentv4.entity.doc_form;

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
@Table(name = "doc_items_data", schema = "stdy")
public class ItemsData {
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

    @OneToMany(mappedBy = "itemsData", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubItems> subItemsList;

    @ManyToOne
    @JoinColumn(name = "main_items_id")
    @JsonIgnore
    private MainItems mainItems;
}
