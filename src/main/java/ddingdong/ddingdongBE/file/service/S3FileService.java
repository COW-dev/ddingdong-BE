package ddingdong.ddingdongBE.file.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import ddingdong.ddingdongBE.common.exception.AwsException.AwsClient;
import ddingdong.ddingdongBE.common.exception.AwsException.AwsService;
import java.net.URL;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class S3FileService {

    @Value("${spring.s3.bucket}")
    private String bucketName;

    @Value("${spring.config.activate.on-profile}")
    private String serverProfile;

    private final AmazonS3Client amazonS3Client;

    public URL generatePreSignedUrl(String path) {
        path = createFilePath(path);

        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 5;
        expiration.setTime(expTimeMillis);
        try {
            GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, path)
                    .withMethod(HttpMethod.PUT)
                    .withExpiration(expiration);

            return amazonS3Client.generatePresignedUrl(generatePresignedUrlRequest);
        } catch (AmazonServiceException e) {
            log.warn("AWS Service Error : {}", e.getMessage());
            throw new AwsService();
        } catch (AmazonClientException e) {
            log.warn("AWS Client Error : {}", e.getMessage());
            throw new AwsClient();
        }

    }

    private String createFilePath(String fileName) {
        String fileExtension = fileName.substring(fileName.lastIndexOf('.') + 1);
        return String.format("%s/%s/%s", serverProfile, fileExtension, fileName);
    }

}