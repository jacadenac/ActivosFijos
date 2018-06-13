package asd.prueba.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Alejandro Cadena
 * @version 1.0
 */
public class City {
    
    private Long cityId;
    private String cityCode;
    private String name;
    @JsonIgnore
    private Date createdDate;
    @JsonIgnore
    private Date updatedDate;

    public City() {
    }

    public City(Long cityId, String cityCode, String name, Date createdDate, Date updatedDate) {
        this.cityId = cityId;
        this.cityCode = cityCode;
        this.name = name;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.cityId);
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
        final City other = (City) obj;
        if (!Objects.equals(this.cityId, other.cityId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "City{" + "cityId=" + cityId + ", cityCode=" + cityCode + ", name=" + name + ", createdDate=" + createdDate + ", updatedDate=" + updatedDate + '}';
    }
    
}
