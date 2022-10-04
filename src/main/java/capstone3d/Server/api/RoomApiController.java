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

    @GetMapping("/room/{id}")
    public RoomUrlDto RoomUrl(@PathVariable("id") int id) {
        Room findRoom = roomRepository.findById(id);
        return new RoomUrlDto(findRoom.getEmpty_room_url(), findRoom.getFull_room_url(), findRoom.getRoom_width(),findRoom.getRoom_height(),findRoom.getRoom_depth());
    }

    @Data
    static class RoomUrlDto {
        private String empty_room_url;
        private String full_room_url;
        private double room_width;
        private double room_height;
        private double room_depth;

        public RoomUrlDto(String empty_room_url, String full_room_url, double room_width, double room_height, double room_depth) {
            this.empty_room_url = empty_room_url;
            this.full_room_url = full_room_url;
            this.room_width = room_width;
            this.room_height = room_height;
            this.room_depth = room_depth;
        }
    }
}