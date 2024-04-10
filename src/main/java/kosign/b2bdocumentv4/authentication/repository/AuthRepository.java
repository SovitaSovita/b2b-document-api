package kosign.b2bdocumentv4.authentication.repository;

import kosign.b2bdocumentv4.model.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AuthRepository extends JpaRepository<UserInfo, UUID> {
    UserDetails findByUsername(String username);
    Boolean existsByUsername(String username);
}
