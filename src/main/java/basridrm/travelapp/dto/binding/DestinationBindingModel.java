package basridrm.travelapp.dto.binding;

import javax.validation.constraints.*;

public class DestinationBindingModel {

    private Long id;

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 3, max = 30, message = "Name length must be between 3 and 30 characters")
    private String name;

    @NotBlank(message = "Image source cannot be blank")
    @Pattern(regexp = "([^\\s]+(\\.(?i)(jpe?g|png))$)", message = "Invalid image source")
    private String imgSrc;

    public DestinationBindingModel() {
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

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }
}