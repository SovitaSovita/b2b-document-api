package kosign.b2bdocumentv4.entity.doc_users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentUsersRepository extends JpaRepository<DocumentUsers, Long> {
    //List<DocumentUsers> findAllByDept_id();

    //@Query(" select du from DocumentUsers du where du.dept_id = :dept_id ")
    //List<DocumentUsers> findAllByDept_id(Long dept_id);

    @Query(value = "select * from stdy.doc_users du where du.dept_id = :dept_id ",nativeQuery = true)
    List<DocumentUsers> getAllByDep_Id(Long dept_id);
}
