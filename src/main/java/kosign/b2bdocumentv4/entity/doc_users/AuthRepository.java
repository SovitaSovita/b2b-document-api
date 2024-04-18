package kosign.b2bdocumentv4.entity.doc_users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<DocumentUsers, Long> {
    DocumentUsers findByUsername(String username);
    Boolean existsByUsername(String username);
}
