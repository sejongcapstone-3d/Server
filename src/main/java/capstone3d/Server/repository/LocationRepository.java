package capstone3d.Server.repository;

import capstone3d.Server.domain.Location;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class LocationRepository {

    private final EntityManager em;

    public List<Location> findAll() {
        return em.createQuery("select l from Location l", Location.class).getResultList();
    }

    public void save(Location location) {
        em.persist(location);
    }
}