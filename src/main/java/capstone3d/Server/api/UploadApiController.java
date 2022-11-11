package capstone3d.Server.api;

import capstone3d.Server.domain.dto.UserUploadFileDto;
import capstone3d.Server.service.S3UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class UploadApiController {

    private final S3UploadService s3UploadService;

    @PostMapping("/upload")
    public String uploadFile(UserUploadFileDto userUploadFileDto) throws IOException {

        String url = s3UploadService.uploadFile(userUploadFileDto);

        return url;
    }
}