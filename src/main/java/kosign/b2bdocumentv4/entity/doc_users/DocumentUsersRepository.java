package kosign.b2bdocumentv4.entity.doc_users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentUsersRepository extends JpaRepository<DocumentUsers, Long> {
    @Query("select du from DocumentUsers du where du.dept_id = :dept_id")
    List<DocumentUsers> getAllByDep_Id(Long dept_id);

}
