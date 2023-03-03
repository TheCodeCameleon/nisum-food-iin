package com.nisum.foodcourt.entity;

import com.nisum.foodcourt.BaseModal.BaseEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.time.DateUtils;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "user_name"
        }),
        @UniqueConstraint(columnNames = {
                "email"
        })
})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

/**
 * @author Asad Khan
 */
public class User extends BaseEntity {

    @NotBlank
    @Column(name = "user_name")
    String userName;

    @Email
    @Column(name = "email")
    String email;

    @NotBlank
    @Size(min = 4, max = 100)
    @Column(name = "password")
    String password;

    @Column(name = "full_name")
    String fullName;

    @Size(max = 13)
    @Column(name = "contact_no")
    String contactNo;

    @Column(name = "employee_id")
    String employeeId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "floor_id")
    Floor floor;

    int status;

    @Column(name = "created_at")
    Timestamp createdAt;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    Set<Role> roles = new HashSet<>();

    @Column(name = "is_profileSet")
    int isProfileSet = 0;

    @PrePersist
    protected void onUserPersist() {
        createdAt = new Timestamp(new Date().getTime());
    }

}
