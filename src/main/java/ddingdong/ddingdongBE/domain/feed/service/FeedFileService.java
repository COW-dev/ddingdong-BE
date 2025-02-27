package ddingdong.ddingdongBE.domain.feed.service;

import ddingdong.ddingdongBE.common.exception.PersistenceException.ResourceNotFound;
import ddingdong.ddingdongBE.domain.club.entity.Club;
import ddingdong.ddingdongBE.domain.feed.entity.Feed;
import ddingdong.ddingdongBE.domain.feed.service.dto.query.ClubProfileQuery;
import ddingdong.ddingdongBE.domain.feed.service.dto.query.FeedFileUrlQuery;
import ddingdong.ddingdongBE.domain.feed.service.dto.query.FeedListQuery;
import ddingdong.ddingdongBE.domain.filemetadata.entity.DomainType;
import ddingdong.ddingdongBE.domain.filemetadata.entity.FileMetaData;
import ddingdong.ddingdongBE.domain.filemetadata.service.FileMetaDataService;
import ddingdong.ddingdongBE.file.service.S3FileService;
import ddingdong.ddingdongBE.file.service.dto.query.UploadedFileUrlQuery;
import ddingdong.ddingdongBE.file.service.dto.query.UploadedVideoUrlQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FeedFileService {

    private final FileMetaDataService fileMetaDataService;
    private final S3FileService s3FileService;

    public FeedListQuery extractFeedThumbnailInfo(Feed feed) {
        FileMetaData fileMetaData = getFileMetaData(feed.getFeedType().getDomainType(), feed.getId());
        if (feed.isImage()) {
            UploadedFileUrlQuery urlQuery = s3FileService.getUploadedFileUrl(fileMetaData.getFileKey());
            return new FeedListQuery(feed.getId(), urlQuery.cdnUrl(), urlQuery.originUrl(), feed.getFeedType().name());
        }

        if (feed.isVideo()) {
            UploadedVideoUrlQuery urlQuery = s3FileService.getUploadedVideoUrl(fileMetaData.getFileKey());
            return new FeedListQuery(feed.getId(), urlQuery.thumbnailCdnUrl(), urlQuery.thumbnailOriginUrl(), feed.getFeedType().name());
        }

        throw new IllegalArgumentException("FeedType은 Image 혹은 Video여야 합니다.");
    }

    public FeedFileUrlQuery extractFeedFileInfo(Feed feed) {
        FileMetaData fileMetaData = getFileMetaData(feed.getFeedType().getDomainType(), feed.getId());
        if (feed.isImage()) {
            UploadedFileUrlQuery urlQuery = s3FileService.getUploadedFileUrl(fileMetaData.getFileKey());
            return new FeedFileUrlQuery(urlQuery.id(), urlQuery.originUrl(), urlQuery.cdnUrl());
        }

        if (feed.isVideo()) {
            UploadedVideoUrlQuery urlQuery = s3FileService.getUploadedVideoUrl(fileMetaData.getFileKey());
            return new FeedFileUrlQuery(fileMetaData.getId().toString(), urlQuery.videoOriginUrl(), urlQuery.videoCdnUrl());
        }

        throw new IllegalArgumentException("FeedType은 Image 혹은 Video여야 합니다.");
    }

    public ClubProfileQuery extractClubInfo(Club club) {
        String clubName = club.getName();
        UploadedFileUrlQuery urlQuery = getFileUrl(DomainType.CLUB_PROFILE, club.getId());
        if (urlQuery == null) {
            return new ClubProfileQuery(club.getId(), clubName, null, null);
        }
        return new ClubProfileQuery(club.getId(), clubName, urlQuery.originUrl(), urlQuery.cdnUrl());
    }

    private FileMetaData getFileMetaData(DomainType domainType, Long id) {
        return fileMetaDataService.getCoupledAllByDomainTypeAndEntityId(domainType, id)
            .stream()
            .findFirst()
            .orElseThrow(() -> new ResourceNotFound("해당 FileMetaData(feedId: " + id + ")를 찾을 수 없습니다.)"));
    }

    private UploadedFileUrlQuery getFileUrl(DomainType domainType, Long clubId) {
        return fileMetaDataService.getCoupledAllByDomainTypeAndEntityId(domainType, clubId)
            .stream()
            .map(fileMetaData -> s3FileService.getUploadedFileUrl(fileMetaData.getFileKey()))
            .findFirst()
            .orElse(null);
    }
}
