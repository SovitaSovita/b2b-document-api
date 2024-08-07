package kosign.b2bdocumentv4.entity.doc_favorite;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;


public interface DocumentFavoriteRepository extends JpaRepository<DocumentFavorite, Long> {

    // list favorite by user (not use)
    @Query(value = """
            SELECT df.id, df.article_id, df.dept_id, df.user_id, da.title,da.content_body
            FROM stdy.doc_favorite df
            INNER JOIN stdy.doc_articles da ON df.article_id = da.id
            WHERE df.user_id = :user_id
            """, nativeQuery = true)
    List<Map<Object, String>> listAllFavorite(@Param("user_id") String user_id);

    // Delete
    @Modifying
    @Transactional
    @Query(value = """
        DELETE FROM stdy.doc_favorite WHERE user_id = :user_id AND article_id = :article_id
        """, nativeQuery = true)
    int deleteFavoriteByUserIdAndArticleId(@Param("user_id") String user_id, @Param("article_id") Long article_id);

    // Check is favorite
    @Query(value = """
            SELECT * FROM stdy.doc_favorite
            WHERE user_id = :user_id and article_id = :article_id and dept_id = :dept_id
            """, nativeQuery = true)
    Map<Object, String> checkIsFavorite(@Param("user_id") String user_id,@Param("article_id") Long article_id,@Param("dept_id") Long dept_id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = """
           DELETE FROM stdy.doc_favorite WHERE article_id = :article_id
            """, nativeQuery = true)
    void deleteArticleId(@Param("article_id")Long article_id);

    @Query(value = """
            SELECT * FROM stdy.doc_favorite
            WHERE article_id = :article_id
            """, nativeQuery = true)
    List<Map<Object, String>> findArticlesId(@Param("article_id")Long article_id);

}
