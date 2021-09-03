package basridrm.travelapp.service.implementations;

import basridrm.travelapp.data.entity.User;
import basridrm.travelapp.data.repository.RoleRepository;
import basridrm.travelapp.data.repository.UserRepository;
import basridrm.travelapp.dto.binding.auth.UserRegisterBindingModel;
import basridrm.travelapp.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleServiceImpl roleService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           ModelMapper modelMapper, PasswordEncoder passwordEncoder, RoleServiceImpl roleService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Transactional
    @Override
    public User register(UserRegisterBindingModel userRegisterBindingModel) {

        User user = this.modelMapper.map(userRegisterBindingModel, User.class);
        if(userRegisterBindingModel.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userRegisterBindingModel.getPassword()));
        }

        user.setRoles(Set.of(roleRepository.findByName("USER").get()));

        return userRepository.save(user);
    }

    public User getPrincipal() {
        User user = null;
        if(SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal() instanceof User) {
            user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        return user;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    public boolean usernameExists(String username){
        return userRepository.findByUsername(username).isPresent();
    }

    @Override
    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}