package ddingdong.ddingdongBE.domain.formapplicaion.service;

import ddingdong.ddingdongBE.domain.formapplicaion.service.dto.CreateFormApplicationCommand;

public interface FacadeUserFormService {

    void createFormApplication(Long formId, CreateFormApplicationCommand createFormApplicationCommand);

}
