package basridrm.travelapp.service.implementations;

import basridrm.travelapp.data.repository.DestinationRepository;
import basridrm.travelapp.dto.binding.DestinationBindingModel;
import basridrm.travelapp.service.DestinationService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DestinationServiceImpl implements DestinationService {

    private final DestinationRepository destinationRepository;
    private final ModelMapper modelMapper;


    public DestinationServiceImpl(DestinationRepository destinationRepository, ModelMapper modelMapper) {
        this.destinationRepository = destinationRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<DestinationBindingModel> findAll() {
        return destinationRepository.findAll()
                .stream()
                .map(destination -> this.modelMapper.map(destination, DestinationBindingModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public DestinationBindingModel findById(Long destinationId) throws NotFoundException {
        return this.destinationRepository.findById(destinationId)
                .map(destination -> this.modelMapper.map(destination, DestinationBindingModel.class))
                .orElseThrow(() -> new NotFoundException("Cannot find destination with the given id!"));
    }
}