package ddingdong.ddingdongBE.domain.notice.controller;

import ddingdong.ddingdongBE.domain.notice.api.NoticeApi;
import ddingdong.ddingdongBE.domain.notice.controller.dto.response.NoticeListResponse;
import ddingdong.ddingdongBE.domain.notice.controller.dto.response.NoticeResponse;
import ddingdong.ddingdongBE.domain.notice.service.NoticeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NoticeController implements NoticeApi {

    private final NoticeService noticeService;

    @Override
    public List<NoticeListResponse> getNotices() {
        return noticeService.getAllNotices();
    }

    @Override
    public NoticeResponse getNotice(@PathVariable Long noticeId) {
        return noticeService.getNotice(noticeId);
    }

}