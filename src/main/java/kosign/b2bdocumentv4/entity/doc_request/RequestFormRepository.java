package kosign.b2bdocumentv4.entity.doc_request;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestFormRepository extends JpaRepository<RequestForm, Long> {
    List<RequestForm> findByRequestToAndRequestStatus(String recipient, RqStatus status);
    List<RequestForm> findByRequestFromAndRequestStatus(String proposer, RqStatus status);
    List<RequestForm> findByRequestTo(String recipient);
    List<RequestForm> findByRequestFrom(String proposer);
    List<RequestForm> findByRequestId(Long id);
    RequestForm findByRequestIdAndId(Long reqId , Long id);

}
