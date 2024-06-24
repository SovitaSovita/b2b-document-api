package kosign.b2bdocumentv4.entity.doc_form;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ItemsRepository extends JpaRepository<ItemsData, Long>{

}
