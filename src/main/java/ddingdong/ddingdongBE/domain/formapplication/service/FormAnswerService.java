package ddingdong.ddingdongBE.domain.formapplication.service;

import ddingdong.ddingdongBE.domain.formapplication.entity.FormAnswer;
import ddingdong.ddingdongBE.domain.formapplication.entity.FormApplication;

import java.util.List;

public interface FormAnswerService {

    void createAll(List<FormAnswer> formAnswers);

    List<FormAnswer> getAllByApplication(FormApplication formApplication);

}
