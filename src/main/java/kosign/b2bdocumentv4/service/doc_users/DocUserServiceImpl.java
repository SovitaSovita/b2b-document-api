package kosign.b2bdocumentv4.service.doc_users;

import kosign.b2bdocumentv4.entity.doc_users.AuthRepository;
import kosign.b2bdocumentv4.entity.doc_users.DocumentUsers;
import kosign.b2bdocumentv4.payload.BaseResponse;
import kosign.b2bdocumentv4.payload.doc_users.DocUserResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocUserServiceImpl implements DocUserService{

    private final AuthRepository usersRepository;
    private final ModelMapper modelMapper;

    @Override
    public BaseResponse listUsers(Long dep_id) {
        List<DocumentUsers> usersList = usersRepository.findAll();
        List<DocUserResponse> responseUserList = usersList.stream()
                .map(user->modelMapper.map(user,DocUserResponse.class))
                .toList();
        return BaseResponse.builder()
                .code("200")
                .message("success")
                .isError(false)
                .rec(responseUserList)
                .build();
    }
}
