package capstone3d.Server.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminController {

    @GetMapping("/admin/test")
    public String test() {
        return "success";
    }
}
