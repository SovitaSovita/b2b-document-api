package kosign.b2bdocumentv4.entity.doc_file;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DocumentFileRepository extends JpaRepository<DocumentFile ,Integer > {
    @Query(value = "SELECT f.file_id , f.file_article_id ,f.file_idnt_id,f.file_nm,f.file_size,f.thum_img_path,f.img_path, f.status FROM  b2bdoc_db.stdy.doc_file AS f\n" +
            "    INNER JOIN  b2bdoc_db.stdy.doc_articles AS a ON f.file_article_id = a.file_article_id\n" +
            "    WHERE a.id = :articleId AND a.status = 1 AND f.status = 1", nativeQuery = true)
    List<DocumentFile> findByArticleIdAndStatus(@Param("articleId") int articleId);
}
