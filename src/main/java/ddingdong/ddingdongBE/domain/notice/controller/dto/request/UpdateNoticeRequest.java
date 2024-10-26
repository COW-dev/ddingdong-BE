package ddingdong.ddingdongBE.domain.notice.controller.dto.request;

import ddingdong.ddingdongBE.common.vo.FileInfo;
import ddingdong.ddingdongBE.domain.notice.service.dto.command.UpdateNoticeCommand;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record UpdateNoticeRequest(
    @NotNull(message = "공지사항 제목은 필수 입력 사항입니다.")
    @Schema(description = "공지사항 제목", example = "공지사항 제목")
    String title,

    @NotNull(message = "공지사항 내용은 필수 입력 사항입니다.")
    @Schema(description = "공지사항 내용", example = "공지사항 내용")
    String content,

    @Schema(description = "공지사항 이미지 key 목록")
    List<String> imageKeys,

    @Schema(description = "공지사항 파일 정보 목록")
    List<FileInfo> fileInfos
) {

    public UpdateNoticeCommand toCommand(Long noticeId) {
        return UpdateNoticeCommand.builder()
            .noticeId(noticeId)
            .title(title)
            .content(content)
            .imageKeys(imageKeys)
            .fileInfos(fileInfos)
            .build();
    }

}
