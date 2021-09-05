package basridrm.travelapp.data.entity;

import basridrm.travelapp.data.entity.base.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "destinations")
public class Destination extends BaseEntity {

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "image_source", nullable = false, unique = true)
    private String imgSrc;

    @OneToMany(mappedBy = "destination", targetEntity = Hotel.class,
            fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Hotel> hotels;

    public Destination() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public List<Hotel> getHotels() {
        return hotels;
    }

    public void setHotels(List<Hotel> hotels) {
        this.hotels = hotels;
    }
}