package kosign.b2bdocumentv4.entity.doc_tags;

import kosign.b2bdocumentv4.entity.doc_users.DocumentUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentTagRepository extends JpaRepository<DocumentTag, Long> {

    // Custom query method

    @Query("select du from DocumentTag du where status = 1 and du.dept_id = :dept_id")
    // @Query(value = "SELECT id, title, create_date, modified_date, user_id, status, dept_id FROM stdy.doc_tags WHERE dept_id = :dept_id", nativeQuery = true)
    List<DocumentTag> getTagsByDepId(Long dept_id);

}
