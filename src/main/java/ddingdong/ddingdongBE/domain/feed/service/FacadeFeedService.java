package ddingdong.ddingdongBE.domain.feed.service;

import static ddingdong.ddingdongBE.domain.fileinformation.entity.FileDomainCategory.CLUB_PROFILE;
import static ddingdong.ddingdongBE.domain.fileinformation.entity.FileTypeCategory.IMAGE;

import ddingdong.ddingdongBE.domain.club.entity.Club;
import ddingdong.ddingdongBE.domain.feed.entity.Feed;
import ddingdong.ddingdongBE.domain.feed.service.dto.response.FeedInfo;
import ddingdong.ddingdongBE.domain.feed.service.dto.response.FeedListInfo;
import ddingdong.ddingdongBE.domain.feed.service.dto.response.NewestFeedListInfo;
import ddingdong.ddingdongBE.domain.feed.vo.ClubInfo;
import ddingdong.ddingdongBE.domain.fileinformation.service.FileInformationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FacadeFeedService {

  private final FeedService feedService;
  private final FileInformationService fileInformationService;

  public List<FeedListInfo> getAllByClubId(Long clubId) {
    List<Feed> feeds = feedService.getAllByClubId(clubId);
    return feeds.stream()
        .map(FeedListInfo::from)
        .toList();
  }

  public List<NewestFeedListInfo> getNewestAll() {
    List<Feed> feeds = feedService.getNewestAll();
    return feeds.stream()
        .map(NewestFeedListInfo::from)
        .toList();
  }

  public FeedInfo getById(Long feedId) {
    Feed feed = feedService.getById(feedId);
    ClubInfo clubInfo = extractClubInfo(feed.getClub());
    return FeedInfo.of(feed, clubInfo);
  }

  private ClubInfo extractClubInfo(Club club) {
    String clubName = club.getName();
    List<String> profileImageUrls = fileInformationService.getImageUrls(
        IMAGE.getFileType() + CLUB_PROFILE.getFileDomain() + club.getId()
    );
    return ClubInfo.builder()
        .name(clubName)
        .profileImageUrl(profileImageUrls.get(0))
        .build();
  }
}
