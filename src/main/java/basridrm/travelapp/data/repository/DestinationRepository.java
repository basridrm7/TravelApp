package basridrm.travelapp.data.repository;

import basridrm.travelapp.data.entity.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DestinationRepository extends JpaRepository<Destination, Long> {
    void deleteById(Long id);
}