package capstone3d.Server.api;

import capstone3d.Server.service.S3UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class UploadApiController {

    private final S3UploadService s3UploadService;

    @GetMapping("/upload")
    public String uploadFile(){
        return "upload";
    }

    @PostMapping("/upload")
    public String uploadFile(MultipartFile multipartFile, String title) throws IOException {

        String url = s3UploadService.uploadFile(multipartFile, title);

        return url;
    }
}