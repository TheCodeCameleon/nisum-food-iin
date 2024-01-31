package com.nisum.foodcourt.service;

import com.nisum.foodcourt.model.RoleDto;
import org.springframework.http.ResponseEntity;

public interface RoleService {

    /**
     * @param id
     * @return ResponseEntity returning Role
     */

    ResponseEntity<?> getRoleById(Integer id);

    /**
     * @return ResponseEntity returning List of all roles
     */
    ResponseEntity<?> getAllRoles();

    /**
     * @param id
     * @param roleDTO
     * @return
     */

    ResponseEntity<?> updateRole(Integer id, RoleDto roleDTO);

    /**
     * @param id
     * @return
     */

    ResponseEntity<?> deleteRole(Integer id);
}
