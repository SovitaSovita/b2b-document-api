package kosign.b2bdocumentv4.entity.doc_favorite;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface DocumentFavoriteRepository extends JpaRepository<DocumentFavorite, Long> {

    // list favorite by user
    @Query(value = """
            SELECT df.id, df.article_id, df.dept_id, df.user_id, da.title,da.content_body
            FROM stdy.doc_favorite df
            INNER JOIN stdy.doc_articles da ON df.article_id = da.id
            WHERE df.user_id = :user_id
            """, nativeQuery = true)
    List<Map<Object, String>> listAllFavorite(@Param("user_id") String user_id);



    // Insert
    // List<DocumentFavorite> AddFavorite();

    // Delete
    @Query(value = """
            DELETE FROM stdy.doc_favorite WHERE article_id = :article_id
            """, nativeQuery = true)
    List<DocumentFavorite> deleteFavoriteByArticle_Id(Long article_id);
}
