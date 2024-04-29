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
    public BaseResponse listUsers(Long dept_id) {
        List<DocumentUsers> usersList = repository.getAllByDep_Id(dept_id);
;
        if (usersList.isEmpty()) {
            return BaseResponse.builder()
                    .code("404")
                    .message("No users found for department ID: " + dept_id)
                    .isError(false)
                    .build();
        }
        List<DocUserResponse> responseUserList = usersList.stream()
                .map(userMapper::toResponse)
                .toList();

        return BaseResponse.builder()
                .code("200")
                .message("success")
                .isError(false)
                .rec(usersList)
                .build();
    }

    @Override
    public BaseResponse updateDocumentUser(DocUserUpdateRequest updateRequest) {
        DocumentUsers user = AuthHelper.getUser();
        if(!user.getRole().equals(Role.ADMIN)){
            BaseResponse.builder().isError(true).code("401").message("Only admin can update!").build();
        }
        // find user
        DocumentUsers userToUpdate = repository.findByIdAndDept_id(updateRequest.getId(),user.getDept_id()).orElse(null);
        if(null == userToUpdate){
            return BaseResponse.builder()
                    .isError(true)
                    .code("404")
                    .message("User id["+updateRequest.getId()+"] not found in department id["+user.getDept_id()+"]!")
                    .build();
        }

        var savedUser = usersRepository.save(userMapper.update(user, updateRequest));
        return BaseResponse.builder().rec(userMapper.toResponse(savedUser)).code("200").message("Success!").build();

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
