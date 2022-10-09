package capstone3d.Server.api;

import capstone3d.Server.domain.dto.FileDto;
import capstone3d.Server.service.S3UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class UploadApiController {

    private final S3UploadService s3UploadService;

    @PostMapping("/upload")
    public String uploadFile(FileDto fileDto) throws IOException {
        String url = s3UploadService.uploadFile(fileDto.getFile());

        return "redirect:/";
    }

    @GetMapping("/upload/test")
    public String test() {
        return "good";
    }
}