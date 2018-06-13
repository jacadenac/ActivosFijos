package asd.prueba.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Alejandro Cadena
 * @version 1.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssetOwner {
    
    private Long assetOwnerId;
    private Area area;
    private Person person;
    private String assetName;
    @JsonIgnore
    private Date createdDate;
    @JsonIgnore
    private Date updatedDate;
    @JsonIgnore
    private String createdByUser;
    @JsonIgnore
    private String updatedByUser;

    public AssetOwner() {
    }

    public AssetOwner(Long assetOwnerId, Area area, Person person, String assetName, Date createdDate, Date updatedDate, String createdByUser, String updatedByUser) {
        this.assetOwnerId = assetOwnerId;
        this.area = area;
        this.person = person;
        this.assetName = assetName;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.createdByUser = createdByUser;
        this.updatedByUser = updatedByUser;
    }

    public Long getAssetOwnerId() {
        return assetOwnerId;
    }

    public void setAssetOwnerId(Long assetOwnerId) {
        this.assetOwnerId = assetOwnerId;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
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
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.assetOwnerId);
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
        final AssetOwner other = (AssetOwner) obj;
        if (!Objects.equals(this.assetOwnerId, other.assetOwnerId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AssetOwner{" + "assetOwnerId=" + assetOwnerId + ", area=" + area + ", person=" + person + ", assetName=" + assetName + ", createdDate=" + createdDate + ", updatedDate=" + updatedDate + ", createdByUser=" + createdByUser + ", updatedByUser=" + updatedByUser + '}';
    }
    
}
