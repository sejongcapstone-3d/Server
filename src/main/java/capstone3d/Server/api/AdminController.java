package capstone3d.Server.api;

import capstone3d.Server.domain.dto.AdminUploadFileDto;
import capstone3d.Server.service.AdminService;
import capstone3d.Server.service.S3UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final S3UploadService s3UploadService;

    @PostMapping("/admin/upload")
    public String uploadFile(AdminUploadFileDto adminUploadFileDto, String userIdentification) throws IOException {

        adminService.saveFile(adminUploadFileDto, userIdentification);

        return "adminUpload";
    }
}
