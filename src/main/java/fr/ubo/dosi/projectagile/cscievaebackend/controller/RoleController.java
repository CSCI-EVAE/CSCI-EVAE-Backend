package fr.ubo.dosi.projectagile.cscievaebackend.controller;

import fr.ubo.dosi.projectagile.cscievaebackend.exception.ResourceNotFoundException;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Role;
import fr.ubo.dosi.projectagile.cscievaebackend.services.Impl.RoleServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import fr.ubo.dosi.projectagile.cscievaebackend.ResponceHandler.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
@PreAuthorize("hasAuthority('ADMIN')")
public class RoleController {

    @Autowired
    private RoleServiceImpl roleService;

    // Create a new Role
    @PostMapping
    public ResponseEntity<ApiResponse<Role>> createRole(@RequestBody Role role) {
        Role createdRole = roleService.saveRole(role);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(createdRole));
    }

    // Read all Roles
    @GetMapping
    public ResponseEntity<ApiResponse<List<Role>>> getAllRoles() {
        List<Role> roles = roleService.listRoles();
        return ResponseEntity.ok(ApiResponse.ok(roles));
    }

    // Read a Role by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Role>> getRoleById(@PathVariable Integer id) {
        try {
            Role role = roleService.getRoleById(id).orElseThrow(() -> new ResourceNotFoundException("Role not found"));
            return ResponseEntity.ok(ApiResponse.ok(role));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Role not found", null));
        }
    }

    // Update a Role
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Role>> updateRole(@PathVariable Integer id, @RequestBody Role role) throws ResourceNotFoundException {
        Role updatedRole = roleService.updateRole(id, role).orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        return ResponseEntity.ok(ApiResponse.ok(updatedRole));
    }

    // Delete a Role by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteRole(@PathVariable Integer id) {
        roleService.deleteRole(id);
        return ResponseEntity.ok(ApiResponse.ok(null));
    }
}
