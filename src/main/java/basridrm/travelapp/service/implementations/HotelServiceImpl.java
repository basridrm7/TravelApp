package basridrm.travelapp.service.implementations;

import basridrm.travelapp.data.entity.*;
import basridrm.travelapp.data.repository.HotelRepository;
import basridrm.travelapp.data.repository.RoomRepository;
import basridrm.travelapp.dto.binding.HotelBindingModel;
import basridrm.travelapp.service.HotelService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;
    private final RoomRepository roomRepository;

    @Autowired
    public HotelServiceImpl(HotelRepository hotelRepository, ModelMapper modelMapper, RoomRepository roomRepository) {
        this.hotelRepository = hotelRepository;
        this.modelMapper = modelMapper;
        this.roomRepository = roomRepository;
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
    public List<HotelBindingModel> findAllByDestination(Long destinationId) {
        return this.hotelRepository.findAllByDestination_Id(destinationId)
                .stream()
                .map(hotel -> this.modelMapper.map(hotel, HotelBindingModel.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Hotel addHotel(HotelBindingModel hotelBindingModel) {
        Hotel hotel = this.modelMapper.map(hotelBindingModel, Hotel.class);
        setRoomsInHotel(hotel);

        return this.hotelRepository.save(hotel);
    }

    public void setRoomsInHotel(Hotel hotel) {
        List<Room> rooms = new ArrayList<>();

        Room roomClassic = new Room();
        roomClassic.setRoomType(RoomType.Classic);
        roomClassic.setPricePerNight(hotel.getClassicRoomPricePerNight());
        roomClassic.setRoomStatus(RoomStatus.AVAILABLE);
        roomClassic.setDescription("Room of 35m2 in size for 2 people with two single beds " +
                "or a large double bed and an en-suite bathroom.");
        rooms.add(roomClassic);


        Room roomDeluxe = new Room();
        roomDeluxe.setRoomType(RoomType.Deluxe);
        roomDeluxe.setPricePerNight(hotel.getDeluxeRoomPricePerNight());
        roomDeluxe.setRoomStatus(RoomStatus.AVAILABLE);
        roomDeluxe.setDescription("Room of 50m2 in size for 2/3 people with one King-Size bed " +
                "and one single bed and an en-suite bathroom.");
        rooms.add(roomDeluxe);

        Room roomSuite = new Room();
        roomSuite.setRoomType(RoomType.Suite);
        roomSuite.setPricePerNight(hotel.getSuiteRoomPricePerNight());
        roomSuite.setRoomStatus(RoomStatus.AVAILABLE);
        roomSuite.setDescription("Room of 80m2 in size for 3/4 people with two King-Size beds " +
                "and one single bed and a living room with sofa.");
        rooms.add(roomSuite);

        hotel.setRooms(rooms);
    }

    @Override
    @Transactional
    public void editHotel(Long hotelId, HotelBindingModel hotelBindingModel) throws NotFoundException {
        Hotel hotel = this.hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new NotFoundException("Hotel with this id was not found!"));

        hotel.setName(hotelBindingModel.getName());
        hotel.setDestination(this.modelMapper.map(hotelBindingModel.getDestination(), Destination.class));
        hotel.setAddress(hotelBindingModel.getAddress());
        hotel.setDescription(hotelBindingModel.getDescription());
        hotel.setImgSrc(hotelBindingModel.getImgSrc());

        hotel.setClassicRoomPricePerNight(hotelBindingModel.getClassicRoomPricePerNight());
        hotel.setDeluxeRoomPricePerNight(hotelBindingModel.getDeluxeRoomPricePerNight());
        hotel.setSuiteRoomPricePerNight(hotelBindingModel.getSuiteRoomPricePerNight());
        hotel.getRooms().get(0).setPricePerNight(hotelBindingModel.getClassicRoomPricePerNight());
        hotel.getRooms().get(1).setPricePerNight(hotelBindingModel.getDeluxeRoomPricePerNight());
        hotel.getRooms().get(2).setPricePerNight(hotelBindingModel.getSuiteRoomPricePerNight());

        this.hotelRepository.save(hotel);
    }

    @Override
    @Transactional
    public void deleteHotel(Long hotelDeleteId) {
        this.hotelRepository.deleteById(hotelDeleteId);
    }

}