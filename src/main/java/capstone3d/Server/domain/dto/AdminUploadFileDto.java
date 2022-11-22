package capstone3d.Server.domain.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class AdminUploadFileDto {

    private String title;

    private List<MultipartFile> files;

    private String userEmail;

    private double lat;

    private double lng;

    private int room_width;

    private int room_height;

    private int room_depth;

    private String address;
}
