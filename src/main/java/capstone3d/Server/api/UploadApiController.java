package capstone3d.Server.api;

import capstone3d.Server.domain.dto.FileDto;
import capstone3d.Server.service.S3UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;

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
    public String uploadFile(FileDto fileDto, Principal principal) throws IOException {
        System.out.println("fileDto = " + fileDto.getTitle());
        System.out.println("fileDto = " + fileDto.getFile().getOriginalFilename());
        System.out.println("principal = " + principal.getName());
        String url = s3UploadService.uploadFile(fileDto.getFile(), principal.getName(), fileDto.getTitle());

        return url;
    }
}