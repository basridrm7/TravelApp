package basridrm.travelapp.service;

import basridrm.travelapp.data.entity.User;
import basridrm.travelapp.dto.binding.auth.UserRegisterBindingModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    boolean usernameExists(String username);
    boolean emailExists(String email);
    User register(UserRegisterBindingModel userRegisterBindingModel);
}