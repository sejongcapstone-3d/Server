package capstone3d.Server.domain.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserUploadFileDto {
    private String title;

    private MultipartFile file;

    private String address;

    private double lat;

    private double lon;
}
