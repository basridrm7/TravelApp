package basridrm.travelapp.service.implementations;

import basridrm.travelapp.data.entity.Destination;
import basridrm.travelapp.data.repository.DestinationRepository;
import basridrm.travelapp.dto.binding.DestinationBindingModel;
import basridrm.travelapp.service.DestinationService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DestinationServiceImpl implements DestinationService {

    private final DestinationRepository destinationRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public DestinationServiceImpl(DestinationRepository destinationRepository, ModelMapper modelMapper) {
        this.destinationRepository = destinationRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<DestinationBindingModel> findAll() {
        return this.destinationRepository.findAll()
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

    @Override
    @Transactional
    public Destination addDestination(DestinationBindingModel destinationBindingModel) {
        Destination destination = this.modelMapper.map(destinationBindingModel, Destination.class);

        return this.destinationRepository.save(destination);
    }

    @Override
    @Transactional
    public void editDestination(Long destinationId, DestinationBindingModel destinationBindingModel) throws NotFoundException {
        Destination destination = this.destinationRepository
                .findById(destinationId)
                .orElseThrow(() -> new NotFoundException("Destination with this id was not found!"));

        destination.setName(destinationBindingModel.getName());
        destination.setImgSrc(destinationBindingModel.getImgSrc());
        this.destinationRepository.save(destination);
    }

    @Override
    @Transactional
    public void deleteDestination(Long destinationDeleteId) {
        this.destinationRepository.deleteById(destinationDeleteId);
    }

    @Override
    public List<String> getAllDestinationNames() {
        return this.destinationRepository.findAll()
                .stream()
                .map(Destination::getName)
                .collect(Collectors.toList());
    }

    /*public boolean imageSourceExists(String imgSrc) {
        String path = "classpath:/images/destination/" + imgSrc;
        Path pathCheck = Paths.get(path);

        return Files.exists(pathCheck);
    }*/
}