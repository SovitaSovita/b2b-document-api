package kosign.b2bdocumentv4.entity.doc_search;

import kosign.b2bdocumentv4.payload.document_search.DocumentSearchResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Map;

public interface DocSearhAllRepository extends JpaRepository<DocSearchAll, Object> {

    //Search all condition
    @Query(value = "select id,tag_id ,tag_title ,title,content_body from stdy.docs_search(:srch_wd)" , nativeQuery = true)
    List<DocSearchAll>  findAll(@Param("srch_wd") String srch_wd);
}
