package kosign.b2bdocumentv4.entity.doc_articles;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import kosign.b2bdocumentv4.entity.BaseEntity;
import kosign.b2bdocumentv4.entity.doc_file.DocumentFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "doc_articles", schema = "stdy")
public class DocumentArticles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private Long tag_id;
    private String title;
    private String content_body;

    @NotNull
    private String user_id;

    @OneToMany(mappedBy = "documentArticles", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArticleUsers> articleUsers;

    @OneToMany(mappedBy = "documentArticles", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DocumentFile> documentFiles;

    @NotNull
    private Long status;
    @NotNull
    private String file_article_id;
    private Long dept_id;
    @NotNull
    private String isfavorite;
    private String modifiedBy;
    private Timestamp modified_date;

    private String createdBy;
    private Timestamp create_date;

    @Override
    public String toString() {
        return "DocumentArticles{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", documentFiles=" + (documentFiles != null ? "size=" + documentFiles.size() : "null") +
                ", articleUsers=" + (articleUsers != null ? "size=" + articleUsers.size() : "null") +
                '}';
    }

}

