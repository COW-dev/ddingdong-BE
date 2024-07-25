package ddingdong.ddingdongBE.domain.documents.controller.dto.request;

import ddingdong.ddingdongBE.domain.documents.entity.Document;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Schema(
        name = "GenerateDocumentRequest",
        description = "자료실 자료 생성 요청"
)
@Builder
public record GenerateDocumentRequest(
        @Schema(description = "자료 제목", example = "제목")
        String title,

        @Schema(description = "자료 내용", example = "내용")
        String content
) {
    public Document toEntity() {
        return Document.builder()
                .title(title)
                .content(content)
                .build();
    }

}
