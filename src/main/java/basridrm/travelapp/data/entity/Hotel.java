package basridrm.travelapp.data.entity;

import basridrm.travelapp.data.entity.base.BaseEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "hotels")
public class Hotel extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(targetEntity = Destination.class)
    @JoinColumn(name = "destination_id", referencedColumnName = "id")
    private Destination destination;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "description", length = 1000, nullable = false)
    private String description;

    @Column(name = "image_source", nullable = false, unique = true)
    private String imgSrc;

    @Column(name = "classic_price", nullable = false)
    private BigDecimal classicRoomPricePerNight;

    @Column(name = "deluxe_price", nullable = false)
    private BigDecimal deluxeRoomPricePerNight;

    @Column(name = "suite_price", nullable = false)
    private BigDecimal suiteRoomPricePerNight;


    @OneToMany(targetEntity = Room.class, fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "hotel_id", referencedColumnName = "id")
    private List<Room> rooms;

    @PreRemove
    private void removeHotelFromDestination() {
        this.destination.getHotels().remove(this);
    }

    public Hotel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
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