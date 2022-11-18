package capstone3d.Server.api;

import capstone3d.Server.domain.dto.AdminUploadFileDto;
import capstone3d.Server.response.AllResponse;
import capstone3d.Server.response.StatusMessage;
import capstone3d.Server.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/admin/upload")
    public AllResponse uploadFile(AdminUploadFileDto adminUploadFileDto) throws IOException {

        adminService.saveFile(adminUploadFileDto, adminUploadFileDto.getUserEmail());

        return new AllResponse(StatusMessage.Admin_Upload_Success.getStatus(), StatusMessage.Admin_Upload_Success.getMessage(), 0, null);
    }
}
