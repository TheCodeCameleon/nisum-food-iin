package com.nisum.foodcourt.entity;

import com.nisum.foodcourt.BaseModal.BaseEntity;
import com.nisum.foodcourt.resource.constant.RoleName;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "role_name")
    RoleName roleName;

    @Column(name = "description")
    String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        return new EqualsBuilder()
                .append(id,role.id)
                .append(roleName, role.roleName)
                .append(description, role.description)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(roleName)
                .append(description)
                .toHashCode();
    }
}
