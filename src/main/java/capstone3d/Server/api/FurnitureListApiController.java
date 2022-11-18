package capstone3d.Server.api;

import capstone3d.Server.domain.Furniture;
import capstone3d.Server.repository.FurnitureRepository;
import capstone3d.Server.response.AllResponse;
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
public class FurnitureListApiController {

    private final FurnitureRepository furnitureRepository;

    @GetMapping("/furniture")
    public AllResponse<FurnitureDto> furnitureList() {
        List<Furniture> furniture = furnitureRepository.findAll();
        List<FurnitureDto> collect = furniture.stream()
                .map(f -> new FurnitureDto(f.getCategory(), f.getFurniture_url(), f.getFurniture_img_url(), f.getFurniture_width(), f.getFurniture_height(), f.getFurniture_depth()))
                .collect(Collectors.toList());
        if (furniture.isEmpty()) {
            return new AllResponse(StatusMessage.Get_FurnitureList_Fail.getStatus(), StatusMessage.Get_FurnitureList_Fail.getMessage(), furniture.size(), collect);
        } else {
            return new AllResponse(StatusMessage.Get_FurnitureList.getStatus(), StatusMessage.Get_FurnitureList.getMessage(), furniture.size(), collect);
        }
    }

    @Data
    @AllArgsConstructor
    static class FurnitureDto {
        private String category;

        private String furniture_url;

        private String furniture_img_url;

        private int furniture_width;

        private int furniture_height;

        private int furniture_depth;
    }
}