package kosign.b2bdocumentv4.entity.doc_articles;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface  DocumentArticlesRepository extends JpaRepository<DocumentArticles, Long> {

    @Query(value = """
           select * from stdy.doc_articles da where da.dept_id = :dept_id
           """,nativeQuery = true)
    List<DocumentArticles> getByDepartmentId(String dept_id);

    @Query(value = """
             SELECT a.id, a.tag_id, a.file_article_id, t.title as tag_title,a.title, a.content_body,  
             a.modified_date , a.create_date, a.created_by, a.dept_id, a.isfavorite, a.modified_by,
             a.status,b.username,b.image,a.user_id,
             f.file_id, f.file_idnt_id,f.file_nm,f.file_size,f.thum_img_path,f.img_path
             FROM stdy.doc_articles a
             left join stdy.doc_users b on a.user_id = b.id
             left join stdy.doc_tags t on t.id = a.tag_id
             left join stdy.doc_file as f on f.file_article_id = a.file_article_id
             where a.id=cast(:id as INTEGER) and a.status = 1 and t.status = 1
            """,nativeQuery = true)
    List<Map<String, Object>> findArticlesById(Long id);

    @Query(value = """
           select da.file_article_id from stdy.doc_articles da where da.id = :id
           """,nativeQuery = true)
    String getFileArticleById(Long id);


    // get menu by dept_id (Articles)
    @Query(value = """
                 SELECT a.id, a.user_id , a.tag_id , t.title as tag_title, a.title, a.content_body,
                 a.create_date , a.created_by, a.dept_id, a.file_article_id, a.isfavorite,
                 a.modified_by, a.modified_date, a.status
                 FROM stdy.doc_articles a
                 left join stdy.doc_users du on du.id = a.user_id
                 RIGHT JOIN stdy.doc_tags t ON a.tag_id = t.id
                 WHERE a.dept_id = :dept_id 
                 --and du.username  = :username
                 ORDER BY t.title
            """, nativeQuery = true)
    List<Map<String, Object>> getMenuByDept_ID(String dept_id);


    // get menu by dept_id (Tag)
    @Query(value = """
            SELECT id, title, dept_id, FROM stdy.doc_tags
            WHERE status = 1 
            """, nativeQuery = true)
    List<DocumentArticles> getTAGByDept_Id(String dept_id);



}
