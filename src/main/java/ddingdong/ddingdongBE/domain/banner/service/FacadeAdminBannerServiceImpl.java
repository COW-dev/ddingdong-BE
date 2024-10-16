package ddingdong.ddingdongBE.domain.banner.service;

import static ddingdong.ddingdongBE.domain.filemetadata.entity.FileCategory.BANNER_WEB_IMAGE;

import ddingdong.ddingdongBE.domain.banner.entity.Banner;
import ddingdong.ddingdongBE.domain.banner.service.dto.query.AdminBannerListQuery;
import ddingdong.ddingdongBE.domain.banner.service.dto.command.CreateBannerCommand;
import ddingdong.ddingdongBE.domain.filemetadata.service.FacadeFileMetaDataService;
import ddingdong.ddingdongBE.domain.filemetadata.service.dto.CreateFileMetaDataCommand;
import ddingdong.ddingdongBE.file.service.S3FileService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FacadeAdminBannerServiceImpl implements FacadeAdminBannerService {

    private final BannerService bannerService;
    private final FacadeFileMetaDataService facadeFileMetaDataService;
    private final S3FileService s3FileService;


    @Override
    @Transactional
    public Long create(CreateBannerCommand command) {
        CreateFileMetaDataCommand createBannerWebImageFileMetaDataCommand =
                new CreateFileMetaDataCommand(command.webImageKey(), BANNER_WEB_IMAGE);
        CreateFileMetaDataCommand createBannerMobileImageFileMetaDataCommand =
                new CreateFileMetaDataCommand(command.mobileImageKey(), BANNER_WEB_IMAGE);
        facadeFileMetaDataService.create(createBannerWebImageFileMetaDataCommand,
                createBannerMobileImageFileMetaDataCommand);

        return bannerService.save(command.toEntity());
    }

    @Override
    public List<AdminBannerListQuery> findAll() {
        List<Banner> banners = bannerService.findAll();
        return banners.stream()
                .map(banner -> AdminBannerListQuery.of(
                                banner,
                                s3FileService.getUploadedFileUrl(banner.getWebImageKey()),
                                s3FileService.getUploadedFileUrl(banner.getMobileImageKey())
                        )
                )
                .toList();
    }

    @Override
    @Transactional
    public void delete(Long bannerId) {
        bannerService.delete(bannerId);
    }
}