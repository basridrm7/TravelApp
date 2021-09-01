package basridrm.travelapp;

import basridrm.travelapp.data.entity.Role;
import basridrm.travelapp.data.entity.User;
import basridrm.travelapp.data.repository.RoleRepository;
import basridrm.travelapp.data.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class TravelAppInitialData implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public TravelAppInitialData(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        if(userRepository.count() == 0) {

            Role roleAdmin = new Role();
            roleAdmin.setName("ADMIN");
            roleRepository.save(roleAdmin);

            Role roleUser = new Role();
            roleUser.setName("USER");
            roleRepository.save(roleUser);

            // ADMIN PROFILE
            User admin = new User();
            admin.setUsername("admin");
            admin.setName("Admin");
            admin.setSurname("Admin");
            admin.setEmail("admin@example.com");
            admin.setCity("Sofia");
            admin.setPassword(passwordEncoder.encode("admin111"));
            admin.setRoles(Set.of(roleAdmin, roleUser));
            userRepository.save(admin);

            // USER PROFILE
            User user = new User();
            user.setUsername("user");
            user.setName("User");
            user.setSurname("User");
            user.setEmail("user@example.com");
            user.setCity("Sofia");
            user.setPassword(passwordEncoder.encode("user111"));
            user.setRoles(Set.of(roleUser));
            userRepository.save(user);
        }
    }
}