package kosign.b2bdocumentv4.entity.doc_favorite;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "doc_favorite" , schema = "stdy")
public class DocumentFavorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private int id;
    @NotNull
    private int user_id;
    @NotNull
    private int article_id;
    @NotNull
    private String dep_id;
}
