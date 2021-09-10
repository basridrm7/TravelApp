package basridrm.travelapp.service.implementations;

import basridrm.travelapp.data.entity.Destination;
import basridrm.travelapp.data.entity.Hotel;
import basridrm.travelapp.data.repository.HotelRepository;
import basridrm.travelapp.dto.binding.DestinationBindingModel;
import basridrm.travelapp.dto.binding.HotelBindingModel;
import basridrm.travelapp.service.HotelService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public HotelServiceImpl(HotelRepository hotelRepository, ModelMapper modelMapper) {
        this.hotelRepository = hotelRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<HotelBindingModel> findAll() {
        return this.hotelRepository.findAll()
                .stream()
                .map(hotel -> this.modelMapper.map(hotel, HotelBindingModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public HotelBindingModel findById(Long hotelId) throws NotFoundException {
        return this.hotelRepository.findById(hotelId)
                .map(hotel -> this.modelMapper.map(hotel, HotelBindingModel.class))
                .orElseThrow(() -> new NotFoundException("Cannot find hotel with the given id!"));
    }

    @Override
    @Transactional
    public Hotel addHotel(HotelBindingModel hotelBindingModel) {
        Hotel hotel = this.modelMapper.map(hotelBindingModel, Hotel.class);

        return this.hotelRepository.save(hotel);
    }
}
