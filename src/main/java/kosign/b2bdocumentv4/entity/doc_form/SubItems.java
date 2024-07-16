package kosign.b2bdocumentv4.entity.doc_form;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sub_items", schema = "stdy")
public class SubItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemName;
    private boolean isItem;

    @ManyToOne
    @JoinColumn(name = "items_data_id")
    @JsonIgnore
    private ItemsData itemsData;
}