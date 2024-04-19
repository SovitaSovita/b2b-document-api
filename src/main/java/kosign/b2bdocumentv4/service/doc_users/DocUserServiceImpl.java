package kosign.b2bdocumentv4.service.doc_users;

import kosign.b2bdocumentv4.entity.doc_users.AuthRepository;
import kosign.b2bdocumentv4.entity.doc_users.DocumentUsers;
import kosign.b2bdocumentv4.entity.doc_users.DocumentUsersRepository;
import kosign.b2bdocumentv4.mapper.DocumentUserMapper;
import kosign.b2bdocumentv4.payload.BaseResponse;
import kosign.b2bdocumentv4.payload.doc_users.DocUserResponse;
import kosign.b2bdocumentv4.payload.doc_users.DocUserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.text.spi.NumberFormatProvider;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocUserServiceImpl implements DocUserService{

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
    public BaseResponse updateDocumentUser(DocUserUpdateRequest updateRequest){
        if(updateRequest.getId() != null){
            DocumentUsers findUser = usersRepository.findById(updateRequest.getId()).orElse(null);
            if(null == findUser) return BaseResponse.builder().isError(true).code("403").message("could not find user with that ID").build();

            var updatedUser = usersRepository.save(userMapper.updateDocUser(findUser,updateRequest));
            return BaseResponse.builder().rec(userMapper.entityToResponse(updatedUser)).code("200").message("Save success!").build();
        }
        return BaseResponse.builder().isError(true).code("403").message("User id could not be null").build();
    }

}
