package kosign.b2bdocumentv4.service.doc_form;

import kosign.b2bdocumentv4.entity.doc_form.Form;
import kosign.b2bdocumentv4.entity.doc_form.FormRepository;
import kosign.b2bdocumentv4.exception.NotFoundExceptionClass;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class FormServiceImpl {
    private final FormRepository formRepository;

    public FormServiceImpl(FormRepository formRepository) {
        this.formRepository = formRepository;
    }


    public Form createForm(Form request) {
        Form data = formRepository.save(request);
        return data;
    }

    public List<Form> getAll(int status, String username) {
        List<Form> data;
        if (username == null || username.isBlank()) {
            data = formRepository.findAllByStatus(status);
        } else {
            data = formRepository.findAllByStatusAndUsername(status, username);
        }
        if (data.isEmpty()) {
            throw new NotFoundExceptionClass("There is No form Data!");
        }
        return data;
    }

    public Form updateForm(Long id, String content) {
        Form oldForm = formRepository.findById(id)
                .orElseThrow(() -> new NotFoundExceptionClass("Form not found with id: " + id));

        if (Objects.equals(content, "")) {
            throw new IllegalArgumentException("Content can't empty!");
        }
        if (oldForm != null) {
            oldForm.setFormContent(content);
        }

        assert oldForm != null;
        return formRepository.save(oldForm);
    }

    public void removeForm(Long id) {
        Form oldForm = formRepository.findById(id)
                .orElseThrow(() -> new NotFoundExceptionClass("Form not found with id: " + id));

        if (oldForm != null) {
            formRepository.deleteById(id);
        }
    }
}
