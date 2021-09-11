package basridrm.travelapp.data.repository;

import basridrm.travelapp.data.entity.Destination;
import basridrm.travelapp.data.entity.Hotel;
import basridrm.travelapp.dto.binding.HotelBindingModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> findAllByDestination_Id(Long destinationId);
}