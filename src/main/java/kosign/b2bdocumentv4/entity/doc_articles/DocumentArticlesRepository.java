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
    List<DocumentArticles> getByDepartmentId(int dept_id);

    @Query(value = """
                        SELECT a.id, a.tag_id, a.file_article_id, t.title as tag_title,a.title, a.content_body,
                        a.modified_date , a.create_date, a.created_by, a.dept_id, a.modified_by,
                        a.status,a.user_id
                        FROM stdy.doc_articles a
                        left join stdy.doc_tags t on t.id = a.tag_id
                        where a.id=cast(:id as INTEGER)
            """,nativeQuery = true)
    Map<String, Object> findArticlesById(Long id);


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


}
