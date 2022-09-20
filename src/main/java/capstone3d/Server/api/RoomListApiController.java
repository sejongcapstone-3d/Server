package capstone3d.Server.api;

import capstone3d.Server.domain.Room;
import capstone3d.Server.repository.RoomRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class RoomListApiController {

    private RoomRepository roomRepository;

    @GetMapping("/list")
    public Location locationList() {
        List<Room> findRooms = roomRepository.findAll();

        List<RoomLocationDto> collect = findRooms.stream()
                .map(room -> new RoomLocationDto(room.getLat(), room.getLon()))
                .collect(Collectors.toList());
        return new Location(collect);
    }

    @Data
    @AllArgsConstructor
    static class Location<T> {
        private T data;
    }

    @Data
    static class RoomLocationDto {
        private Double lat;
        private Double lon;

        public RoomLocationDto(Double lat, Double lon) {
            this.lat = lat;
            this.lon = lon;
        }
    }
}