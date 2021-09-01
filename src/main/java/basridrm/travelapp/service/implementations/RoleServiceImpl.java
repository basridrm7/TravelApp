package basridrm.travelapp.service.implementations;

import basridrm.travelapp.data.entity.Role;
import basridrm.travelapp.data.repository.RoleRepository;
import basridrm.travelapp.service.RoleService;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findRoleByName(String name) throws RoleNotFoundException {
        return this.roleRepository.findByName(name)
                .orElseThrow(() -> new RoleNotFoundException("Role with given name was not found!"));
    }
}