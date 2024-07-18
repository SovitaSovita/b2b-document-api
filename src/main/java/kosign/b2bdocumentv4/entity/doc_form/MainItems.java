package kosign.b2bdocumentv4.entity.doc_form;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "main_items", schema = "stdy")
public class MainItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String itemName;
    private String type;
    private String value = "";
    private Boolean require;

    @OneToMany(mappedBy = "mainItems", cascade = CascadeType.ALL)
    private List<ItemsData> itemsData;

    @ManyToOne
    @JoinColumn(name = "form_id")
    @JsonIgnore
    private Form form;
}
