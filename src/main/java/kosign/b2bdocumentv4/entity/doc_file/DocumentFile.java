package kosign.b2bdocumentv4.entity.doc_file;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "doc_file" , schema = "stdy")
public class DocumentFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer file_id;
    @NotNull
    private String file_idnt_id;
    private String file_nm;
    private String thum_img_path;
    private String img_path;
    private String file_size;
    @NotNull
    private String file_article_id;
    private int status = 1;
}
