package com.nisum.foodcourt.BaseModal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "is_deleted = 0")
@MappedSuperclass
public abstract class BaseAuditableEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Integer id;

    @Column(name = "is_deleted")
    protected byte isDeleted = 0;

    @CreatedBy
    protected Integer createdBy;

    @CreatedDate
    protected Timestamp createdAt;

    @LastModifiedBy
    protected Integer updatedBy;

    @LastModifiedDate
    protected Timestamp updatedAt;

    @Override
    public boolean equals(Object object) {
        if (object == this) return true;

        if (Objects.isNull(object) || getClass() != object.getClass()) return false;

        BaseAuditableEntity that = (BaseAuditableEntity) object;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(isDeleted, that.isDeleted)
                .append(createdAt, that.createdAt)
                .append(createdBy, that.createdBy)
                .append(updatedBy, that.updatedBy)
                .append(updatedAt, that.updatedAt)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(isDeleted)
                .append(createdAt)
                .append(createdBy)
                .append(updatedBy)
                .append(updatedAt)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("isDeleted", isDeleted)
                .append("createdAt", createdAt)
                .append("createdBy", createdBy)
                .append("updatedBy", updatedBy)
                .append("updatedAt", updatedAt)
                .toString();
    }
}
