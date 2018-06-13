package asd.prueba.model;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Alejandro
 * @version 1.0
 */
public class AuthUserRole {
    
    private Long id;
    private String username;
    private String role;
    private Date createdDate;
    private Date updatedDate;

    public AuthUserRole() {
    }

    public AuthUserRole(Long id, String username, String role, Date createdDate, Date updatedDate) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public String toString() {
        return "AuthUserRole{" + "id=" + id + ", username=" + username + ", role=" + role + ", createdDate=" + createdDate + ", updatedDate=" + updatedDate + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AuthUserRole other = (AuthUserRole) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
}
