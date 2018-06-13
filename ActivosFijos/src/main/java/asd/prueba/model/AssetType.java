package asd.prueba.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Alejandro Cadena
 * @version 1.0
 */
public class AssetType {
    
    private Long assetTypeId;
    private String name;
    @JsonIgnore
    private Date createdDate;
    @JsonIgnore
    private Date updatedDate;
    @JsonIgnore
    private String createdByUser;
    @JsonIgnore
    private String updatedByUser;

    public AssetType() {
    }

    public AssetType(Long assetTypeId, String name, Date createdDate, Date updatedDate, String createdByUser, String updatedByUser) {
        this.assetTypeId = assetTypeId;
        this.name = name;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.createdByUser = createdByUser;
        this.updatedByUser = updatedByUser;
    }

    public Long getAssetTypeId() {
        return assetTypeId;
    }

    public void setAssetTypeId(Long assetTypeId) {
        this.assetTypeId = assetTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getCreatedByUser() {
        return createdByUser;
    }

    public void setCreatedByUser(String createdByUser) {
        this.createdByUser = createdByUser;
    }

    public String getUpdatedByUser() {
        return updatedByUser;
    }

    public void setUpdatedByUser(String updatedByUser) {
        this.updatedByUser = updatedByUser;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.assetTypeId);
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
        final AssetType other = (AssetType) obj;
        if (!Objects.equals(this.assetTypeId, other.assetTypeId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AssetType{" + "assetTypeId=" + assetTypeId + ", name=" + name + ", createdDate=" + createdDate + ", updatedDate=" + updatedDate + ", createdByUser=" + createdByUser + ", updatedByUser=" + updatedByUser + '}';
    }
    
}
