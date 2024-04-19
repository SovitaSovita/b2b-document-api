package kosign.b2bdocumentv4.entity.doc_file;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentFileRepository extends JpaRepository<DocumentFile ,Integer > {
    List<DocumentFile> findByFile_article_id(String id);
}
