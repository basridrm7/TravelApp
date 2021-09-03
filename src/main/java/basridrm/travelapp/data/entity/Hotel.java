package basridrm.travelapp.data.entity;

import basridrm.travelapp.data.entity.base.BaseEntity;

import javax.persistence.*;
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

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "image_source", nullable = false, unique = true)
    private String imgSrc;

    @ManyToMany(targetEntity = Room.class, fetch = FetchType.EAGER)
    @JoinTable(
            name = "hotel_rooms",
            joinColumns = @JoinColumn(
                    name = "hotel_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "room_id",
                    referencedColumnName = "id"
            )
    )
    private List<Room> rooms;

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

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
}