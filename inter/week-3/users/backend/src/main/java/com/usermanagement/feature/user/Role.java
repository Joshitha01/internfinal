package com.usermanagement.feature.user;

import javax.persistence.*;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * A <code>{@link Role}</code> object represents a specific user's role
 * in the application. The <code>Role</code> object has it's own role type
 * defined in {@link #roleType}
 *
 * @author Timur Berezhnoi
 */
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @Column(unique = true)
    @Enumerated(value = EnumType.STRING)
    private RoleType roleType;

    public Role(RoleType roleType) {
        this.roleType = roleType;
    }

    public Role() {}

    public Integer getId() {
        return id;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    /**
     * This enum provides constants of roles types for a {@link Role}
     * that can be used across the application.
     * <p>
     * This enum should be consistent with database table
     * where roles stored. Whenever a new role should be
     * added to the application, write migration script
     * then also add the new role in this enum.
     *
     * @author Timur Berezhnoi
     * @see Role
     */
    public enum RoleType {
        /**
         * Specifies an admin that can fully manage all the application.
         *
         * A user with this role has full access to all the functionality.
         */
        ROLE_APPLICATION_ADMIN,
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Role role = (Role) o;
        return roleType == role.roleType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleType);
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", role='" + roleType + '\'' +
                '}';
    }
}
