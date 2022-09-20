package capstone3d.Server.repository;

import capstone3d.Server.domain.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RoomRepository {

    private final EntityManager em;

    public List<Room> findAll() {
        return em.createQuery("select r from Room r", Room.class).getResultList();
    }

    public Room findByLoc(Double lat, Double lon) {
        return em.createQuery("select r from Room r where r.lat = :lat and r.lon =:lon", Room.class)
                .setParameter("lat", lat)
                .setParameter("lon", lon)
                .getSingleResult();
    }
}