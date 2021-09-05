package basridrm.travelapp.service;

import basridrm.travelapp.data.entity.Destination;
import basridrm.travelapp.dto.binding.DestinationBindingModel;
import javassist.NotFoundException;

import java.util.List;

public interface DestinationService {

    List<DestinationBindingModel> findAll();
    DestinationBindingModel findById(Long destinationId) throws NotFoundException;
    Destination addDestination(DestinationBindingModel destinationBindingModel);
    void editDestination(Long destinationId, DestinationBindingModel destinationBindingModel) throws NotFoundException;
}