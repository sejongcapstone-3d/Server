package capstone3d.Server.api;

import capstone3d.Server.domain.Room;
import capstone3d.Server.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RoomApiController {

    private RoomRepository roomRepository;

    @GetMapping("/url/{lat}/{lon}")
    public String RoomUrl(
            @PathVariable("lat") Double lat,
            @PathVariable("lon") Double lon
    ) {
        Room findRoom = roomRepository.findByLoc(lat, lon);
        String url = findRoom.getFileUrl();
        return url;
    }
}
