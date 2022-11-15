package capstone3d.Server.service;

import capstone3d.Server.domain.Room;
import capstone3d.Server.domain.User;
import capstone3d.Server.domain.dto.UserDetails;
import capstone3d.Server.domain.dto.UserUploadFileDto;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class S3UploadService {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;

    public String uploadFile(UserUploadFileDto userUploadFileDto) throws IOException {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = userDetails.getUser().getEmail();

        int pos = userUploadFileDto.getFile().getOriginalFilename().lastIndexOf('.');
        String extension = userUploadFileDto.getFile().getOriginalFilename().substring(pos+1);
        String fileName = "user/" + userId + "/" + userUploadFileDto.getTitle()  + "/"
                + userUploadFileDto.getFile().getOriginalFilename().substring(0, pos)
                + "_" + userUploadFileDto.getAddress() + "_" + userUploadFileDto.getLat() + "_" + userUploadFileDto.getLon() + "." + extension;

        try {
            MultipartFile file = userUploadFileDto.getFile();
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getResource().contentLength());
            amazonS3.putObject(new PutObjectRequest(bucket, fileName, file.getInputStream(), metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));

        } catch (AmazonServiceException e) {
            e.printStackTrace();
        } catch (SdkClientException e) {
            e.printStackTrace();
        }

        return amazonS3.getUrl(bucket, fileName).toString();
    }

    public Map<String, String> uploadFiles(List<MultipartFile> multipartFiles, String username, String title) throws IOException {
        Map<String, String> urls = new HashMap<>();

        for (MultipartFile multipartFile :
                multipartFiles) {
            String fileName = "server/" + username + "/" + title + "/" + multipartFile.getOriginalFilename();
            try {
                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentType(multipartFile.getContentType());
                metadata.setContentLength(multipartFile.getResource().contentLength());
                amazonS3.putObject(new PutObjectRequest(bucket, fileName, multipartFile.getInputStream(), metadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));

            } catch (AmazonServiceException e) {
                e.printStackTrace();
            } catch (SdkClientException e) {
                e.printStackTrace();
            }

            int pos = multipartFile.getOriginalFilename().lastIndexOf('.');
            switch (multipartFile.getOriginalFilename().substring(pos+1)) {
                case "png":
                    urls.put("png", amazonS3.getUrl(bucket, fileName).toString());
                    break;
                case "jpg":
                    urls.put("jpg", amazonS3.getUrl(bucket, fileName).toString());
                    break;
                case "jpeg":
                    urls.put("jpeg", amazonS3.getUrl(bucket, fileName).toString());
                    break;
                case "json":
                    if (multipartFile.getOriginalFilename().contains("empty")) {
                        urls.put("empty", amazonS3.getUrl(bucket, fileName).toString());
                    } else if (multipartFile.getOriginalFilename().contains("full")){
                        urls.put("full", amazonS3.getUrl(bucket, fileName).toString());
                    }
                    break;
            }
        }

        return urls;
    }

    public void deleteFiles(User user) {
        List<Room> room_list = user.getRoom_list();
        String userId = user.getEmail();

        for (Room room : room_list) {
            String img = URLDecoder.decode(room.getRoom_img_url(), Charset.forName("UTF-8"));
            String full = URLDecoder.decode(room.getFull_room_url(), Charset.forName("UTF-8"));
            String empty = URLDecoder.decode(room.getEmpty_room_url(), Charset.forName("UTF-8"));

            try {
                amazonS3.deleteObject(bucket, img.substring(49));
                amazonS3.deleteObject(bucket, full.substring(49));
                amazonS3.deleteObject(bucket, empty.substring(49));
            } catch (AmazonServiceException e) {
                e.printStackTrace();
            } catch (SdkClientException e) {
                e.printStackTrace();
            }
        }
    }
}