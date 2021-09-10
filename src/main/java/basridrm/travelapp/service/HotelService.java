package basridrm.travelapp.service;

import basridrm.travelapp.data.entity.Hotel;
import basridrm.travelapp.dto.binding.HotelBindingModel;
import javassist.NotFoundException;

import java.util.List;

public interface HotelService {
    List<HotelBindingModel> findAll();
    HotelBindingModel findById(Long hotelId) throws NotFoundException;
    Hotel addHotel(HotelBindingModel hotelBindingModel);
}