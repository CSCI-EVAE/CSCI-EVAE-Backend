package fr.ubo.dosi.projectagile.cscievaebackend.services;

import fr.ubo.dosi.projectagile.cscievaebackend.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    public Role saveRole(Role role);
    public Optional<Role> updateRole(Integer id, Role roleDetails);
    public List<Role> listRoles();
    public Optional<Role> getRoleById(Integer id);
    public void deleteRole(Integer id);
}
