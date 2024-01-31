package com.nisum.foodcourt.service;

import com.nisum.foodcourt.entity.Role;
import com.nisum.foodcourt.model.RoleDto;
import com.nisum.foodcourt.repository.RoleRepository;
import com.nisum.foodcourt.resource.util.ConversionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    private final ConversionUtil conversionUtil;

    @Override
    public ResponseEntity<?> getRoleById(Integer id) {
        Optional<Role> role = roleRepository.findById(id);

        return role.isPresent() ?
                ResponseEntity.ok(role.get()) :
                ResponseEntity.badRequest().body("Role not found");

    }

    @Override
    public ResponseEntity<?> getAllRoles() {
        List<Role> roleList = roleRepository.findAll();
        return roleList.isEmpty() ?
                ResponseEntity.badRequest().body("Roles not found") :
                ResponseEntity.ok(roleList);
    }

    @Override
    public ResponseEntity<?> updateRole(Integer id, RoleDto roleDTO) {
        return null;
    }

    @Override
    public ResponseEntity<?> deleteRole(Integer id) {
        return null;
    }
}
