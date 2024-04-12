package kosign.b2bdocumentv4.entity.docUsers;

import kosign.b2bdocumentv4.entity.docUsers.DocUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<DocUsers, Long> {
    DocUsers findByUsername(String username);
    Boolean existsByUsername(String username);
}
