package ddingdong.ddingdongBE.domain.feed.api;

import ddingdong.ddingdongBE.domain.feed.controller.dto.response.FeedListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag(name = "Feed - User", description = "Feed API")
@RequestMapping("/server/feeds")
public interface FeedApi {

  @Operation(summary = "동아리 게시물 전체 조회 API")
  @ApiResponse(responseCode = "200", description = "동아리 게시물 전체 조회 성공",
      content = @Content(schema = @Schema(implementation = FeedListResponse.class)))
  @ResponseStatus(HttpStatus.OK)
  @GetMapping
  List<FeedListResponse> getAllFeedByClubId(@RequestParam Long clubId);
}