package basridrm.travelapp.dto.binding;

import basridrm.travelapp.data.entity.Room;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

public class HotelBindingModel {

    private Long id;

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 3, max = 40, message = "Name length must be between 3 and 40 characters")
    private String name;

    @NotNull(message = "Destination cannot be blank")
    private DestinationBindingModel destination;

    @NotBlank(message = "Address cannot be blank")
    @Size(min = 5, max = 70, message = "Address length must be between 5 and 70 characters")
    private String address;

    @NotBlank(message = "Description cannot be blank")
    @Size(min = 10, max = 300, message = "Description length must be between 10 and 300 characters")
    private String description;

    @NotBlank(message = "Image source cannot be blank")
    @Pattern(regexp = "([^\\s]+(\\.(?i)(jpe?g|png))$)", message = "Invalid image source")
    private String imgSrc;

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.00", inclusive = false)
    private BigDecimal classicRoomPricePerNight;

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.00", inclusive = false)
    private BigDecimal deluxeRoomPricePerNight;

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.00", inclusive = false)
    private BigDecimal suiteRoomPricePerNight;

    private List<Room> rooms;

    public HotelBindingModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DestinationBindingModel getDestination() {
        return destination;
    }

    public void setDestination(DestinationBindingModel destination) {
        this.destination = destination;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public BigDecimal getClassicRoomPricePerNight() {
        return classicRoomPricePerNight;
    }

    public void setClassicRoomPricePerNight(BigDecimal classicRoomPricePerNight) {
        this.classicRoomPricePerNight = classicRoomPricePerNight;
    }

    public BigDecimal getDeluxeRoomPricePerNight() {
        return deluxeRoomPricePerNight;
    }

    public void setDeluxeRoomPricePerNight(BigDecimal deluxeRoomPricePerNight) {
        this.deluxeRoomPricePerNight = deluxeRoomPricePerNight;
    }

    public BigDecimal getSuiteRoomPricePerNight() {
        return suiteRoomPricePerNight;
    }

    public void setSuiteRoomPricePerNight(BigDecimal suiteRoomPricePerNight) {
        this.suiteRoomPricePerNight = suiteRoomPricePerNight;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
}