package kosign.b2bdocumentv4.entity.doc_department;

import kosign.b2bdocumentv4.payload.doc_documents.DocDepartmentRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentDepartmentRepository extends JpaRepository<DocDepartmentTag,Long> {
    List<DocDepartmentTag> save(Long dep_id);
}
