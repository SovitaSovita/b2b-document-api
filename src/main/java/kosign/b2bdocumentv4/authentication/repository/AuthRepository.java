package kosign.b2bdocumentv4.authentication.repository;

import kosign.b2bdocumentv4.model.entity.DocUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<DocUsers, Long> {
    DocUsers findByUsername(String username);
    Boolean existsByUsername(String username);

}
