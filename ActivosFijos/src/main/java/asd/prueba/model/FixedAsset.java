package asd.prueba.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Alejandro Cadena
 * @version 1.0
 */
@JsonInclude(Include.NON_NULL)
public class FixedAsset {
    
    private Long fixedAssetId;
    private AssetOwner assetOwner;
    private Long assetOwnerId;
    private AssetStatus assetStatus;
    private Long assetStatusId;
    private String name;
    private String description;
    private AssetType assetType;
    private Long assetTypeId;
    private String serial;
    private String inventoryNum;
    private BigDecimal weight;
    private BigDecimal high;
    private BigDecimal width;
    @JsonProperty("long")
    private BigDecimal longValue;
    private BigDecimal purchaseValue;
    private Date purchaseDate;
    private Date withdrawalDate;
    private String color;
    @JsonIgnore
    private Date createdDate;
    @JsonIgnore
    private Date updatedDate;
    @JsonIgnore
    private String createdByUser;
    @JsonIgnore
    private String updatedByUser;

    public FixedAsset() {
    }

    public FixedAsset(Long fixedAssetId, AssetOwner assetOwner, Long assetOwnerId, AssetStatus assetStatus, Long assetStatusId, String name, String description, AssetType assetType, Long assetTypeId, String serial, String inventoryNum, BigDecimal weight, BigDecimal high, BigDecimal width, BigDecimal longValue, BigDecimal purchaseValue, Date purchaseDate, Date withdrawalDate, String color, Date createdDate, Date updatedDate, String createdByUser, String updatedByUser) {
        this.fixedAssetId = fixedAssetId;
        this.assetOwner = assetOwner;
        this.assetOwnerId = assetOwnerId;
        this.assetStatus = assetStatus;
        this.assetStatusId = assetStatusId;
        this.name = name;
        this.description = description;
        this.assetType = assetType;
        this.assetTypeId = assetTypeId;
        this.serial = serial;
        this.inventoryNum = inventoryNum;
        this.weight = weight;
        this.high = high;
        this.width = width;
        this.longValue = longValue;
        this.purchaseValue = purchaseValue;
        this.purchaseDate = purchaseDate;
        this.withdrawalDate = withdrawalDate;
        this.color = color;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.createdByUser = createdByUser;
        this.updatedByUser = updatedByUser;
    }

    public Long getFixedAssetId() {
        return fixedAssetId;
    }

    public void setFixedAssetId(Long fixedAssetId) {
        this.fixedAssetId = fixedAssetId;
    }

    public AssetOwner getAssetOwner() {
        return assetOwner;
    }

    public void setAssetOwner(AssetOwner assetOwner) {
        this.assetOwner = assetOwner;
    }

    public AssetStatus getAssetStatus() {
        return assetStatus;
    }

    public void setAssetStatus(AssetStatus assetStatus) {
        this.assetStatus = assetStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AssetType getAssetType() {
        return assetType;
    }

    public void setAssetType(AssetType assetType) {
        this.assetType = assetType;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getInventoryNum() {
        return inventoryNum;
    }

    public void setInventoryNum(String inventoryNum) {
        this.inventoryNum = inventoryNum;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getHigh() {
        return high;
    }

    public void setHigh(BigDecimal high) {
        this.high = high;
    }

    public BigDecimal getWidth() {
        return width;
    }

    public void setWidth(BigDecimal width) {
        this.width = width;
    }

    public BigDecimal getLongValue() {
        return longValue;
    }

    public void setLongValue(BigDecimal longValue) {
        this.longValue = longValue;
    }

    public BigDecimal getPurchaseValue() {
        return purchaseValue;
    }

    public void setPurchaseValue(BigDecimal purchaseValue) {
        this.purchaseValue = purchaseValue;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Date getWithdrawalDate() {
        return withdrawalDate;
    }

    public void setWithdrawalDate(Date withdrawalDate) {
        this.withdrawalDate = withdrawalDate;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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

    public Long getAssetOwnerId() {
        return assetOwnerId;
    }

    public void setAssetOwnerId(Long assetOwnerId) {
        this.assetOwnerId = assetOwnerId;
    }

    public Long getAssetStatusId() {
        return assetStatusId;
    }

    public void setAssetStatusId(Long assetStatusId) {
        this.assetStatusId = assetStatusId;
    }

    public Long getAssetTypeId() {
        return assetTypeId;
    }

    public void setAssetTypeId(Long assetTypeId) {
        this.assetTypeId = assetTypeId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.fixedAssetId);
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
        final FixedAsset other = (FixedAsset) obj;
        if (!Objects.equals(this.fixedAssetId, other.fixedAssetId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "FixedAsset{" + "fixedAssetId=" + fixedAssetId + ", assetOwner=" + assetOwner + ", assetOwnerId=" + assetOwnerId + ", assetStatus=" + assetStatus + ", assetStatusId=" + assetStatusId + ", name=" + name + ", description=" + description + ", assetType=" + assetType + ", assetTypeId=" + assetTypeId + ", serial=" + serial + ", inventoryNum=" + inventoryNum + ", weight=" + weight + ", high=" + high + ", width=" + width + ", longValue=" + longValue + ", purchaseValue=" + purchaseValue + ", purchaseDate=" + purchaseDate + ", withdrawalDate=" + withdrawalDate + ", color=" + color + ", createdDate=" + createdDate + ", updatedDate=" + updatedDate + ", createdByUser=" + createdByUser + ", updatedByUser=" + updatedByUser + '}';
    }
    
}
