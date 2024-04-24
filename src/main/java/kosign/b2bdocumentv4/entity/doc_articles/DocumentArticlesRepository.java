package kosign.b2bdocumentv4.entity.doc_articles;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  DocumentArticlesRepository extends JpaRepository<DocumentArticles, Long> {

    @Query(value = """
           select * from stdy.doc_articles da where da.dep_id = :dep_id
           """,nativeQuery = true)
    List<DocumentArticles> getByDepartmentId(String dep_id);

    @Query(value = """
            SELECT a.id, a.tag_id, a.file_article_id, t.title as tag_title,a.title, a.content_body,  a.modified_date , a.create_date,  a.status,b.username,b.image,a.user_id,\s
                            f.file_id,f.file_idnt_id,f.file_nm,f.file_size,f.thum_img_path,f.img_path
                            FROM doc_articles a
                            inner join doc_users b on a.user_id = b.id
                            left join doc_tags t on t.id = a.tag_id \s
                            left join doc_file as f on f.file_article_id = a.file_article_id
                            where a.id=cast($1 as INTEGER) and a.status = 1 and t.status = 1
            """,nativeQuery = true)
    List<DocumentArticles> findArticlesById(Long id);

    @Query(value = """
           select da.file_article_id from stdy.doc_articles da where da.id = :id
           """,nativeQuery = true)
    String getFileArticleById(Long id);

}
