package kosign.b2bdocumentv4.entity.doc_search;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocSearchRepository extends JpaRepository<DocSearch, Long> {

    // old
    // @Query(value = "SELECT * FROM stdy.doc_articles a WHERE a.title LIKE %:title%", nativeQuery = true)
    // List<DocSearch> findByTitleContaining(@Param("title") String title);

    // custom new
    @Query(value = "SELECT * FROM stdy.doc_articles a WHERE a.title LIKE '%' || :title || '%'", nativeQuery = true)
    List<DocSearch> findByTitleContaining(@Param("title") String title);

    // Search all case
    // List<DocSearch>
}
