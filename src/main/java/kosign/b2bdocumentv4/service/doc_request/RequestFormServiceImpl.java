package kosign.b2bdocumentv4.service.doc_request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kosign.b2bdocumentv4.dto.*;
import kosign.b2bdocumentv4.entity.doc_form.*;
import kosign.b2bdocumentv4.entity.doc_request.*;
import kosign.b2bdocumentv4.exception.NotFoundExceptionClass;
import kosign.b2bdocumentv4.payload.doc_users.UserDetiailPayload;
import kosign.b2bdocumentv4.utils.ApiService;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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
        int index = 0;

        for (RequestToDto recipient : requestForm.getRequestTo()) {
            RequestForm newRequestForm = createRequestForm(requestForm, formExist, recipient, requestId, index);

            List<RequestMainItems> mainItemsList = createMainItemsList(requestForm, formExist, newRequestForm);

            newRequestForm.setRequestMainItems(mainItemsList);
            requestFormRepository.save(newRequestForm);
            index++;
        }

        return requestForm;
    }

    private RequestForm createRequestForm(RequestFormDto requestForm, Form formExist, RequestToDto recipient, Long requestId, int index) throws JsonProcessingException {
        RequestForm newRequestForm = new RequestForm();
        newRequestForm.setFormId(formExist.getId());
        newRequestForm.setFormName(formExist.getFormName());
        newRequestForm.setClassification(formExist.getClassification());
        newRequestForm.setFormContent(requestForm.getFormContent());

        // Set user info who requested
        newRequestForm.setRequestFrom(requestForm.getWhoRequest());
        newRequestForm.setFromCompany(requestForm.getWhoRequestCompany());
        newRequestForm.setFromDepartment(getUserDetailFromBizLogin(requestForm.getWhoRequest(), requestForm.getWhoRequestCompany()).getDepartment());

        // Set user info who requested to
        UserDetiailPayload userDetailPayload = getUserDetailFromBizLogin(recipient.getRequestTo(), recipient.getRequestToCompany());
        newRequestForm.setRequestTo(userDetailPayload.getUsername());
        newRequestForm.setToDepartment(userDetailPayload.getDepartment());
        newRequestForm.setToCompany(userDetailPayload.getCompany());

        newRequestForm.setRequestId(requestId);
        newRequestForm.setRequestDate(requestForm.getRequestDate());
        newRequestForm.setReqOrder(index);

        newRequestForm.setRequestStatus(index == 0 ? RqStatus.PENDING : RqStatus.HOLD);

        return newRequestForm;
    }

    private List<RequestMainItems> createMainItemsList(RequestFormDto requestFormDto, Form formExist, RequestForm newRequestForm) {
        List<RequestMainItems> mainItemsList = new ArrayList<>();
        int count = 0;

        for (RequestMainItemsDto mainItemsDto : requestFormDto.getRequestMainItems()) {
            MainItems formMainItemsExist = formExist.getMainItems().get(count);
            RequestMainItemsDto dto = requestFormDto.getRequestMainItems().get(count);

            if (Objects.equals(formMainItemsExist.getId(), dto.getId())) {
                RequestMainItems mainItems = new RequestMainItems();
                mainItems.setValue(mainItemsDto.getValue());
                mainItems.setItemName(formMainItemsExist.getItemName());
                mainItems.setRequire(formMainItemsExist.getRequire());
                mainItems.setType(formMainItemsExist.getType());
                mainItems.setRequestForm(newRequestForm);

                List<RequestItemsData> itemsDataList = createItemsDataList(mainItemsDto, formMainItemsExist, mainItems);

                mainItems.setRequestItemsData(itemsDataList);
                mainItemsList.add(mainItems);

                count++;
            } else {
                throw new IllegalArgumentException("Main Item with ID " + dto.getId() + " Not Found");
            }
        }

        return mainItemsList;
    }

    private List<RequestItemsData> createItemsDataList(RequestMainItemsDto mainItemsDto, MainItems formMainItems, RequestMainItems mainItems) {
        List<RequestItemsData> itemsDataList = new ArrayList<>();

        for (int i = 0; i < formMainItems.getItemsData().size(); i++) {
            ItemsData formItemsData = formMainItems.getItemsData().get(i);
            RequestItemsDataDto itemsDataDto = mainItemsDto.getRequestItemsData().get(i);

            if (Objects.equals(formItemsData.getId(), itemsDataDto.getId())) {
                RequestItemsData itemsData = new RequestItemsData();
                itemsData.setInputValue(itemsDataDto.getInputValue());
                itemsData.setItemName(formItemsData.getItemName());
                itemsData.setInputType(formItemsData.getInputType());
                itemsData.setSelected(formItemsData.isSelected());
                itemsData.setInputRequire(formItemsData.getInputRequire());
                itemsData.setDescription(formItemsData.getDescription());
                itemsData.setRequestMainItems(mainItems);

                itemsDataList.add(itemsData);
            } else {
                throw new IllegalArgumentException("Item Data with ID " + itemsDataDto.getId() + " Not Found.");
            }
        }

        return itemsDataList;
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

        if (request.getStartDate() == null || request.getEndDate() == null) {
            throw new IllegalArgumentException("Start date and End date Can not empty.");
        }

        if (request.getProposer().isBlank() && request.getRecipient().isBlank()) {
            throw new IllegalArgumentException("Recipient or Proposer must only one with data");
        }
        if (request.getCompany().isBlank()) {
            throw new IllegalArgumentException("Company can not blank.");
        }

        List<RequestForm> getData = new ArrayList<>();

        if (Objects.isNull(request.getReqStatus())) {
            if (request.getProposer().isBlank()) {
                getData = requestFormRepository.findByRequestToAndToCompany(request.getRecipient(), request.getCompany());
            } else if (request.getRecipient().isBlank()) {
                getData = requestFormRepository.findByRequestFromAndFromCompany(request.getProposer(), request.getCompany());
            } else {
                throw new IllegalArgumentException("Recipient or Proposer must only one with data");
            }
        } else {
            if (request.getProposer().isBlank()) {
                getData = requestFormRepository.findByRequestToAndRequestStatus(request.getRecipient(), RqStatus.valueOf(request.getReqStatus()));
            } else if (request.getRecipient() == null || request.getRecipient().isBlank()) {
                getData = requestFormRepository.findByRequestFromAndRequestStatus(request.getProposer(), RqStatus.valueOf(request.getReqStatus()));
            } else {
                throw new IllegalArgumentException("Recipient or Proposer must only one with data");
            }
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        List<RequestForm> filteredData = getData.stream()
                .filter(item -> {
                    LocalDateTime localDateTime = LocalDateTime.parse(item.getRequestDate().toString(), formatter);
                    LocalDate requestLocalDate = localDateTime.toLocalDate();
                    return !requestLocalDate.isBefore(request.getStartDate()) && !requestLocalDate.isAfter(request.getEndDate());
                })
                .collect(Collectors.toList());

        return filteredData;
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

    public List<RequestForm> getListApproved(String userId, String company) {
        List<RequestForm> newList = new ArrayList<>();
        Map<Long, RequestForm> lastRequestForms = new HashMap<>();

        List<RequestForm> requestList = requestFormRepository.findByRequestFromAndFromCompany(userId, company);

        if (requestList.isEmpty()) {
            throw new NotFoundExceptionClass("Request With UserID : " + userId + " Not Found.");
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

        if (request.getRequestStatus() == RqStatus.APPROVED) {
            throw new IllegalArgumentException("Request is Already " + request.getRequestStatus());
        } else if (request.getRequestStatus() == RqStatus.HOLD) {
            throw new IllegalArgumentException("Request is on " + request.getRequestStatus());
        }

        List<RequestForm> allRequests = requestFormRepository.findByRequestId(request.getRequestId());
        List<RequestForm> sortedRequests = allRequests.stream()
                .sorted(Comparator.comparingInt(RequestForm::getReqOrder))
                .toList();


        for (int i = 0; i <= sortedRequests.size(); i++) {
            RequestForm req = sortedRequests.get(i);
            if (req.getRequestStatus() == RqStatus.PENDING) {
                req.setRequestStatus(RqStatus.APPROVED);

                if (sortedRequests.size() - 1 > req.getReqOrder()) {
                    sortedRequests.get(i + 1).setRequestStatus(RqStatus.PENDING);
                }
                req.setApproveDate(new Timestamp(System.currentTimeMillis()));
                requestFormRepository.save(req);
                return request;
            }
        }

        return null;
    }

    public List<RequestForm> getRequestById(Long requestId) {
        List<RequestForm> requestForm = requestFormRepository.findByRequestId(requestId);

        if (requestForm.isEmpty()) {
            throw new NotFoundExceptionClass("Request with ID " + requestId + " Not Found");
        }

        return requestForm;
    }

    public List<CombineRequestFormDto> getListRequest(String userId, String company) {
        // Fetch the list of request forms
        List<RequestForm> requestFormList = requestFormRepository.findByRequestFromAndFromCompany(userId, company);

        // Group the request forms by requestId
        Map<Long, List<RequestForm>> groupedByRequestId = requestFormList.stream()
                .collect(Collectors.groupingBy(RequestForm::getRequestId));

        // Merge the request forms with the same requestId into CombineRequestFormDto
        List<CombineRequestFormDto> combinedRequestForms = groupedByRequestId.values().stream().map(requestForms -> {
            // Sort the request forms by reqOrder
            requestForms.sort(Comparator.comparingInt(RequestForm::getReqOrder));

            // Take the first form as the base
            RequestForm firstForm = requestForms.get(0);
            CombineRequestFormDto combinedRequestFormDto = new CombineRequestFormDto(
                    firstForm.getId(),
                    firstForm.getRequestId(),
                    firstForm.getFormId(),
                    firstForm.getFormName(),
                    firstForm.getClassification(),
                    firstForm.getFormContent(),
                    firstForm.getRequestFrom(),
                    firstForm.getFromDepartment(),
                    firstForm.getFromCompany(),
                    null, // requestTo will be set later
                    firstForm.getToDepartment(),
                    firstForm.getToCompany(),
                    firstForm.getReqOrder(),
                    firstForm.getRequestDate(),
                    null, // requestStatus will be set later
                    firstForm.getRequestMainItems(),
                    null, // nextApprove will be set later
                    null  // finalApprove will be set later
            );

            // Merge requestTo, requestStatus, and determine nextApprove and finalApprove
            StringBuilder requestToBuilder = new StringBuilder();
            StringBuilder requestStatusBuilder = new StringBuilder();
            String whoChecking = null;
            String finalApprove = null;

            for (int i = 0; i < requestForms.size(); i++) {
                RequestForm currentForm = requestForms.get(i);

                // Combine requestTo
                requestToBuilder.append(currentForm.getRequestTo());
                if (i < requestForms.size() - 1) {
                    requestToBuilder.append(", ");
                }

                // Combine requestStatus
                requestStatusBuilder.append(currentForm.getRequestStatus());
                if (i < requestForms.size() - 1) {
                    requestStatusBuilder.append(", ");
                }

                // Determine nextApprove (first status after PENDING)
                if (whoChecking == null && "PENDING".equals(String.valueOf(currentForm.getRequestStatus()))) {
                    whoChecking = currentForm.getRequestTo();
                }

                // Determine finalApprove (highest reqOrder)
                if (finalApprove == null || currentForm.getReqOrder() > firstForm.getReqOrder()) {
                    finalApprove = currentForm.getRequestTo();
                }
            }

            // Set the combined and calculated values
            combinedRequestFormDto.setRequestTo(requestToBuilder.toString());
            combinedRequestFormDto.setRequestStatus(requestStatusBuilder.toString());
            combinedRequestFormDto.setWhoChecking(whoChecking);
            combinedRequestFormDto.setFinalApprove(finalApprove);

            return combinedRequestFormDto;
        }).collect(Collectors.toList());

        return combinedRequestForms;
    }

}
