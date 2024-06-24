package kosign.b2bdocumentv4.entity.doc_form;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface FormRepository extends JpaRepository<Form, Long> {
    List<Form> findAllByStatusAndUsername(int status, String username);
    List<Form> findAllByStatus(int status);
    List<Form> findAllByUsername(String username);

}