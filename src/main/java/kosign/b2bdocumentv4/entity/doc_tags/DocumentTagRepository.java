package kosign.b2bdocumentv4.entity.doc_tags;

import kosign.b2bdocumentv4.entity.doc_articles.DocumentArticles;
import kosign.b2bdocumentv4.entity.doc_users.DocumentUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DocumentTagRepository extends JpaRepository<DocumentTag, Long> {

    // Tag
    @Query(value = """
            SELECT id, title, create_date, modified_date, user_name, status, dept_id FROM stdy.doc_tags WHERE dept_id = :dept_id
            """, nativeQuery = true)
    List<DocumentTag> getTagsByDepId(Long dept_id);

    // Articles
    @Query(value = """
            SELECT a.id, a.tag_id, a.title, a.create_date, a.dept_id, a.modified_date, a.status, a.user_name FROM stdy.doc_articles a right join stdy.doc_tags t on a.tag_id = t.id  where a.status=1 and t.status = 1 order by a.title
            """, nativeQuery = true)
    List<DocumentTag> getArticles();


    // New API Tag
    @Query(value = """
            SELECT id,title,dept_id,create_date,modified_date,status,user_name
            FROM stdy.doc_tags
            where status = CAST(:status AS INT) 
            and (:dept_id IS NULL OR dept_id = :dept_id)  
            and (:userName IS NULL OR user_name = :userName)
            order by
            title
            """, nativeQuery = true)
    List<DocumentTag> getDocumentTag(@Param("dept_id") Long dept_id, @Param("status") Long status, @Param("userName") String userName);

    // New API Articles
    @Query(value = """
            SELECT a.id, a.tag_id, a.title,a.create_date,a.dept_id,a.modified_date,a.status,t.user_name
            FROM stdy.doc_articles a
            right join stdy.doc_tags t
            on a.tag_id = t.id
            where a.status = CAST(:status AS INT) 
            and (:dept_id IS NULL OR a.dept_id = :dept_id)  
            and (:userName IS NULL OR t.user_name = :userName)
            order by title
            """, nativeQuery = true)
    List<Map<Object, String>> getDocumentArticleList(@Param("dept_id") Long dept_id, @Param("status") Long status,  @Param("userName") String userName);


    @Query(value = """
            select id from stdy.doc_articles da where tag_id = :tag_id
            """, nativeQuery = true)

  List<Long> findArticlesId(Long tag_id);

}
