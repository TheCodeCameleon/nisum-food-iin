package com.nisum.foodcourt.BaseModal;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@MappedSuperclass
@Where(clause = "is_deleted = 0")
public abstract class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Integer id;

    @Column(name = "is_deleted")
    protected byte isDeleted;

    @Override
    public boolean equals(Object object) {
        if(object == this) return true;

        if(Objects.isNull(object) || getClass() != object.getClass()) return false;

        BaseEntity that = (BaseEntity) object;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(isDeleted, that.isDeleted)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(isDeleted)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("isDeleted", isDeleted)
                .toString();
    }
}
