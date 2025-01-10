package ddingdong.ddingdongBE.domain.feed.service;

import ddingdong.ddingdongBE.common.exception.PersistenceException.ResourceNotFound;
import ddingdong.ddingdongBE.domain.feed.entity.Feed;
import ddingdong.ddingdongBE.domain.feed.repository.FeedRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GeneralFeedService implements FeedService {

    private final FeedRepository feedRepository;

    @Override
    public Slice<Feed> getFeedPageByClubId(Long clubId, int size, Long currentCursorId) {
        return feedRepository.findPageByClubIdOrderById(clubId, size, currentCursorId);
    }

    @Override
    public List<Feed> getNewestAll() {
        return feedRepository.findNewestAll();
    }

    @Override
    public Feed getById(Long feedId) {
        return feedRepository.findById(feedId)
            .orElseThrow(() -> new ResourceNotFound("Feed(id: " + feedId + ")를 찾을 수 없습니다."));
    }

    @Override
    @Transactional
    public Long create(Feed feed) {
        Feed savedFeed = feedRepository.save(feed);
        return savedFeed.getId();
    }

    @Override
    @Transactional
    public void delete(Feed feed) {
        feedRepository.delete(feed);
    }
}
