package kosign.b2bdocumentv4.entity.doc_users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentUsersRepository extends JpaRepository<DocumentUsers, Long> {
//    @Query("select du from DocumentUsers du where du.dept_id = :dept_id")
//    @Query(value = """
//            select id,dept_id,image,password,provider,role,status,username
//            from stdy.doc_users
//            where dept_id=:dept_id
//            """, nativeQuery = true)
    // test new
    @Query(value = """
            SELECT
                    du.id,
                    du.username,
                    du.password,
                    du.status,
                    du.role,
                    du.image,
                    du.dept_id,
                    dd.dep_name
                FROM
                    stdy.doc_users du
                left join
                    stdy.doc_department dd
                        on dd.dep_id=:dep_id
            """, nativeQuery = true)
    List<DocumentUsers> getAllByDep_Id(Long dep_id);

    @Query("SELECT u from DocumentUsers u WHERE u.id = :id AND u.dept_id = :dept_id")
    Optional<DocumentUsers> findByIdAndDept_id(Long id, Long dept_id);

}
