package asd.prueba.model;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Alejandro
 * @version 1.0
 */
public class AuthUser {
    
    private String username;
    private String password;
    private String email;
    private String fullname;
    private boolean enabled;
    private Date createdDate;
    private Date updatedDate;

    public AuthUser() {
    }

    public AuthUser(String username, String password, String email, String fullname, boolean enabled, Date createdDate, Date updatedDate) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullname = fullname;
        this.enabled = enabled;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
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
        return "AuthUser{" + "username=" + username + ", password=" + password + ", email=" + email + ", enabled=" + enabled + ", createdDate=" + createdDate + ", updatedDate=" + updatedDate + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.username);
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
        final AuthUser other = (AuthUser) obj;
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        return true;
    }
    
}
