package capstone3d.Server;

import capstone3d.Server.domain.Location;
import capstone3d.Server.domain.Furniture;
import capstone3d.Server.domain.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class TestDB {
    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbInit1() {

            Room room = createRoom("https://3d-rooms.s3.ap-northeast-2.amazonaws.com/Sample1.json","https://3d-rooms.s3.ap-northeast-2.amazonaws.com/Sample2.json");
            em.persist(room);

            Location location = createLocation(room,37.0561,126.9706);
            em.persist(location);

            Furniture furniture1 = createObject("table", "https://3d-rooms.s3.ap-northeast-2.amazonaws.com/Sample1.json");
            em.persist(furniture1);

            Furniture furniture2 = createObject("chair", "https://3d-rooms.s3.ap-northeast-2.amazonaws.com/Sample1.json");
            em.persist(furniture2);
        }

        public void dbInit2() {

            Room room = createRoom("https://3d-rooms.s3.ap-northeast-2.amazonaws.com/Sample1.json","https://3d-rooms.s3.ap-northeast-2.amazonaws.com/Sample2.json");
            em.persist(room);

            Location location = createLocation(room,37.0,126.0);
            em.persist(location);

            Furniture furniture1 = createObject("book", "https://3d-rooms.s3.ap-northeast-2.amazonaws.com/Sample1.json");
            em.persist(furniture1);

            Furniture furniture2 = createObject("TV", "https://3d-rooms.s3.ap-northeast-2.amazonaws.com/Sample1.json");
            em.persist(furniture2);
        }

        private Furniture createObject(String category, String url) {
            Furniture furniture = new Furniture();
            furniture.setCategory(category);
            furniture.setFurniture_url(url);
            return furniture;
        }

        private Location createLocation(Room room, Double lat, Double lon) {
            Location location = new Location();
            location.setRoom(room);
            location.setLat(lat);
            location.setLon(lon);
            return location;
        }

        private Room createRoom(String url1, String url2) {
            Room room = new Room();
            room.setUrl1(url1);
            room.setUrl2(url2);
            return room;
        }
    }
}