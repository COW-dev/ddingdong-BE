package ddingdong.ddingdongBE.domain.form.service;

import ddingdong.ddingdongBE.domain.form.entity.Form;

public interface FormService {

    Form create(Form form);

    Form getById(Long formId);

    void delete(Form form);
}
