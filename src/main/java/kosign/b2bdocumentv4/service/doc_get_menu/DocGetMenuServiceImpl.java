//package kosign.b2bdocumentv4.service.doc_get_menu;
//
//
//import kosign.b2bdocumentv4.entity.doc_get_menu.GetMenu;
//import kosign.b2bdocumentv4.entity.doc_get_menu.GetMenuRepository;
//import kosign.b2bdocumentv4.entity.doc_tags.DocumentTag;
//import kosign.b2bdocumentv4.entity.doc_tags.DocumentTagRepository;
//import kosign.b2bdocumentv4.payload.BaseResponse;
//import kosign.b2bdocumentv4.payload.document_articles.GetMenuResponse;
//import lombok.AllArgsConstructor;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
////@RequiredArgsConstructor
//@AllArgsConstructor
//public class DocGetMenuServiceImpl implements DocGetMenuService{
//
//    private final GetMenuRepository repository;
//    private final DocumentTagRepository documentTagRepository;
//
//    // list all menu
//    @Override
//    public BaseResponse getMenuList() {
//        List<GetMenu> list = repository.findAll();
//        return BaseResponse.builder().rec(list).code("200").message("successfully fetch users").build();
//    }
//
//    // list menu by dept_id
//    @Override
//    public BaseResponse getMenuListByDept_id(String dept_id) {
//        List<GetMenu> r = repository.getMenuByDept_ID(dept_id);
//        List<DocumentTag> rr = documentTagRepository.getTagsByDepId(Long.parseLong(dept_id));
//
//        GetMenuResponse response = new GetMenuResponse();
//        // response.setArticlesList(rr);
//
//
//        return BaseResponse.builder().build();
//    }
//
//
//
//
//}
