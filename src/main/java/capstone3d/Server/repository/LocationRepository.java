package capstone3d.Server.repository;

import capstone3d.Server.domain.Location;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class LocationRepository {

    private final EntityManager em;

    public void save(Location location) {
        em.persist(location);
    }
}