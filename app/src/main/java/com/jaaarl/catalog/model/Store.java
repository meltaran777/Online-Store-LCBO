package com.jaaarl.catalog.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "is_dead",
        "name",
        "tags",
        "address_line_1",
        "address_line_2",
        "city",
        "postal_code",
        "telephone",
        "fax",
        "latitude",
        "longitude",
        "products_count",
        "inventory_count",
        "inventory_price_in_cents",
        "inventory_volume_in_milliliters",
        "has_wheelchair_accessability",
        "has_bilingual_services",
        "has_product_consultant",
        "has_tasting_bar",
        "has_beer_cold_room",
        "has_special_occasion_permits",
        "has_vintages_corner",
        "has_parking",
        "has_transit_access",
        "sunday_open",
        "sunday_close",
        "monday_open",
        "monday_close",
        "tuesday_open",
        "tuesday_close",
        "wednesday_open",
        "wednesday_close",
        "thursday_open",
        "thursday_close",
        "friday_open",
        "friday_close",
        "saturday_open",
        "saturday_close",
        "updated_at",
        "store_no"
})
@Entity(tableName = "store")
public class Store implements Serializable, Model{

    @ColumnInfo(name = "id")
    @JsonProperty("id")
    @PrimaryKey
    private Integer id;
    @JsonProperty("is_dead")
    private Boolean isDead;
    @JsonProperty("name")
    private String name;
    @JsonProperty("tags")
    private String tags;
    @SerializedName("address_line_1")
    @Expose
    @JsonProperty("address_line_1")
    private String addressLine1;
    @JsonProperty("address_line_2")
    private String addressLine2;
    @JsonProperty("city")
    private String city;
    @JsonProperty("postal_code")
    private String postalCode;
    @JsonProperty("telephone")
    private String telephone;
    @JsonProperty("fax")
    private String fax;
    @JsonProperty("latitude")
    private Double latitude;
    @JsonProperty("longitude")
    private Double longitude;
    @JsonProperty("products_count")
    private Integer productsCount;
    @JsonProperty("inventory_count")
    private Integer inventoryCount;
    @JsonProperty("inventory_price_in_cents")
    private Integer inventoryPriceInCents;
    @JsonProperty("inventory_volume_in_milliliters")
    private Integer inventoryVolumeInMilliliters;
    @JsonProperty("has_wheelchair_accessability")
    private Boolean hasWheelchairAccessability;
    @JsonProperty("has_bilingual_services")
    private Boolean hasBilingualServices;
    @JsonProperty("has_product_consultant")
    private Boolean hasProductConsultant;
    @JsonProperty("has_tasting_bar")
    private Boolean hasTastingBar;
    @JsonProperty("has_beer_cold_room")
    private Boolean hasBeerColdRoom;
    @JsonProperty("has_special_occasion_permits")
    private Boolean hasSpecialOccasionPermits;
    @JsonProperty("has_vintages_corner")
    private Boolean hasVintagesCorner;
    @JsonProperty("has_parking")
    private Boolean hasParking;
    @JsonProperty("has_transit_access")
    private Boolean hasTransitAccess;
    @JsonProperty("sunday_open")
    private Integer sundayOpen;
    @JsonProperty("sunday_close")
    private Integer sundayClose;
    @JsonProperty("monday_open")
    private Integer mondayOpen;
    @JsonProperty("monday_close")
    private Integer mondayClose;
    @JsonProperty("tuesday_open")
    private Integer tuesdayOpen;
    @JsonProperty("tuesday_close")
    private Integer tuesdayClose;
    @JsonProperty("wednesday_open")
    private Integer wednesdayOpen;
    @JsonProperty("wednesday_close")
    private Integer wednesdayClose;
    @JsonProperty("thursday_open")
    private Integer thursdayOpen;
    @JsonProperty("thursday_close")
    private Integer thursdayClose;
    @JsonProperty("friday_open")
    private Integer fridayOpen;
    @JsonProperty("friday_close")
    private Integer fridayClose;
    @JsonProperty("saturday_open")
    private Integer saturdayOpen;
    @JsonProperty("saturday_close")
    private Integer saturdayClose;
    @JsonProperty("updated_at")
    private String updatedAt;
    @JsonProperty("store_no")
    private Integer storeNo;
    @JsonIgnore
    @Ignore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonIgnore
    @NotNull
    private boolean favorite = false;

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("is_dead")
    public Boolean getIsDead() {
        return isDead;
    }

