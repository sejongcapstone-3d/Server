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

    public Room findById(long id) {
        return em.createQuery("select r from Room r where r.id = :id", Room.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public List<Room> findAll() {
        return em.createQuery("select r from Room r", Room.class).getResultList();
    }

    public void save(Room room) {
        em.persist(room);
    }
}