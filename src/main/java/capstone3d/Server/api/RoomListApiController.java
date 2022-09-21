package capstone3d.Server.api;

import capstone3d.Server.domain.Location;
import capstone3d.Server.domain.Room;
import capstone3d.Server.repository.LocationRepository;
import capstone3d.Server.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RoomListApiController {

    private final LocationRepository locationRepository;
    private final RoomRepository roomRepository;

    @GetMapping("/list")
    public List<Location> locationList() {
        return locationRepository.findAll();
    }

    @GetMapping("/why")
    public Room roomCome(){
        return roomRepository.findById(1);
    }
}