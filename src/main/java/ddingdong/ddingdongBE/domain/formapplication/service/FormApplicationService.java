package ddingdong.ddingdongBE.domain.formapplication.service;

import ddingdong.ddingdongBE.domain.formapplication.entity.FormApplication;
import org.springframework.data.domain.Slice;

public interface FormApplicationService {

    FormApplication create(FormApplication formApplication);

    Slice<FormApplication> getFormApplicationPageByFormId(Long formId, int size, Long currentCursorId);

    FormApplication getById(Long applicationId);
}
