package ddingdong.ddingdongBE.domain.form.service;

import ddingdong.ddingdongBE.domain.form.entity.Form;
import ddingdong.ddingdongBE.domain.form.entity.FormField;
import java.util.List;
import java.util.Optional;

public interface FormFieldService {

    void createAll(List<FormField> formFields);

    FormField getById(Long id);

    List<FormField> findAllByForm(Form form);

    void deleteAll(List<FormField> originFormFields);
}
