package capstone3d.Server.api;

import capstone3d.Server.domain.Furniture;
import capstone3d.Server.repository.FurnitureRepository;
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
    public List<FurnitureDto> furnitureList() {
        List<Furniture> furniture = furnitureRepository.findAll();
        List<FurnitureDto> collect = furniture.stream()
                .map(f -> new FurnitureDto(f.getCategory(), f.getFurniture_url(), f.getFurniture_img_url(), f.getFurniture_width(), f.getFurniture_height(), f.getFurniture_depth()))
                .collect(Collectors.toList());
        return collect;
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