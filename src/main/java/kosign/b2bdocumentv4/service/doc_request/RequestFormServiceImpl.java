package kosign.b2bdocumentv4.service.doc_request;

import kosign.b2bdocumentv4.entity.doc_form.Form;
import kosign.b2bdocumentv4.entity.doc_form.FormRepository;
import kosign.b2bdocumentv4.entity.doc_request.GetByUserRequest;
import kosign.b2bdocumentv4.entity.doc_request.RequestForm;
import kosign.b2bdocumentv4.entity.doc_request.RequestFormRepository;
import kosign.b2bdocumentv4.entity.doc_request.RqStatus;
import kosign.b2bdocumentv4.exception.NotFoundExceptionClass;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public RequestForm sendFormRequest(RequestForm requestForm) {
        Form formExist = formRepository.findById(requestForm.getFormId())
                .orElseThrow(() -> new NotFoundExceptionClass("Form not found with id: " + requestForm.getFormId()));
        List<String> recipients = List.of(requestForm.getRequestTo().split(","));
        Long requestId = generateRequestId(); // or another logic to generate a unique ID

        for (String recipient : recipients) {
            RequestForm newRequestForm = new RequestForm();
            newRequestForm.setFormId(requestForm.getFormId());
            newRequestForm.setFormName(formExist.getFormName());
            newRequestForm.setFormContent(requestForm.getFormContent());
            newRequestForm.setRequestFrom(requestForm.getRequestFrom());
            newRequestForm.setRequestTo(recipient.trim());
            newRequestForm.setRequestId(requestId);
            newRequestForm.setRequestDate(requestForm.getRequestDate());
            newRequestForm.setRequestStatus(requestForm.getRequestStatus());
            requestFormRepository.save(newRequestForm);
        }
        return null;
    }

    public List<RequestForm> getByUserId(GetByUserRequest request) {

        if(request.getProposer().isBlank() && request.getRecipient().isBlank()){
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
}
