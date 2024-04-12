package kosign.b2bdocumentv4.entity.docUsers;

import kosign.b2bdocumentv4.entity.docUsers.DocUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocUsersRepository extends JpaRepository<DocUsers, Long> {
    List<DocUsers> findAll();
}
