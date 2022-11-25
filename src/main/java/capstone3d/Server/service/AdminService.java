package capstone3d.Server.service;

import capstone3d.Server.domain.Location;
import capstone3d.Server.domain.Room;
import capstone3d.Server.domain.User;
import capstone3d.Server.domain.dto.AdminUploadFileDto;
import capstone3d.Server.exception.BadRequestException;
import capstone3d.Server.repository.LocationRepository;
import capstone3d.Server.repository.RoomRepository;
import capstone3d.Server.repository.UserRepository;
import capstone3d.Server.response.StatusMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final LocationRepository locationRepository;
    private final S3UploadService s3UploadService;

    @Transactional
    public void saveFile(AdminUploadFileDto adminUploadFileDto, String userEmail) throws IOException {
        User user = userRepository
                .findByEmail(userEmail)
                .orElseThrow(() -> new BadRequestException(StatusMessage.Not_Found_User));

        if (adminUploadFileDto.getTitle() == null || adminUploadFileDto.getAddress() == null ||
                adminUploadFileDto.getX() == 0 || adminUploadFileDto.getY() == 0 ||
                adminUploadFileDto.getZ() == 0 || adminUploadFileDto.getLat() == 0.0 ||
                adminUploadFileDto.getLng() == 0.0) {
            throw new BadRequestException(StatusMessage.Admin_Upload_Error);
        }

        Map<String, String> urls = new HashMap<>();
        urls = s3UploadService.uploadFiles(adminUploadFileDto.getFiles(), userEmail, adminUploadFileDto.getTitle());

        Room room = new Room();
        room.setUser(user);
        room.setName(adminUploadFileDto.getTitle());
        room.setX(adminUploadFileDto.getX());
        room.setY(adminUploadFileDto.getY());
        room.setZ(adminUploadFileDto.getZ());
        if (urls.get("png") != null) {
            room.setRoom_img_url(urls.get("png"));
        } else if (urls.get("jpg") != null) {
            room.setRoom_img_url(urls.get("jpg"));
        } else if (urls.get("jpeg") != null) {
            room.setRoom_img_url(urls.get("jpeg"));
        }
        room.setFull_room_url(urls.get("full"));
        room.setEmpty_room_url(urls.get("empty"));

        Location location = new Location();
        location.setLat(adminUploadFileDto.getLat());
        location.setLng(adminUploadFileDto.getLng());
        location.setAddress(adminUploadFileDto.getAddress());
        locationRepository.save(location);

        room.setLocation(location);
        roomRepository.save(room);
    }
}