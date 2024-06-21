package kosign.b2bdocumentv4.entity.doc_request;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestFormRepository extends JpaRepository<RequestForm, Long> {
    List<RequestForm> findByRequestTo(String userId);
}
