package basridrm.travelapp.service;

import basridrm.travelapp.data.entity.Hotel;
import basridrm.travelapp.dto.binding.HotelBindingModel;
import javassist.NotFoundException;

import java.util.List;

public interface HotelService {
    List<HotelBindingModel> findAll();
    HotelBindingModel findById(Long hotelId) throws NotFoundException;
    List<HotelBindingModel> findAllByDestination(Long destinationId);
    Hotel addHotel(HotelBindingModel hotelBindingModel);
    void editHotel(Long hotelId, HotelBindingModel hotelBindingModel) throws NotFoundException;
    void deleteHotel(Long hotelDeleteId);
}