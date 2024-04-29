package kosign.b2bdocumentv4.entity.doc_users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentUsersRepository extends JpaRepository<DocumentUsers, Long> {

    // List user by department ID
//    @Query(value = """
//            select id,dept_id,image,password,provider,role,status,username
//            from stdy.doc_users
//            where dept_id=:dept_id
//            """, nativeQuery = true)
//    List<DocumentUsers> getAllByDep_Id(Long dept_id);


    // Test
    @Query(value = """
            select u.id, u.dept_id, u.image, u.password, u.provider, u.role, u.status, u.username
            from stdy.doc_users u
            WHERE u.dept_id = :dept_id
            """, nativeQuery = true)
    List<DocumentUsers> getAllByDep_Id(Long dept_id);



    @Query("SELECT u from DocumentUsers u WHERE u.id = :id AND u.dept_id = :dept_id")
    Optional<DocumentUsers> findByIdAndDept_id(Long id, Long dept_id);

}
