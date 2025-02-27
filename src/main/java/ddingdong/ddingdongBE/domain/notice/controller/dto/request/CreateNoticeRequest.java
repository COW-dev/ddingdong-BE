package ddingdong.ddingdongBE.domain.notice.controller.dto.request;

import ddingdong.ddingdongBE.domain.notice.service.dto.command.CreateNoticeCommand;
import ddingdong.ddingdongBE.domain.notice.service.dto.command.CreateNoticeCommand.FileInfo;
import ddingdong.ddingdongBE.domain.notice.service.dto.command.CreateNoticeCommand.ImageInfo;
import ddingdong.ddingdongBE.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record CreateNoticeRequest(
        @NotNull(message = "공지사항 제목은 필수 입력 사항입니다.")
        @Schema(description = "공지사항 제목", example = "공지사항 제목")
        String title,

        @NotNull(message = "공지사항 내용은 필수 입력 사항입니다.")
        @Schema(description = "공지사항 내용", example = "공지사항 내용")
        String content,

        @Schema(description = "공지사항 이미지 정보 목록")
        List<ImageInfoRequest> images,

        @Schema(description = "공지사항 파일 정보 목록")
        List<FileInfoRequest> files
) {

    public CreateNoticeCommand toCommand(User user) {
        return CreateNoticeCommand.builder()
                .user(user)
                .title(title)
                .content(content)
                .imageInfos((images != null) ?
                        images.stream()
                                .map(image -> new ImageInfo(image.id, image.order()))
                                .toList() :
                        List.of())
                .fileInfos((files != null) ?
                        files.stream()
                                .map(file -> new FileInfo(file.id, file.order()))
                                .toList() :
                        List.of())
                .build();
    }

    private record ImageInfoRequest(
            @Schema(description = "이미지 식별자", example = "0192c828-ffce-7ee8-94a8-d9d4c8cdec00")
            String id,
            @Schema(description = "이미지 순서", example = "1")
            @Min(value = 1, message = "이미지 순서는 1 이상이어야 합니다")
            int order
    ) {

    }

    private record FileInfoRequest(
            @Schema(description = "파일 식별자", example = "0192c828-ffce-7ee8-94a8-d9d4c8cdec00")
            String id,
            @Schema(description = "파일 순서", example = "1")
            @Min(value = 1, message = "파일 순서는 1 이상이어야 합니다")
            int order
    ) {

    }

}
