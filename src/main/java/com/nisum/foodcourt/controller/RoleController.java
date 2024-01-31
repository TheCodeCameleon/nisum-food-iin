package com.nisum.foodcourt.controller;

import com.nisum.foodcourt.model.RoleDto;
import com.nisum.foodcourt.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getRoleById(@PathVariable Integer id) {
        return roleService.getRoleById(id);
    }

    @GetMapping
    public ResponseEntity<?> getAllRoles() {
        return roleService.getAllRoles();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRole(@PathVariable Integer id, @RequestBody RoleDto roleDTO) {
        return roleService.updateRole(id, roleDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable Integer id) {
        return roleService.deleteRole(id);
    }
}
