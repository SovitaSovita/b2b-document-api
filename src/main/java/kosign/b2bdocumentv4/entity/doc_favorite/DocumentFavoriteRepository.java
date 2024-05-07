package kosign.b2bdocumentv4.entity.doc_favorite;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DocumentFavoriteRepository extends JpaRepository<DocumentFavorite, Long> {

    // list favorite by user
    @Query(value = """
            SELECT
                id, user_id, article_id,dept_id
            FROM
                stdy.doc_favorite
            WHERE
                user_id = :user_id
            """, nativeQuery = true)
    List<DocumentFavorite> listAllFavorite(Long user_id);


    // Insert
    // List<DocumentFavorite> AddFavorite();

    // Delete
    @Query(value = """
            DELETE FROM stdy.doc_favorite WHERE article_id = :article_id
            """, nativeQuery = true)
    List<DocumentFavorite> deleteFavoriteByArticle_Id(Long article_id);
}
