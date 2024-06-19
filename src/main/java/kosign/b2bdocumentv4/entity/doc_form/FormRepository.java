package kosign.b2bdocumentv4.entity.doc_form;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FormRepository extends JpaRepository<Form, Long> {
    List<Form> findAllByStatusAndUsername(int status, String username);
    List<Form> findAllByStatus(int status);
}
