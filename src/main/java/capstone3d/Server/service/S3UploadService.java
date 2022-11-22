package capstone3d.Server.service;

import capstone3d.Server.domain.Room;
import capstone3d.Server.domain.User;
import capstone3d.Server.domain.dto.UserUploadFileDto;
import capstone3d.Server.exception.BadRequestException;
import capstone3d.Server.repository.UserRepository;
import capstone3d.Server.response.StatusMessage;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class S3UploadService {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;
    private final UserRepository userRepository;

    public List<String> uploadFile(UserUploadFileDto userUploadFileDto) throws IOException {

//        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String userId = userDetails.getUser().getEmail();

        /*
         * 임시적으로 id로 업로드
         * */
        long id = userUploadFileDto.getUserId();
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new BadRequestException(StatusMessage.Not_Found_User));
        String userEmail = user.getEmail();

        if (userUploadFileDto.getTitle() == null || userUploadFileDto.getAddress() == null ||
                userUploadFileDto.getLat() == 0.0 || userUploadFileDto.getLng() == 0.0 ||
                userUploadFileDto.getImg() == null || userUploadFileDto.getFile() == null) {
            throw new BadRequestException(StatusMessage.Upload_Error);
        }

        List<String> urls = new ArrayList<>();

        urls.add(uploadToS3(userUploadFileDto.getFile(), userEmail, userUploadFileDto.getTitle(), userUploadFileDto.getAddress(),
                userUploadFileDto.getLat(), userUploadFileDto.getLng()));
        urls.add(uploadToS3(userUploadFileDto.getImg(), userEmail, userUploadFileDto.getTitle(), userUploadFileDto.getAddress(),
                userUploadFileDto.getLat(), userUploadFileDto.getLng()));

        return urls;
    }

    private String uploadToS3(MultipartFile multipartFile, String userEmail, String title, String address, double lat, double lng) throws IOException {
        int pos = multipartFile.getOriginalFilename().lastIndexOf('.');

        String extension = multipartFile.getOriginalFilename().substring(pos + 1);

        if (!(extension.equals("pts") || extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png")))
            throw new BadRequestException(StatusMessage.UploadFile_format_Error);

        String fileName = "user/" + userEmail + "/" + title + "/"
                + multipartFile.getOriginalFilename().substring(0, pos)
                + "_" + address + "_" + lat + "_" + lng + "." + extension;

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
        return amazonS3.getUrl(bucket, fileName).toString();
    }

    public Map<String, String> uploadFiles(List<MultipartFile> multipartFiles, String username, String title) throws IOException {
        Map<String, String> urls = new HashMap<>();
        int filesNum = multipartFiles.size();

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
            switch (multipartFile.getOriginalFilename().substring(pos + 1)) {
                case "png":
                    urls.put("png", amazonS3.getUrl(bucket, fileName).toString());
                    filesNum--;
                    break;
                case "jpg":
                    urls.put("jpg", amazonS3.getUrl(bucket, fileName).toString());
                    filesNum--;
                    break;
                case "jpeg":
                    urls.put("jpeg", amazonS3.getUrl(bucket, fileName).toString());
                    filesNum--;
                    break;
                case "json":
                    if (multipartFile.getOriginalFilename().contains("empty")) {
                        urls.put("empty", amazonS3.getUrl(bucket, fileName).toString());
                        filesNum--;
                    } else if (multipartFile.getOriginalFilename().contains("full")) {
                        urls.put("full", amazonS3.getUrl(bucket, fileName).toString());
                        filesNum--;
                    }
                    break;
            }
            if (filesNum != 0) throw new BadRequestException(StatusMessage.Admin_UploadFile_format_Error);
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