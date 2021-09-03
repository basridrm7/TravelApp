package basridrm.travelapp.dto.binding;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class DestinationBindingModel {

    private Long id;

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 3, max = 15, message = "Name must be between 3 and 15 characters")
    private String name;

    @NotEmpty(message = "Image source cannot be blank")
    private String imgSrc;

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