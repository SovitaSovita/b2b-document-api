package kosign.b2bdocumentv4.service.doc_users;

import kosign.b2bdocumentv4.entity.doc_users.AuthRepository;
import kosign.b2bdocumentv4.entity.doc_users.DocumentUsers;
import kosign.b2bdocumentv4.entity.doc_users.DocumentUsersRepository;
import kosign.b2bdocumentv4.enums.ResponseMessage;
import kosign.b2bdocumentv4.enums.Role;
import kosign.b2bdocumentv4.mapper.DocumentUserMapper;
import kosign.b2bdocumentv4.payload.BaseResponse;
import kosign.b2bdocumentv4.payload.doc_users.DocUserResponse;
import kosign.b2bdocumentv4.payload.doc_users.DocUserUpdateRequest;
import kosign.b2bdocumentv4.utils.AuthHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DocUserServiceImpl implements DocUserService {

    private final AuthRepository usersRepository;
    private final DocumentUserMapper userMapper;
    private final DocumentUsersRepository repository;

    @Override
    public BaseResponse listUsers(Long dep_id) {
        List<DocumentUsers> usersList = repository.getAllByDep_Id(dep_id);
        List<DocUserResponse> responseUserList = usersList.stream()
                .map(userMapper::entityToResponse)
                .toList();
        return BaseResponse.builder()
                .code("200")
                .message("success")
                .isError(false)
                .rec(responseUserList)
                .build();
    }

    @Override
    public BaseResponse updateDocumentUser(DocUserUpdateRequest updateRequest) {
        DocumentUsers user = AuthHelper.getUser();
        if (user != null) {
            var savedUser = usersRepository.save(userMapper.updateDocUser(user, updateRequest));
            return BaseResponse.builder().rec(userMapper.entityToResponse(savedUser)).code("200").message("Success!").build();
        }
        return BaseResponse.builder().isError(true).code("403").message("User not found!").build();
    }

    @Override
    public BaseResponse deactivateUser(Long userId) {
        DocumentUsers user = AuthHelper.getUser();
        boolean isError = true;
        String message = "Only admin is authorized.";
        String code = ResponseMessage.OK.getCode();
        if (null != user && user.getRole().equals(Role.ADMIN)) {
            if (null != userId) {
                if (!userId.equals(user.getId())) user = repository.findById(userId).orElse(null); // if admin de active user account
                if (null != user) {
                    isError = false ; message = "Username " + user.getUsername() + " id["+user.getId()+"] is deactivated.";
                    if(user.getStatus().equals(0L)){
                        isError = true ; message = "Username " + user.getUsername() + " id["+user.getId()+"] is already deactivated.";
                    }else {
                        user.setStatus(0L);
                        repository.save(user);
                    }
                    return BaseResponse.builder()
                            .isError(isError)
                            .code(code)
                            .message(message)
                            .build();
                }
                else message = "User ID [" + userId + "] is not found."; code = ResponseMessage.USER_NOT_FOUND.getCode();
            }
            else message = "Please provide user [id]."; code = ResponseMessage.USER_NOT_FOUND.getCode();
        }
        return BaseResponse.builder().isError(isError).code(code).message(message).build();
    }

}
