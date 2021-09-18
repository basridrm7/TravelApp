package basridrm.travelapp.data.entity;

import basridrm.travelapp.data.entity.base.BaseEntity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "rooms")
public class Room extends BaseEntity {

    @Column(name = "room_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    @Column(name = "price", nullable = false)
    private BigDecimal pricePerNight;

    @Column(name = "room_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private RoomStatus roomStatus;

    @Column(name = "description", nullable = false)
    private String description;

    public Room() {
    }

    public Room(RoomType roomType, BigDecimal pricePerNight,
                RoomStatus roomStatus, String description) {
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
        this.roomStatus = roomStatus;
        this.description = description;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public BigDecimal getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(BigDecimal price) {
        this.pricePerNight = price;
    }

    public RoomStatus getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(RoomStatus roomStatus) {
        this.roomStatus = roomStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}