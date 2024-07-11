package kosign.b2bdocumentv4.service.doc_form;

import kosign.b2bdocumentv4.dto.FormDto;
import kosign.b2bdocumentv4.dto.ItemsDataDto;
import kosign.b2bdocumentv4.dto.SubItemsDto;
import kosign.b2bdocumentv4.entity.doc_form.Form;
import kosign.b2bdocumentv4.entity.doc_form.FormRepository;
import kosign.b2bdocumentv4.entity.doc_form.ItemsData;
import kosign.b2bdocumentv4.entity.doc_form.SubItems;
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


    public Form createForm(FormDto formDto) {
        Form form = mapFormDtoToForm(formDto);  //func
        List<ItemsData> itemsDataList = new ArrayList<>();

        if (!formDto.getIsItem().isBlank()) {
            handleIsItemField(formDto, form, itemsDataList);  //func
        } else {
            throw new IllegalArgumentException("IsItems couldn't be blank!");
        }

        form.setFileId(formDto.getFileId());
        form.setCompany(formDto.getCompany());
        setFormStatusAndUsername(formDto, form); //func
        form.setCreateDate(formDto.getCreateDate());
        form.setItemsData(itemsDataList);

        formRepository.save(form);
        return form;
    }

    private Form mapFormDtoToForm(FormDto formDto) {
        Form form = new Form();
        form.setClassification(formDto.getClassification());
        form.setFormName(formDto.getFormName());
        form.setFormDescription(formDto.getFormDescription());
        form.setFormContent(formDto.getFormContent());
        form.setFormNumber(formDto.getFormNumber());
        return form;
    }

    private void handleIsItemField(FormDto formDto, Form form, List<ItemsData> itemsDataList) {
        switch (formDto.getIsItem()) {
            case "unused":
                form.setIsItem("unused");
                itemsDataList.add(null);
                break;
            case "use":
                form.setIsItem("use");
                for (ItemsDataDto itemsDataDto : formDto.getItemsData()) {
                    ItemsData itemsData = mapItemsDataDtoToItemsData(itemsDataDto, form);
                    itemsDataList.add(itemsData);
                }
                break;
            default:
                throw new IllegalArgumentException("IsItems must be 'use' or 'unused'!");
        }
    }

    private ItemsData mapItemsDataDtoToItemsData(ItemsDataDto itemsDataDto, Form form) {
        ItemsData itemsData = new ItemsData();
        itemsData.setItemName(itemsDataDto.getItemName());
        itemsData.setInputRequire(itemsDataDto.getInputRequire());
        itemsData.setInputType(itemsDataDto.getInputType());
        itemsData.setInputValue(itemsDataDto.getInputValue());
        itemsData.setForm(form);

        List<SubItems> subItemsList = new ArrayList<>();
        if ("select".equals(itemsDataDto.getInputType()) || "radio".equals(itemsDataDto.getInputType())) {
            itemsData.setSelected(true);
            itemsData.setInputValue(null);
            for (SubItemsDto subItemsDto : itemsDataDto.getSubItems()) {
                SubItems subItems = new SubItems();
                subItems.setItem(subItemsDto.isItem());
                subItems.setItemName(subItemsDto.getItemName());
                subItems.setItemsData(itemsData);
                subItemsList.add(subItems);
            }
        } else {
            subItemsList.add(null);
        }

        itemsData.setSubItemsList(subItemsList);
        return itemsData;
    }

    private void setFormStatusAndUsername(FormDto formDto, Form form) {
        if (formDto.getUsername().isBlank()) {
            form.setUsername(null);
            form.setStatus(1);
        } else {
            form.setUsername(formDto.getUsername());
            form.setStatus(2);
        }
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
        if ((formRequest.getUserId().isBlank() && formRequest.getCompany().isBlank()) && formRequest.getStatus() != 0) {
            if (formRequest.getStatus() == 1) {
                formList = formRepository.findAllByStatus(1);
            } else if (formRequest.getStatus() == 2) {
                formList = formRepository.findAllByStatus(2);
            } else {
                throw new IllegalArgumentException("Status must ('1' = default or '2' = create by user)");
            }
        } else if (!(formRequest.getUserId().isBlank() && formRequest.getCompany().isBlank()) && formRequest.getStatus() == 2) {
            formList = formRepository.findAllByUsernameAndCompany(formRequest.getUserId(), formRequest.getCompany());
        } else if (formRequest.getUserId().isBlank() && formRequest.getStatus() == 0) {
            formList = formRepository.findAllByCompany(formRequest.getCompany());
        } else {
            throw new IllegalArgumentException("Bad request for userId = " + formRequest.getUserId() + ", and status = " + formRequest.getStatus());
        }

        return formList;
    }

    public Form getDetail(Long id) {
        Form data = formRepository.findById(id).orElseThrow(() -> new NotFoundExceptionClass("Form not found"));
        return data;
    }
}
