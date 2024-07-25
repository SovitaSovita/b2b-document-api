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
    @Query(value = "select coalesce(id ,'1') as id,tag_id ,tag_title ,coalesce(title,'') as title,coalesce(content_body,'')as content_body from stdy.docs_search(:srch_wd,:dept_id,:user_name)" , nativeQuery = true)
    List<DocSearchAll>  findAll(@Param("srch_wd") String srch_wd, @Param("dept_id") Integer dept_id, @Param("user_name") String user_name);
}
