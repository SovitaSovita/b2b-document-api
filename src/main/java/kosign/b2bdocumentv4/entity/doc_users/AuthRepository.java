package kosign.b2bdocumentv4.entity.doc_users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<DocumentUsers, Long> {
    DocumentUsers findByUsername(String username);
    Boolean existsByUsername(String username);

    @Query(value = "select provider from b2bdoc_db.stdy.doc_users where username = :userId", nativeQuery = true)
    String getByProvider(String userId);
}
