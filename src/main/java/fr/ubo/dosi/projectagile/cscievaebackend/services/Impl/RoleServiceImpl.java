package fr.ubo.dosi.projectagile.cscievaebackend.services.Impl;

import fr.ubo.dosi.projectagile.cscievaebackend.model.Role;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.RoleRepository;
import fr.ubo.dosi.projectagile.cscievaebackend.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role saveRole(Role role) {
        if (roleRepository.existsByName(role.getName())) {
            throw new IllegalArgumentException("Role with name " + role.getName() + " already exists");
        }
        return roleRepository.save(role);
    }

    public Optional<Role> updateRole(Integer id, Role roleDetails) {
        return roleRepository.findById(id).map(role -> {
            role.setName(roleDetails.getName());
            return roleRepository.save(role);
        });
    }

    public List<Role> listRoles() {
        return roleRepository.findAll();
    }

    public Optional<Role> getRoleById(Integer id) {
        return roleRepository.findById(id);
    }

    public void deleteRole(Integer id) {
        roleRepository.deleteById(id);
    }
}
