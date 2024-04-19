package kosign.b2bdocumentv4.entity.doc_articles;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentArticlesRepository extends JpaRepository<DocumentArticles, Long> {

    @Query(value = """
           select * from stdy.doc_articles da where da.dep_id = :dep_id
           """,nativeQuery = true)
    List<DocumentArticles> getByDepartmentId(String dep_id);

    @Query(value = """
           select da.file_article_id from stdy.doc_articles da where da.id = :id
           """,nativeQuery = true)
    String getFileArticleById(Long id);
}
