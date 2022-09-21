package capstone3d.Server.api;

import capstone3d.Server.domain.Room;
import capstone3d.Server.repository.RoomRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RoomApiController {

    private final RoomRepository roomRepository;

    @GetMapping("/list/{id}")
    public RoomUrlDto RoomUrl(@PathVariable("id") int id) {
        Room findRoom = roomRepository.findById(id);
        return new RoomUrlDto(findRoom.getUrl1(), findRoom.getUrl2());
    }

    @Data
    static class RoomUrlDto {
        private String url1;
        private String url2;

        public RoomUrlDto(String url1, String url2) {
            this.url1 = url1;
            this.url2 = url2;
        }
    }
}