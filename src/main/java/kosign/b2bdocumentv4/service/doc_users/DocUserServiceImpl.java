package kosign.b2bdocumentv4.service.doc_users;

import kosign.b2bdocumentv4.entity.docUsers.DocUsersRepository;
import kosign.b2bdocumentv4.entity.docUsers.DocUsers;
import kosign.b2bdocumentv4.payload.BaseResponse;
import kosign.b2bdocumentv4.payload.docUser.DocUserResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocUserServiceImpl implements DocUserService{

    private final DocUsersRepository usersRepository;
    private final ModelMapper modelMapper;

    @Override
    public BaseResponse listUsers(Long dep_id) {
        List<DocUsers> usersList = usersRepository.findAll();
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
