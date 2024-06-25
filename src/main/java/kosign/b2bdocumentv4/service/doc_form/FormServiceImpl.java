package kosign.b2bdocumentv4.service.doc_form;

import kosign.b2bdocumentv4.dto.FormDto;
import kosign.b2bdocumentv4.dto.ItemsDataDto;
import kosign.b2bdocumentv4.entity.doc_form.Form;
import kosign.b2bdocumentv4.entity.doc_form.FormRepository;
import kosign.b2bdocumentv4.entity.doc_form.ItemsData;
import kosign.b2bdocumentv4.exception.NotFoundExceptionClass;
import kosign.b2bdocumentv4.payload.doc_form.GetFormRequest;
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


    public FormDto createForm(FormDto formDto) {
        Form form = new Form();
        form.setClassification(formDto.getClassification());
        form.setFormName(formDto.getFormName());
        form.setFormDescription(formDto.getFormDescription());
        form.setFormContent(formDto.getFormContent());
        form.setFormNumber(formDto.getFormNumber());

        List<ItemsData> itemsDataList = new ArrayList<>();
        if (!formDto.getIsItem().isBlank()) {
            if (formDto.getIsItem().equals("unused")) {
                form.setIsItem("unused");
                itemsDataList.add(null);
            } else if (formDto.getIsItem().equals("use")) {
                form.setIsItem("use");
                for (ItemsDataDto itemsDataDto : formDto.getItemsData()) {
                    ItemsData itemsData = new ItemsData();
                    itemsData.setItemName(itemsDataDto.getItemName());
                    itemsData.setInputRequire(itemsDataDto.getInputRequire());
                    itemsData.setInputType(itemsDataDto.getInputType());
                    itemsData.setInputValue(itemsDataDto.getInputValue());
                    itemsData.setForm(form);  // Set the reference to the parent Form
                    itemsDataList.add(itemsData);
                }
            } else throw new IllegalArgumentException("IsItems must ('use', or 'unused') !");
        } else {
            throw new IllegalArgumentException("IsItems couldn't blank!");
        }

        form.setFileId(formDto.getFileId());

        if (formDto.getUsername().isBlank()) {
            form.setUsername(null);
            form.setStatus(1);
        }
        else {
            form.setUsername(formDto.getUsername());
            form.setStatus(2);
        }
        form.setCreateDate(formDto.getCreateDate());
        form.setItemsData(itemsDataList);
        // Save the form (itemsDataList will be saved as well due to cascade setting)
        formRepository.save(form);
        return formDto;
    }

    public List<Form> getAll() {
        List<Form> data = formRepository.findAll();
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

    public List<Form> getBy(GetFormRequest formRequest) {
        List<Form> formList = null;
        if (formRequest.getUserId().isBlank() && formRequest.getStatus() != 0) {
            if (formRequest.getStatus() == 1) {
                formList = formRepository.findAllByStatus(1);
            } else if(formRequest.getStatus() == 2) {
                formList = formRepository.findAllByStatus(2);
            }
            else{
                throw new IllegalArgumentException("Status must ('1' = default or '2' = create by user)");
            }
        }
        else if(!formRequest.getUserId().isBlank() && formRequest.getStatus() == 2){
            formList = formRepository.findAllByUsername(formRequest.getUserId());
        }
        else if(!formRequest.getUserId().isBlank() && formRequest.getStatus() == 0){
            formList = formRepository.findAllByUsernameOrUsernameIsNull(formRequest.getUserId());
        }
        else {
            throw new IllegalArgumentException("Bad request for userId = "+formRequest.getUserId() + ", and status = " + formRequest.getStatus());
        }

        return formList;
    }

    public Form getDetail(Long id) {
        Form data = formRepository.findById(id).orElseThrow(() -> new NotFoundExceptionClass("Form not found"));
        return data;
    }
}
