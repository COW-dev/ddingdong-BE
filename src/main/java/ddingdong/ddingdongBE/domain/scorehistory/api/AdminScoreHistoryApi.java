package ddingdong.ddingdongBE.domain.scorehistory.api;

import ddingdong.ddingdongBE.common.exception.ExceptionResponse;
import ddingdong.ddingdongBE.domain.scorehistory.controller.dto.request.CreateScoreHistoryRequest;
import ddingdong.ddingdongBE.domain.scorehistory.controller.dto.response.ScoreHistoryFilterByClubResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag(name = "ScoreHistory - Admin", description = "ScoreHistory Admin API")
@RequestMapping("/server/admin/{clubId}/score")
public interface AdminScoreHistoryApi {

    @Operation(summary = "어드민 점수 등록 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "점수 등록 성공"),
            @ApiResponse(responseCode = "400",
                    description = "잘못된 요청, 올바르지 않은 점수변동내역 카테고리",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            //TODO: Exception 핸들링 작업 완료 시 명세 수정
                            schema = @Schema(implementation = ExceptionResponse.class))
            ),
            @ApiResponse(responseCode = "400",
                    description = "잘못된 요청, 존재하지 않는 동아리",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            //TODO: Exception 핸들링 작업 완료 시 명세 수정
                            schema = @Schema(implementation = ExceptionResponse.class))
            ),
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @SecurityRequirement(name = "AccessToken")
    void register(@PathVariable Long clubId, @Valid @RequestBody CreateScoreHistoryRequest createScoreHistoryRequest);

    @Operation(summary = "어드민 동아리 점수 내역 목록 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "점수 변동 내역 목록 조회 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ScoreHistoryFilterByClubResponse.class))),
            @ApiResponse(responseCode = "400",
                    description = "잘못된 요청, 존재하지 않는 동아리",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            //TODO: Exception 핸들링 작업 완료 시 명세 수정
                            schema = @Schema(implementation = ExceptionResponse.class))
            ),
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @SecurityRequirement(name = "AccessToken")
    ScoreHistoryFilterByClubResponse getScoreHistories(@PathVariable Long clubId);

}
