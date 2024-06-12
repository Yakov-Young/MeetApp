package com.kemsu.sibiryakov.api.Services;

import com.kemsu.sibiryakov.api.Entities.UserPart.Role;
import com.kemsu.sibiryakov.api.Repositories.IRoleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    private final IRoleRepository roleRepository;
    @Autowired
    public RoleService(IRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> getAllRole() {
        return roleRepository.findAll();
    }

    public Role getByID(Long id) {
        return roleRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }
}
