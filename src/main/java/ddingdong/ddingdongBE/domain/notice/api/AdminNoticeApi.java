package ddingdong.ddingdongBE.domain.notice.api;

import ddingdong.ddingdongBE.auth.PrincipalDetails;
import ddingdong.ddingdongBE.domain.notice.controller.dto.request.CreateNoticeRequest;
import ddingdong.ddingdongBE.domain.notice.controller.dto.request.UpdateNoticeRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Notice - Admin", description = "Notice Admin API")
@RequestMapping("/server/admin/notices")
public interface AdminNoticeApi {

    @Operation(summary = "공지사항 생성")
    @ApiResponse(responseCode = "201", description = "공지사항 생성 성공")
    @ResponseStatus(HttpStatus.CREATED)
    @SecurityRequirement(name = "AccessToken")
    @PostMapping
    void createNotice(@ModelAttribute CreateNoticeRequest request,
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @RequestPart(name = "thumbnailImages", required = false) List<MultipartFile> images,
        @RequestPart(name = "uploadFiles", required = false) List<MultipartFile> files);

    @Operation(summary = "공지사항 수정")
    @ApiResponse(responseCode = "204", description = "공지사항 수정 성공")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @SecurityRequirement(name = "AccessToken")
    @PatchMapping("/{noticeId}")
    void updateNotice(@PathVariable Long noticeId,
        @ModelAttribute UpdateNoticeRequest request,
        @RequestPart(name = "thumbnailImages", required = false) List<MultipartFile> images,
        @RequestPart(name = "uploadFiles", required = false) List<MultipartFile> files
    );

    @Operation(summary = "공지사항 삭제")
    @ApiResponse(responseCode = "204", description = "공지사항 삭제 성공")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @SecurityRequirement(name = "AccessToken")
    @DeleteMapping("/{noticeId}")
    void deleteNotice(@PathVariable Long noticeId);
}