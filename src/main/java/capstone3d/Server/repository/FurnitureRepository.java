package capstone3d.Server.repository;

import capstone3d.Server.domain.Furniture;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FurnitureRepository {

    private final EntityManager em;

    public List<Furniture> findAll() {
        return em.createQuery("select f from Furniture f", Furniture.class).getResultList();
    }
}