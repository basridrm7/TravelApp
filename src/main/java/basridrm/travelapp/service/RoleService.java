package basridrm.travelapp.service;

import basridrm.travelapp.data.entity.Role;
import javax.management.relation.RoleNotFoundException;

public interface RoleService {
    Role findRoleByName(String name) throws RoleNotFoundException;
}