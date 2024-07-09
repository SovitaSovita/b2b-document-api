package kosign.b2bdocumentv4.service.doc_request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kosign.b2bdocumentv4.dto.RequestFormDto;
import kosign.b2bdocumentv4.dto.RequestItemsDataDto;
import kosign.b2bdocumentv4.dto.RequestToDto;
import kosign.b2bdocumentv4.entity.doc_form.Form;
import kosign.b2bdocumentv4.entity.doc_form.FormRepository;
import kosign.b2bdocumentv4.entity.doc_form.ItemsData;
import kosign.b2bdocumentv4.entity.doc_request.*;
import kosign.b2bdocumentv4.exception.NotFoundExceptionClass;
import kosign.b2bdocumentv4.payload.doc_users.UserDetiailPayload;
import kosign.b2bdocumentv4.utils.ApiService;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.*;

@Service
public class RequestFormServiceImpl {
    private final RequestFormRepository requestFormRepository;
    private final FormRepository formRepository;
    private final ApiService apiService;
    private static final SecureRandom random = new SecureRandom();

    private static long generateRequestId() {
        return random.nextInt(1_000_000);  // Generates a random number between 0 and 999,999
    }


    public RequestFormServiceImpl(FormRepository formRepository, RequestFormRepository requestFormRepository, ApiService apiService) {
        this.requestFormRepository = requestFormRepository;
        this.formRepository = formRepository;
        this.apiService = apiService;
    }

    public RequestFormDto sendFormRequest(RequestFormDto requestForm) throws JsonProcessingException {
        Form formExist = formRepository.findById(requestForm.getFormId())
                .orElseThrow(() -> new NotFoundExceptionClass("Form not found with id: " + requestForm.getFormId()));


        Long requestId = generateRequestId();

        Integer index = 0;
        for (RequestToDto recipient : requestForm.getRequestTo()) {
            RequestForm newRequestForm = new RequestForm();
            newRequestForm.setFormId(formExist.getId());
            newRequestForm.setFormName(formExist.getFormName());
            newRequestForm.setClassification(formExist.getClassification());
            newRequestForm.setFormContent(requestForm.getFormContent());

            //set user info who Request
            newRequestForm.setRequestFrom(requestForm.getWhoRequest());
            newRequestForm.setFromCompany(requestForm.getWhoRequestCompany());
            newRequestForm.setFromDepartment(getUserDetailFromBizLogin(requestForm.getWhoRequest(), requestForm.getWhoRequestCompany()).getDepartment());

            //set user info who Request To
            UserDetiailPayload userDetiailPayload = getUserDetailFromBizLogin(recipient.getRequestTo(), recipient.getRequestToCompany());
            newRequestForm.setRequestTo(userDetiailPayload.getUsername());
            newRequestForm.setToDepartment(userDetiailPayload.getDepartment());
            newRequestForm.setToCompany(userDetiailPayload.getCompany());

            newRequestForm.setRequestId(requestId);
            newRequestForm.setRequestDate(requestForm.getRequestDate());
            newRequestForm.setReqOrder(index);

            if (index == 0) {
                newRequestForm.setRequestStatus(RqStatus.PENDING);
            } else {
                newRequestForm.setRequestStatus(RqStatus.HOLD);
            }
            List<RequestItemsData> itemsDataList = new ArrayList<>();
            int countFormItems = formExist.getItemsData().size();
            int countInputItems = requestForm.getRequestItemsData().size();

//            System.out.println("[countItems][countInputItems] >> " + countInputItems + " : " + countItems);

            //set input items data
            if (countFormItems == countInputItems) {
                for (int i = 0; i < countFormItems; i++) {
                    RequestItemsDataDto itemsData = requestForm.getRequestItemsData().get(i);
                    ItemsData formItemsData = formExist.getItemsData().get(i);

                    RequestItemsData data = new RequestItemsData();
                    data.setInputValue(itemsData.getInputValue());

                    if(Objects.equals(formItemsData.getInputType(), "select")){
                        data.setInputValue(formItemsData.getInputValue());
                        List<String> selectValues = List.of(itemsData.getInputValue().split(","));
                        if((itemsData.getSelectIndex()+1) <= selectValues.size()){
                            data.setSelectIndex(itemsData.getSelectIndex());
                        }
                        else {
                            throw new IllegalArgumentException("Index larger than Size of Select values");
                        }

                    }

                    data.setItemName(formItemsData.getItemName());
                    data.setInputType(formItemsData.getInputType());
                    data.setSelected(formItemsData.isSelected());
                    data.setInputRequire(formItemsData.getInputRequire());
                    data.setRequestForm(newRequestForm);
                    itemsDataList.add(data);
                    newRequestForm.setRequestItemsData(itemsDataList);
                }
            } else {
                throw new IllegalArgumentException("We found itemsData in Form [" + countFormItems + "], But your input provided [" + countInputItems + "]");
            }
            requestFormRepository.save(newRequestForm);
            index++;
        }
        return requestForm;
    }

