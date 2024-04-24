package kosign.b2bdocumentv4.entity.doc_articles;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentArticlesRepository extends JpaRepository<DocumentArticles, Long> {

    @Query(value = """
           select * from stdy.doc_articles da where da.dept_id = :dept_id
           """,nativeQuery = true)
    List<DocumentArticles> getByDepartmentId(String dept_id);

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


    // get menu by dept_id (Articles)
    @Query(value = """
            SELECT a.id, a.tag_id, a.title, a.content_body, a.create_date, a.created_by, a.dept_id, a.file_article_id, a.isfavorite, a.modified_by, a.modified_date, a.status, a.user_id
            FROM stdy.doc_articles a
            RIGHT JOIN stdy.doc_tags t ON a.tag_id = t.id
            WHERE a.status = 1 AND t.status = 1 AND a.dept_id = :dept_id
            ORDER BY a.title
            """, nativeQuery = true)
    List<DocumentArticles> getMenuByDept_ID(String dept_id);


    // get menu by dept_id (Tag)
    @Query(value = """
            SELECT id, title, dept_id, FROM stdy.doc_tags
            WHERE status = 1 
            """, nativeQuery = true)
    List<DocumentArticles> getTAGByDept_Id(String dept_id);



}
