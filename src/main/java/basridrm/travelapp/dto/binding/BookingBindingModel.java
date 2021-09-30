package basridrm.travelapp.dto.binding;

import basridrm.travelapp.data.entity.Room;
import basridrm.travelapp.data.entity.User;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

public class BookingBindingModel {

    private Long id;

    private User user;

    @NotBlank(message = "First Name cannot be blank")
    @Size(min = 2,max = 15, message = "First Name length must be between 2 and 15 characters")
    private String name;

    @NotBlank(message = "Last Name cannot be blank")
    @Size(min = 2,max = 15, message = "Last Name length must be between 2 and 15 characters")
    private String surname;

    @Future(message = "Please select a date in the future")
    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE)
    private LocalDate checkIn;

    @Future(message = "Please select a date in the future")
    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE)
    private LocalDate checkOut;

    private Room room;

    private Integer guests;

    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(regexp = "([ 0-9]){8,11}$", message = "Invalid phone number")
    @Size(max = 10 ,message = "Phone number should be maximum of 10 digits")
    private String phoneNumber;

    private BigDecimal totalPrice;

    public BookingBindingModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Integer getGuests() {
        return guests;
    }

    public void setGuests(Integer guests) {
        this.guests = guests;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}