    public UserDetiailPayload getUserDetailFromBizLogin(String userId, String company) throws JsonProcessingException {
        String json = apiService.getUserDetails(userId, company).block();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(json);
        JsonNode payloadNode = jsonNode.get("payload");
        String username = payloadNode.get("username").asText();
        String clph_NO = payloadNode.get("clph_NO").asText();
        String dvsn_CD = payloadNode.get("dvsn_CD").asText();
        String dvsn_NM = payloadNode.get("dvsn_NM").asText();
        String jbcl_NM = payloadNode.get("jbcl_NM").asText();
        String eml = payloadNode.get("eml").asText();
        String use_INTT_ID = payloadNode.get("use_INTT_ID").asText();
        String flnm = payloadNode.get("flnm").asText();
        String prfl_PHTG = payloadNode.get("prfl_PHTG").asText();

        UserDetiailPayload paylaod = new UserDetiailPayload();
        paylaod.setDepartment(dvsn_NM);
        paylaod.setDepartmentId(dvsn_CD);
        paylaod.setPosition(jbcl_NM);
        paylaod.setPhoneNumber(clph_NO);
        paylaod.setUsername(username);
        paylaod.setEmail(eml);
        paylaod.setCompany(use_INTT_ID);
        paylaod.setFullName(flnm);
        paylaod.setProfile(prfl_PHTG);

        return paylaod;
    }

    public List<RequestForm> getByUserId(GetByUserRequest request) {

        if (request.getProposer().isBlank() && request.getRecipient().isBlank()) {
            throw new IllegalArgumentException("Recipient or Proposer must only one with data");
        }

        if (Objects.isNull(request.getReqStatus())) {
            if (request.getProposer().isBlank()) {
                return requestFormRepository.findByRequestTo(request.getRecipient());
            } else if (request.getRecipient().isBlank()) {
                return requestFormRepository.findByRequestFrom(request.getProposer());
            } else {
                throw new IllegalArgumentException("Recipient or Proposer must only one with data");
            }
        } else {

            if (request.getProposer().isBlank()) {
                return requestFormRepository.findByRequestToAndRequestStatus(request.getRecipient(), RqStatus.valueOf(request.getReqStatus()));
            } else if (request.getRecipient() == null || request.getRecipient().isBlank()) {
                return requestFormRepository.findByRequestFromAndRequestStatus(request.getProposer(), RqStatus.valueOf(request.getReqStatus()));
            } else {
                throw new IllegalArgumentException("Recipient or Proposer must only one with data");
            }
        }
    }

    public RequestForm updateRequestById(Long reqId) {
        return null;
    }

    public RequestForm getDetailById(Long id) {
        return requestFormRepository.findById(id).orElseThrow(() -> new NotFoundExceptionClass("Request with ID : " + id + " Not Found."));
    }

    public void deleteById(Long id) {

        RequestForm existForm = getDetailById(id);

        if (existForm != null) {
            requestFormRepository.deleteById(id);
        } else {
            throw new NotFoundExceptionClass("Request with ID : " + id + " IS NULL.");
        }
    }

    public List<RequestForm> getApprovedById(Long requestId) {
        List<RequestForm> requests = requestFormRepository.findByRequestId(requestId);

        if (requests == null) {
            throw new NotFoundExceptionClass("Request with ID : " + requestId + " Not Found.");
        }

        for (RequestForm request : requests) {
            if (request.getRequestStatus() != RqStatus.APPROVED) {
                return null;
            }
        }
        return requests;
    }

    public List<RequestForm> getListApproved(String userId) {
        List<RequestForm> newList = new ArrayList<>();
        Map<Long, RequestForm> lastRequestForms = new HashMap<>();

        List<RequestForm> requestList = requestFormRepository.findByRequestFrom(userId);

        if(requestList.isEmpty()){
            throw new NotFoundExceptionClass("Request With UserID : "+ userId +" Not Found.");
        }
        for (RequestForm req : requestList) {
            Long requestId = req.getRequestId();
            lastRequestForms.put(requestId, req);
        }

        for (Map.Entry<Long, RequestForm> entry : lastRequestForms.entrySet()) {
            if (entry.getValue().getRequestStatus() == RqStatus.APPROVED) {
                newList.add(entry.getValue());
            }
        }
        return newList;
    }

    public RequestForm approveRequest(Long id) {

        RequestForm request = requestFormRepository.findById(id)
                .orElseThrow(() -> new NotFoundExceptionClass("Request With ID : " + id + " Not Found."));

        if(request.getRequestStatus() == RqStatus.APPROVED){
            throw new IllegalArgumentException("Request is Already " + request.getRequestStatus());
        }
        else if(request.getRequestStatus() == RqStatus.HOLD){
            throw new IllegalArgumentException("Request is on " + request.getRequestStatus());
        }

        List<RequestForm> allRequests = requestFormRepository.findByRequestId(request.getRequestId());
        List<RequestForm> sortedRequests = allRequests.stream()
                .sorted(Comparator.comparingInt(RequestForm::getReqOrder))
                .toList();


        for(int i=0; i<=sortedRequests.size(); i++){
            RequestForm req = sortedRequests.get(i);
                if(req.getRequestStatus() == RqStatus.PENDING){
                    req.setRequestStatus(RqStatus.APPROVED);

                    if(sortedRequests.size()-1 > req.getReqOrder()){
                        sortedRequests.get(i + 1).setRequestStatus(RqStatus.PENDING);
                    }
                    req.setApproveDate(new Timestamp(System.currentTimeMillis()));
                    requestFormRepository.save(req);
                    return request;
                }
        }

        return null;
    }
}