    @JsonProperty("is_dead")
    public void setIsDead(Boolean isDead) {
        this.isDead = isDead;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("tags")
    public String getTags() {
        return tags;
    }

    @JsonProperty("tags")
    public void setTags(String tags) {
        this.tags = tags;
    }

    @JsonProperty("address_line_1")
    public String getAddressLine1() {
        return addressLine1;
    }

    @JsonProperty("address_line_1")
    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    @JsonProperty("address_line_2")
    public String getAddressLine2() {
        return addressLine2;
    }

    @JsonProperty("address_line_2")
    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    @JsonProperty("city")
    public String getCity() {
        return city;
    }

    @JsonProperty("city")
    public void setCity(String city) {
        this.city = city;
    }

    @JsonProperty("postal_code")
    public String getPostalCode() {
        return postalCode;
    }

    @JsonProperty("postal_code")
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @JsonProperty("telephone")
    public String getTelephone() {
        return telephone;
    }

    @JsonProperty("telephone")
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @JsonProperty("fax")
    public String getFax() {
        return fax;
    }

    @JsonProperty("fax")
    public void setFax(String fax) {
        this.fax = fax;
    }

    @JsonProperty("latitude")
    public Double getLatitude() {
        return latitude;
    }

    @JsonProperty("latitude")
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @JsonProperty("longitude")
    public Double getLongitude() {
        return longitude;
    }

    @JsonProperty("longitude")
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @JsonProperty("products_count")
    public Integer getProductsCount() {
        return productsCount;
    }

    @JsonProperty("products_count")
    public void setProductsCount(Integer productsCount) {
        this.productsCount = productsCount;
    }

    @JsonProperty("inventory_count")
    public Integer getInventoryCount() {
        return inventoryCount;
    }

    @JsonProperty("inventory_count")
    public void setInventoryCount(Integer inventoryCount) {
        this.inventoryCount = inventoryCount;
    }

    @JsonProperty("inventory_price_in_cents")
    public Integer getInventoryPriceInCents() {
        return inventoryPriceInCents;
    }

    @JsonProperty("inventory_price_in_cents")
    public void setInventoryPriceInCents(Integer inventoryPriceInCents) {
        this.inventoryPriceInCents = inventoryPriceInCents;
    }

    @JsonProperty("inventory_volume_in_milliliters")
    public Integer getInventoryVolumeInMilliliters() {
        return inventoryVolumeInMilliliters;
    }

    @JsonProperty("inventory_volume_in_milliliters")
    public void setInventoryVolumeInMilliliters(Integer inventoryVolumeInMilliliters) {
        this.inventoryVolumeInMilliliters = inventoryVolumeInMilliliters;
    }

    @JsonProperty("has_wheelchair_accessability")
    public Boolean getHasWheelchairAccessability() {
        return hasWheelchairAccessability;
    }

    @JsonProperty("has_wheelchair_accessability")
    public void setHasWheelchairAccessability(Boolean hasWheelchairAccessability) {
        this.hasWheelchairAccessability = hasWheelchairAccessability;
    }

    @JsonProperty("has_bilingual_services")
    public Boolean getHasBilingualServices() {
        return hasBilingualServices;
    }

    @JsonProperty("has_bilingual_services")
    public void setHasBilingualServices(Boolean hasBilingualServices) {
        this.hasBilingualServices = hasBilingualServices;
    }

    @JsonProperty("has_product_consultant")
    public Boolean getHasProductConsultant() {
        return hasProductConsultant;
    }

    @JsonProperty("has_product_consultant")
    public void setHasProductConsultant(Boolean hasProductConsultant) {
        this.hasProductConsultant = hasProductConsultant;
    }

    @JsonProperty("has_tasting_bar")
    public Boolean getHasTastingBar() {
        return hasTastingBar;
    }

    @JsonProperty("has_tasting_bar")
    public void setHasTastingBar(Boolean hasTastingBar) {
        this.hasTastingBar = hasTastingBar;
    }

    @JsonProperty("has_beer_cold_room")
    public Boolean getHasBeerColdRoom() {
        return hasBeerColdRoom;
    }

    @JsonProperty("has_beer_cold_room")
    public void setHasBeerColdRoom(Boolean hasBeerColdRoom) {
        this.hasBeerColdRoom = hasBeerColdRoom;
    }

    @JsonProperty("has_special_occasion_permits")
    public Boolean getHasSpecialOccasionPermits() {
        return hasSpecialOccasionPermits;
    }

    @JsonProperty("has_special_occasion_permits")
    public void setHasSpecialOccasionPermits(Boolean hasSpecialOccasionPermits) {
        this.hasSpecialOccasionPermits = hasSpecialOccasionPermits;
    }

    @JsonProperty("has_vintages_corner")
    public Boolean getHasVintagesCorner() {
        return hasVintagesCorner;
    }

    @JsonProperty("has_vintages_corner")
    public void setHasVintagesCorner(Boolean hasVintagesCorner) {
        this.hasVintagesCorner = hasVintagesCorner;
    }

    @JsonProperty("has_parking")
    public Boolean getHasParking() {
        return hasParking;
    }

    @JsonProperty("has_parking")
    public void setHasParking(Boolean hasParking) {
        this.hasParking = hasParking;
    }

    @JsonProperty("has_transit_access")
    public Boolean getHasTransitAccess() {
        return hasTransitAccess;
    }

    @JsonProperty("has_transit_access")
    public void setHasTransitAccess(Boolean hasTransitAccess) {
        this.hasTransitAccess = hasTransitAccess;
    }

    @JsonProperty("sunday_open")
    public Integer getSundayOpen() {
        return sundayOpen;
    }

    @JsonProperty("sunday_open")
    public void setSundayOpen(Integer sundayOpen) {
        this.sundayOpen = sundayOpen;
    }

    @JsonProperty("sunday_close")
    public Integer getSundayClose() {
        return sundayClose;
    }

    @JsonProperty("sunday_close")
    public void setSundayClose(Integer sundayClose) {
        this.sundayClose = sundayClose;
    }

    @JsonProperty("monday_open")
    public Integer getMondayOpen() {
        return mondayOpen;
    }

    @JsonProperty("monday_open")
    public void setMondayOpen(Integer mondayOpen) {
        this.mondayOpen = mondayOpen;
    }

    @JsonProperty("monday_close")
    public Integer getMondayClose() {
        return mondayClose;
    }

    @JsonProperty("monday_close")
    public void setMondayClose(Integer mondayClose) {
        this.mondayClose = mondayClose;
    }

    @JsonProperty("tuesday_open")
    public Integer getTuesdayOpen() {
        return tuesdayOpen;
    }

    @JsonProperty("tuesday_open")
    public void setTuesdayOpen(Integer tuesdayOpen) {
        this.tuesdayOpen = tuesdayOpen;
    }

    @JsonProperty("tuesday_close")
    public Integer getTuesdayClose() {
        return tuesdayClose;
    }

    @JsonProperty("tuesday_close")
    public void setTuesdayClose(Integer tuesdayClose) {
        this.tuesdayClose = tuesdayClose;
    }

    @JsonProperty("wednesday_open")
    public Integer getWednesdayOpen() {
        return wednesdayOpen;
    }

    @JsonProperty("wednesday_open")
    public void setWednesdayOpen(Integer wednesdayOpen) {
        this.wednesdayOpen = wednesdayOpen;
    }

    @JsonProperty("wednesday_close")
    public Integer getWednesdayClose() {
        return wednesdayClose;
    }

    @JsonProperty("wednesday_close")
    public void setWednesdayClose(Integer wednesdayClose) {
        this.wednesdayClose = wednesdayClose;
    }

    @JsonProperty("thursday_open")
    public Integer getThursdayOpen() {
        return thursdayOpen;
    }

    @JsonProperty("thursday_open")
    public void setThursdayOpen(Integer thursdayOpen) {
        this.thursdayOpen = thursdayOpen;
    }

    @JsonProperty("thursday_close")
    public Integer getThursdayClose() {
        return thursdayClose;
    }

    @JsonProperty("thursday_close")
    public void setThursdayClose(Integer thursdayClose) {
        this.thursdayClose = thursdayClose;
    }

    @JsonProperty("friday_open")
    public Integer getFridayOpen() {
        return fridayOpen;
    }

    @JsonProperty("friday_open")
    public void setFridayOpen(Integer fridayOpen) {
        this.fridayOpen = fridayOpen;
    }

    @JsonProperty("friday_close")
    public Integer getFridayClose() {
        return fridayClose;
    }

    @JsonProperty("friday_close")
    public void setFridayClose(Integer fridayClose) {
        this.fridayClose = fridayClose;
    }

    @JsonProperty("saturday_open")
    public Integer getSaturdayOpen() {
        return saturdayOpen;
    }

    @JsonProperty("saturday_open")
    public void setSaturdayOpen(Integer saturdayOpen) {
        this.saturdayOpen = saturdayOpen;
    }

    @JsonProperty("saturday_close")
    public Integer getSaturdayClose() {
        return saturdayClose;
    }

    @JsonProperty("saturday_close")
    public void setSaturdayClose(Integer saturdayClose) {
        this.saturdayClose = saturdayClose;
    }

    @JsonProperty("updated_at")
    public String getUpdatedAt() {
        return updatedAt;
    }

    @JsonProperty("updated_at")
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @JsonProperty("store_no")
    public Integer getStoreNo() {
        return storeNo;
    }

    @JsonProperty("store_no")
    public void setStoreNo(Integer storeNo) {
        this.storeNo = storeNo;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @NotNull
    public boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(@NotNull boolean favorite) {
        this.favorite = favorite;
    }
}