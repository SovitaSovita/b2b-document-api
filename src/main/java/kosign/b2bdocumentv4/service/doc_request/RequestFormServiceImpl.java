package kosign.b2bdocumentv4.service.doc_request;

import kosign.b2bdocumentv4.dto.RequestFormDto;
import kosign.b2bdocumentv4.dto.RequestItemsDataDto;
import kosign.b2bdocumentv4.entity.doc_form.Form;
import kosign.b2bdocumentv4.entity.doc_form.FormRepository;
import kosign.b2bdocumentv4.entity.doc_form.ItemsData;
import kosign.b2bdocumentv4.entity.doc_request.*;
import kosign.b2bdocumentv4.exception.NotFoundExceptionClass;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RequestFormServiceImpl {
    private final RequestFormRepository requestFormRepository;
    private final FormRepository formRepository;
    private static final SecureRandom random = new SecureRandom();

    private static long generateRequestId() {
        return random.nextInt(1_000_000);  // Generates a random number between 0 and 999,999
    }


    public RequestFormServiceImpl(FormRepository formRepository, RequestFormRepository requestFormRepository) {
        this.requestFormRepository = requestFormRepository;
        this.formRepository = formRepository;
    }

    public RequestFormDto sendFormRequest(RequestFormDto requestForm) {
        Form formExist = formRepository.findById(requestForm.getFormId())
                .orElseThrow(() -> new NotFoundExceptionClass("Form not found with id: " + requestForm.getFormId()));

        List<String> recipients = List.of(requestForm.getRequestTo().split(","));
        Long requestId = generateRequestId();

        Integer index = 0;
        for (String recipient : recipients) {
            RequestForm newRequestForm = new RequestForm();
            newRequestForm.setFormId(formExist.getId());
            newRequestForm.setFormName(formExist.getFormName());
            newRequestForm.setFormContent(requestForm.getFormContent());
            newRequestForm.setRequestFrom(requestForm.getRequestFrom());
            newRequestForm.setRequestTo(recipient.trim());
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

            if (countFormItems == countInputItems) {
                for (int i = 0; i < countFormItems; i++) {
                    RequestItemsDataDto itemsData = requestForm.getRequestItemsData().get(i);
                    ItemsData formItemsData = formExist.getItemsData().get(i);

                    RequestItemsData data = new RequestItemsData();

//                    if(Objects.equals(formItemsData.getItemName(), itemsData.getItemName())){
                    data.setItemName(formItemsData.getItemName());
                    data.setInputValue(itemsData.getInputValue());
                    data.setSelected(itemsData.isSelected());
//                    }
//                    else {
//                        throw new IllegalArgumentException("Item name not match with item name in form data.");
//                    }

                    data.setInputRequire(formItemsData.getInputRequire());
                    data.setInputType(formItemsData.getInputType());
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
                    requestFormRepository.save(req);
                    return request;
                }
        }

        return null;
    }
}
