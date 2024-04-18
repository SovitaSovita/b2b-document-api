package kosign.b2bdocumentv4.entity.doc_users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentUsersRepository extends JpaRepository<DocumentUsers, Long> {
    List<DocumentUsers> findAll();
}
