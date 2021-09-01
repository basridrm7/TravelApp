package basridrm.travelapp.dto.binding.auth;

import basridrm.travelapp.validation.PasswordValidator;

import javax.validation.constraints.*;

@PasswordValidator(pass = "password",
        confPass = "confirmPassword",
        message = "The passwords do not match")
public class UserRegisterBindingModel {

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 2,max = 15, message = "Name length must be between 2 and 15 characters")
    private String name;

    @NotBlank(message = "Surname cannot be blank")
    @Size(min = 2,max = 15, message = "Surname length must be between 2 and 15 characters")
    private String surname;

    @NotBlank(message = "City cannot be blank")
    @Size(min = 3,max = 15, message = "City length must be between 3 and 15 characters")
    private String city;

    @NotBlank(message = "Username cannot be blank")
    @Size(min = 3,max = 20, message = "Username length must be between 3 and 20 characters")
    private String username;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Please enter a valid email address")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 3,max = 20, message = "Password length must be between 3 and 20 characters")
    private String password;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 3,max = 20, message = "Password length must be between 3 and 20 characters")
    private String confirmPassword;

    public UserRegisterBindingModel() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return this.confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public String toString() {
        return "UserRegisterBindingModel{" +
                "username='" + this.username + '\'' +
                ", password='" + this.password + '\'' +
                ", confirmPassword='" + this.confirmPassword + '\'' +
                ", name='" + this.name + '\'' +
                ", surname='" + this.surname + '\'' +
                ", email='" + this.email + '\'' +
                ", city='" + this.city + '\'' +
                '}';
    }
}