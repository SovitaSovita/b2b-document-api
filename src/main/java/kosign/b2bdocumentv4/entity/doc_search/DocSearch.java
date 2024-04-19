package kosign.b2bdocumentv4.entity.doc_search;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import kosign.b2bdocumentv4.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "doc_articles", schema = "stdy")
public class DocSearch extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String title;
    @NotNull
    private int tag_id;


}
