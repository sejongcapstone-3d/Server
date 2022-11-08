package capstone3d.Server.domain.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class AdminUploadFileDto {

    private String title;

    private List<MultipartFile> files;

    private String userIdentification;

    private double lat;

    private double lon;

    private int room_width;

    private int room_height;

    private int room_depth;

    private String address;
}
