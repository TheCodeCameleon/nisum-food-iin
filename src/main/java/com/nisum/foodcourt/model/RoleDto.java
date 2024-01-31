package com.nisum.foodcourt.model;

import com.nisum.foodcourt.resource.constant.RoleName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {

    Integer id;

    RoleName roleName;

    String description;
}
