package capstone3d.Server.domain.dto;

import capstone3d.Server.domain.User;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FileDto {
    private String title;

    private MultipartFile file;
}