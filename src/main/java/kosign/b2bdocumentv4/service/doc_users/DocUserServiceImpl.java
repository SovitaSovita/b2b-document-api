package kosign.b2bdocumentv4.service.doc_users;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import kosign.b2bdocumentv4.entity.doc_department.DocumentDepartmentRepository;
import kosign.b2bdocumentv4.entity.doc_users.AuthRepository;
import kosign.b2bdocumentv4.entity.doc_users.DocumentUsers;
import kosign.b2bdocumentv4.entity.doc_users.DocumentUsersRepository;
import kosign.b2bdocumentv4.enums.Role;
import kosign.b2bdocumentv4.exception.NotFoundExceptionClass;
import kosign.b2bdocumentv4.jwt.JwtTokenUtils;
import kosign.b2bdocumentv4.mapper.DocumentUserMapper;
import kosign.b2bdocumentv4.utils.ApiService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DocUserServiceImpl {

    private final AuthRepository usersRepository;
    private final DocumentUserMapper userMapper;
    private final DocumentUsersRepository repository;
    private final DocumentDepartmentRepository departmentRepository;
    private final ModelMapper modelMapper;
    private final ApiService apiService;
    private final JwtTokenUtils jwtTokenUtils;

    public DocumentUsers getCurrentUser(HttpServletRequest request) {
        String username = jwtTokenUtils.getUsernameFromToken(request.getHeader("Authorization").substring(7));
        DocumentUsers currentUser = repository.findByUsername(username);
        if(currentUser == null){
            saveUser(username);
            currentUser = repository.findByUsername(username);
        }
        return currentUser;
    }

    public void saveUser(String userId){
        String json = apiService.getUserDetails(userId).block();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            JsonNode payloadNode = jsonNode.get("payload");
            String username = payloadNode.get("username").asText();
            String password = payloadNode.get("password").asText();
            String clph_NO = payloadNode.get("clph_NO").asText();
            String dvsn_CD = payloadNode.get("dvsn_CD").asText();
            String dvsn_NM = payloadNode.get("dvsn_NM").asText();
            String jbcl_NM = payloadNode.get("jbcl_NM").asText();
            String eml = payloadNode.get("eml").asText();
            String flnm = payloadNode.get("flnm").asText();
            String prfl_PHTG = payloadNode.get("prfl_PHTG").asText();

            DocumentUsers documentUsers = new DocumentUsers();
            documentUsers.setUsername(username);
            documentUsers.setDept_id(Long.valueOf(dvsn_CD));
            documentUsers.setRole(Role.USER);
            documentUsers.setStatus(1L);
//            documentUsers.setPassword(password);
            documentUsers.setImage(prfl_PHTG);

            DocumentUsers existUser = repository.findByUsername(username);

            if (existUser == null) {
                repository.save(documentUsers);
            }
        }
        catch (Exception e){
            throw new NotFoundExceptionClass(e.getMessage());
        }
    }

}
