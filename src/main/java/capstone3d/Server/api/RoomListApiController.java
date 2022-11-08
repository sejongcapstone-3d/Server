package capstone3d.Server.api;

import capstone3d.Server.domain.Location;
import capstone3d.Server.domain.Room;
import capstone3d.Server.repository.LocationRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class RoomListApiController {

    private final LocationRepository locationRepository;

    @GetMapping("/room")
    public List<LocationDto> locationList() {
        List<Location> locations = locationRepository.findAll();
        List<LocationDto> collect = locations.stream()
                .map(l -> new LocationDto(l.getRoom(), l.getLat(), l.getLon(), l.getRoom().getUser().getBusiness_name(), l.getRoom().getUser().getPhone()))
                .collect(Collectors.toList());
        return collect;
    }

    @Data
    @AllArgsConstructor
    static class LocationDto {
        private Room room;

        private Double lat;

        private Double lon;

        private String business_name;

        private String phone;
    }
}