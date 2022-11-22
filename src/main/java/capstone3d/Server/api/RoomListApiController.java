package capstone3d.Server.api;

import capstone3d.Server.domain.Room;
import capstone3d.Server.response.AllResponse;
import capstone3d.Server.repository.RoomRepository;
import capstone3d.Server.response.StatusMessage;
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

    private final RoomRepository roomRepository;

    @GetMapping("/room")
    public AllResponse locationList() {
        List<Room> rooms = roomRepository.findAll();
        List<RoomDto> collect = rooms.stream()
                .map(r -> new RoomDto(r, r.getLocation().getLat(), r.getLocation().getLon(), r.getUser().getBusiness_name(), r.getUser().getPhone(), r.getLocation().getAddress()))
                .collect(Collectors.toList());

        if (rooms.isEmpty()) {
            return new AllResponse(StatusMessage.Get_RoomList_Fail.getStatus(), StatusMessage.Get_RoomList_Fail.getMessage(), rooms.size(), collect);
        } else {
            return new AllResponse(StatusMessage.Get_RoomList.getStatus(), StatusMessage.Get_RoomList.getMessage(), rooms.size(), collect);
        }
    }

    @Data
    @AllArgsConstructor
    static class RoomDto {
        private Room room;

        private Double lat;

        private Double lon;

        private String business_name;

        private String phone;

        private String address;
    }
}