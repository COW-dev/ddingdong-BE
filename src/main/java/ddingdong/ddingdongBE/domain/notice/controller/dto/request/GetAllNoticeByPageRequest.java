package ddingdong.ddingdongBE.domain.notice.controller.dto.request;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;
import org.springdoc.api.annotations.ParameterObject;

@Getter
@ParameterObject
public class GetAllNoticeByPageRequest {

    @Parameter(description = "현재 페이지 수")
    private int page;

    @Parameter(description = "조회할 페이지 크기", example = "10")
    private Integer limit;

    public GetAllNoticeByPageRequest(int page, Integer limit) {
        this.page = page;
        this.limit = (limit == null) ? 10 : limit;
    }
}

