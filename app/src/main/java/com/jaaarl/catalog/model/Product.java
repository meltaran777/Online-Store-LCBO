package com.jaaarl.catalog.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "is_dead",
        "name",
        "tags",
        "is_discontinued",
        "price_in_cents",
        "regular_price_in_cents",
        "limited_time_offer_savings_in_cents",
        "limited_time_offer_ends_on",
        "bonus_reward_miles",
        "bonus_reward_miles_ends_on",
        "stock_type",
        "primary_category",
        "secondary_category",
        "origin",
        "package",
        "package_unit_type",
        "package_unit_volume_in_milliliters",
        "total_package_units",
        "volume_in_milliliters",
        "alcohol_content",
        "price_per_liter_of_alcohol_in_cents",
        "price_per_liter_in_cents",
        "inventory_count",
        "inventory_volume_in_milliliters",
        "inventory_price_in_cents",
        "sugar_content",
        "producer_name",
        "released_on",
        "has_value_added_promotion",
        "has_limited_time_offer",
        "has_bonus_reward_miles",
        "is_seasonal",
        "is_vqa",
        "is_ocb",
        "is_kosher",
        "value_added_promotion_description",
        "description",
        "serving_suggestion",
        "tasting_note",
        "updated_at",
        "image_thumb_url",
        "image_url",
        "varietal",
        "style",
        "tertiary_category",
        "sugar_in_grams_per_liter",
        "clearance_sale_savings_in_cents",
        "has_clearance_sale",
        "product_no"
})
@Entity(tableName = "product")
public class Product implements Serializable, Model {

    @PrimaryKey
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("is_dead")
    private Boolean isDead;
    @JsonProperty("name")
    private String  name;
    @JsonProperty("tags")
    private String  tags;
    @JsonProperty("is_discontinued")
    private Boolean isDiscontinued;
    @JsonProperty("price_in_cents")
    private Integer priceInCents;
    @JsonProperty("regular_price_in_cents")
    private Integer regularPriceInCents;
    @JsonProperty("limited_time_offer_savings_in_cents")
    private Integer limitedTimeOfferSavingsInCents;
    @JsonProperty("limited_time_offer_ends_on")
    private String  limitedTimeOfferEndsOn;
    @JsonProperty("bonus_reward_miles")
    private Integer bonusRewardMiles;
    @JsonProperty("bonus_reward_miles_ends_on")
    private String  bonusRewardMilesEndsOn;
    @JsonProperty("stock_type")
    private String  stockType;
    @JsonProperty("primary_category")
    private String  primaryCategory;
    @JsonProperty("secondary_category")
    private String  secondaryCategory;
    @JsonProperty("origin")
    private String  origin;
    @JsonProperty("package")
    private String  _package;
    @JsonProperty("package_unit_type")
    private String  packageUnitType;
    @JsonProperty("package_unit_volume_in_milliliters")
    private Integer packageUnitVolumeInMilliliters;
    @JsonProperty("total_package_units")
    private Integer totalPackageUnits;
    @JsonProperty("volume_in_milliliters")
    private Integer volumeInMilliliters;
    @JsonProperty("alcohol_content")
    private Integer alcoholContent;
    @JsonProperty("price_per_liter_of_alcohol_in_cents")
    private Integer pricePerLiterOfAlcoholInCents;
    @JsonProperty("price_per_liter_in_cents")
    private Integer pricePerLiterInCents;
    @JsonProperty("inventory_count")
    private Integer inventoryCount;
    @JsonProperty("inventory_volume_in_milliliters")
    private Integer inventoryVolumeInMilliliters;
    @JsonProperty("inventory_price_in_cents")
    private Integer inventoryPriceInCents;
    @JsonProperty("sugar_content")
    private String  sugarContent;
    @JsonProperty("producer_name")
    private String  producerName;
    @JsonProperty("released_on")
    private String  releasedOn;
    @JsonProperty("has_value_added_promotion")
    private Boolean hasValueAddedPromotion;
    @JsonProperty("has_limited_time_offer")
    private Boolean hasLimitedTimeOffer;
    @JsonProperty("has_bonus_reward_miles")
    private Boolean hasBonusRewardMiles;
    @JsonProperty("is_seasonal")
    private Boolean isSeasonal;
    @JsonProperty("is_vqa")
    private Boolean isVqa;
    @JsonProperty("is_ocb")
    private Boolean isOcb;
    @JsonProperty("is_kosher")
    private Boolean isKosher;
    @JsonProperty("value_added_promotion_description")
    private String  valueAddedPromotionDescription;
    @JsonProperty("description")
    private String  description;
    @JsonProperty("serving_suggestion")
    private String  servingSuggestion;
    @JsonProperty("tasting_note")
    private String  tastingNote;
    @JsonProperty("updated_at")
    private String  updatedAt;
    @JsonProperty("image_thumb_url")
    private String  imageThumbUrl;
    @JsonProperty("image_url")
    private String  imageUrl;
    @JsonProperty("varietal")
    private String  varietal;
    @JsonProperty("style")
    private String  style;
    @JsonProperty("tertiary_category")
    private String  tertiaryCategory;
    @JsonProperty("sugar_in_grams_per_liter")
    private String  sugarInGramsPerLiter;
    @JsonProperty("clearance_sale_savings_in_cents")
    private Integer clearanceSaleSavingsInCents;
    @JsonProperty("has_clearance_sale")
    private Boolean hasClearanceSale;
    @JsonProperty("product_no")
    private Integer productNo;
    @JsonIgnore
    @Ignore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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

    @JsonProperty("is_discontinued")
    public Boolean getIsDiscontinued() {
        return isDiscontinued;
    }

    @JsonProperty("is_discontinued")
    public void setIsDiscontinued(Boolean isDiscontinued) {
        this.isDiscontinued = isDiscontinued;
    }

    @JsonProperty("price_in_cents")
    public Integer getPriceInCents() {
        return priceInCents;
    }

    @JsonProperty("price_in_cents")
    public void setPriceInCents(Integer priceInCents) {
        this.priceInCents = priceInCents;
    }

    @JsonProperty("regular_price_in_cents")
    public Integer getRegularPriceInCents() {
        return regularPriceInCents;
    }

    @JsonProperty("regular_price_in_cents")
    public void setRegularPriceInCents(Integer regularPriceInCents) {
        this.regularPriceInCents = regularPriceInCents;
    }

    @JsonProperty("limited_time_offer_savings_in_cents")
    public Integer getLimitedTimeOfferSavingsInCents() {
        return limitedTimeOfferSavingsInCents;
    }

    @JsonProperty("limited_time_offer_savings_in_cents")
    public void setLimitedTimeOfferSavingsInCents(Integer limitedTimeOfferSavingsInCents) {
        this.limitedTimeOfferSavingsInCents = limitedTimeOfferSavingsInCents;
    }

    @JsonProperty("limited_time_offer_ends_on")
    public String getLimitedTimeOfferEndsOn() {
        return limitedTimeOfferEndsOn;
    }

    @JsonProperty("limited_time_offer_ends_on")
    public void setLimitedTimeOfferEndsOn(String limitedTimeOfferEndsOn) {
        this.limitedTimeOfferEndsOn = limitedTimeOfferEndsOn;
    }

    @JsonProperty("bonus_reward_miles")
    public Integer getBonusRewardMiles() {
        return bonusRewardMiles;
    }

    @JsonProperty("bonus_reward_miles")
    public void setBonusRewardMiles(Integer bonusRewardMiles) {
        this.bonusRewardMiles = bonusRewardMiles;
    }

    @JsonProperty("bonus_reward_miles_ends_on")
    public String getBonusRewardMilesEndsOn() {
        return bonusRewardMilesEndsOn;
    }

    @JsonProperty("bonus_reward_miles_ends_on")
    public void setBonusRewardMilesEndsOn(String bonusRewardMilesEndsOn) {
        this.bonusRewardMilesEndsOn = bonusRewardMilesEndsOn;
    }

    @JsonProperty("stock_type")
    public String getStockType() {
        return stockType;
    }

    @JsonProperty("stock_type")
    public void setStockType(String stockType) {
        this.stockType = stockType;
    }

    @JsonProperty("primary_category")
    public String getPrimaryCategory() {
        return primaryCategory;
    }

    @JsonProperty("primary_category")
    public void setPrimaryCategory(String primaryCategory) {
        this.primaryCategory = primaryCategory;
    }

    @JsonProperty("secondary_category")
    public String getSecondaryCategory() {
        return secondaryCategory;
    }

    @JsonProperty("secondary_category")
    public void setSecondaryCategory(String secondaryCategory) {
        this.secondaryCategory = secondaryCategory;
    }

    @JsonProperty("origin")
    public String getOrigin() {
        return origin;
    }

    @JsonProperty("origin")
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    @JsonProperty("package")
    public String getPackage() {
        return _package;
    }

    @JsonProperty("package")
    public void setPackage(String _package) {
        this._package = _package;
    }

    @JsonProperty("package_unit_type")
    public String getPackageUnitType() {
        return packageUnitType;
    }

    @JsonProperty("package_unit_type")
    public void setPackageUnitType(String packageUnitType) {
        this.packageUnitType = packageUnitType;
    }

    @JsonProperty("package_unit_volume_in_milliliters")
    public Integer getPackageUnitVolumeInMilliliters() {
        return packageUnitVolumeInMilliliters;
    }

    @JsonProperty("package_unit_volume_in_milliliters")
    public void setPackageUnitVolumeInMilliliters(Integer packageUnitVolumeInMilliliters) {
        this.packageUnitVolumeInMilliliters = packageUnitVolumeInMilliliters;
    }

    @JsonProperty("total_package_units")
    public Integer getTotalPackageUnits() {
        return totalPackageUnits;
    }

    @JsonProperty("total_package_units")
    public void setTotalPackageUnits(Integer totalPackageUnits) {
        this.totalPackageUnits = totalPackageUnits;
    }

    @JsonProperty("volume_in_milliliters")
    public Integer getVolumeInMilliliters() {
        return volumeInMilliliters;
    }

    @JsonProperty("volume_in_milliliters")
    public void setVolumeInMilliliters(Integer volumeInMilliliters) {
        this.volumeInMilliliters = volumeInMilliliters;
    }

    @JsonProperty("alcohol_content")
    public Integer getAlcoholContent() {
        return alcoholContent;
    }

    @JsonProperty("alcohol_content")
    public void setAlcoholContent(Integer alcoholContent) {
        this.alcoholContent = alcoholContent;
    }

    @JsonProperty("price_per_liter_of_alcohol_in_cents")
    public Integer getPricePerLiterOfAlcoholInCents() {
        return pricePerLiterOfAlcoholInCents;
    }

    @JsonProperty("price_per_liter_of_alcohol_in_cents")
    public void setPricePerLiterOfAlcoholInCents(Integer pricePerLiterOfAlcoholInCents) {
        this.pricePerLiterOfAlcoholInCents = pricePerLiterOfAlcoholInCents;
    }

    @JsonProperty("price_per_liter_in_cents")
    public Integer getPricePerLiterInCents() {
        return pricePerLiterInCents;
    }

    @JsonProperty("price_per_liter_in_cents")
    public void setPricePerLiterInCents(Integer pricePerLiterInCents) {
        this.pricePerLiterInCents = pricePerLiterInCents;
    }

    @JsonProperty("inventory_count")
    public Integer getInventoryCount() {
        return inventoryCount;
    }

    @JsonProperty("inventory_count")
    public void setInventoryCount(Integer inventoryCount) {
        this.inventoryCount = inventoryCount;
    }

    @JsonProperty("inventory_volume_in_milliliters")
    public Integer getInventoryVolumeInMilliliters() {
        return inventoryVolumeInMilliliters;
    }

    @JsonProperty("inventory_volume_in_milliliters")
    public void setInventoryVolumeInMilliliters(Integer inventoryVolumeInMilliliters) {
        this.inventoryVolumeInMilliliters = inventoryVolumeInMilliliters;
    }

    @JsonProperty("inventory_price_in_cents")
    public Integer getInventoryPriceInCents() {
        return inventoryPriceInCents;
    }

    @JsonProperty("inventory_price_in_cents")
    public void setInventoryPriceInCents(Integer inventoryPriceInCents) {
        this.inventoryPriceInCents = inventoryPriceInCents;
    }

    @JsonProperty("sugar_content")
    public String getSugarContent() {
        return sugarContent;
    }

    @JsonProperty("sugar_content")
    public void setSugarContent(String sugarContent) {
        this.sugarContent = sugarContent;
    }

    @JsonProperty("producer_name")
    public String getProducerName() {
        return producerName;
    }

    @JsonProperty("producer_name")
    public void setProducerName(String producerName) {
        this.producerName = producerName;
    }

    @JsonProperty("released_on")
    public String getReleasedOn() {
        return releasedOn;
    }

    @JsonProperty("released_on")
    public void setReleasedOn(String releasedOn) {
        this.releasedOn = releasedOn;
    }

    @JsonProperty("has_value_added_promotion")
    public Boolean getHasValueAddedPromotion() {
        return hasValueAddedPromotion;
    }

    @JsonProperty("has_value_added_promotion")
    public void setHasValueAddedPromotion(Boolean hasValueAddedPromotion) {
        this.hasValueAddedPromotion = hasValueAddedPromotion;
    }

    @JsonProperty("has_limited_time_offer")
    public Boolean getHasLimitedTimeOffer() {
        return hasLimitedTimeOffer;
    }

    @JsonProperty("has_limited_time_offer")
    public void setHasLimitedTimeOffer(Boolean hasLimitedTimeOffer) {
        this.hasLimitedTimeOffer = hasLimitedTimeOffer;
    }

    @JsonProperty("has_bonus_reward_miles")
    public Boolean getHasBonusRewardMiles() {
        return hasBonusRewardMiles;
    }

    @JsonProperty("has_bonus_reward_miles")
    public void setHasBonusRewardMiles(Boolean hasBonusRewardMiles) {
        this.hasBonusRewardMiles = hasBonusRewardMiles;
    }

    @JsonProperty("is_seasonal")
    public Boolean getIsSeasonal() {
        return isSeasonal;
    }

    @JsonProperty("is_seasonal")
    public void setIsSeasonal(Boolean isSeasonal) {
        this.isSeasonal = isSeasonal;
    }

    @JsonProperty("is_vqa")
    public Boolean getIsVqa() {
        return isVqa;
    }

    @JsonProperty("is_vqa")
    public void setIsVqa(Boolean isVqa) {
        this.isVqa = isVqa;
    }

    @JsonProperty("is_ocb")
    public Boolean getIsOcb() {
        return isOcb;
    }

    @JsonProperty("is_ocb")
    public void setIsOcb(Boolean isOcb) {
        this.isOcb = isOcb;
    }

    @JsonProperty("is_kosher")
    public Boolean getIsKosher() {
        return isKosher;
    }

    @JsonProperty("is_kosher")
    public void setIsKosher(Boolean isKosher) {
        this.isKosher = isKosher;
    }

    @JsonProperty("value_added_promotion_description")
    public String getValueAddedPromotionDescription() {
        return valueAddedPromotionDescription;
    }

    @JsonProperty("value_added_promotion_description")
    public void setValueAddedPromotionDescription(String valueAddedPromotionDescription) {
        this.valueAddedPromotionDescription = valueAddedPromotionDescription;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("serving_suggestion")
    public String getServingSuggestion() {
        return servingSuggestion;
    }

    @JsonProperty("serving_suggestion")
    public void setServingSuggestion(String servingSuggestion) {
        this.servingSuggestion = servingSuggestion;
    }

    @JsonProperty("tasting_note")
    public String getTastingNote() {
        return tastingNote;
    }

    @JsonProperty("tasting_note")
    public void setTastingNote(String tastingNote) {
        this.tastingNote = tastingNote;
    }

    @JsonProperty("updated_at")
    public String getUpdatedAt() {
        return updatedAt;
    }

    @JsonProperty("updated_at")
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @JsonProperty("image_thumb_url")
    public String getImageThumbUrl() {
        return imageThumbUrl;
    }

    @JsonProperty("image_thumb_url")
    public void setImageThumbUrl(String imageThumbUrl) {
        this.imageThumbUrl = imageThumbUrl;
    }

    @JsonProperty("image_url")
    public String getImageUrl() {
        return imageUrl;
    }

    @JsonProperty("image_url")
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @JsonProperty("varietal")
    public String getVarietal() {
        return varietal;
    }

    @JsonProperty("varietal")
    public void setVarietal(String varietal) {
        this.varietal = varietal;
    }

    @JsonProperty("style")
    public String getStyle() {
        return style;
    }

    @JsonProperty("style")
    public void setStyle(String style) {
        this.style = style;
    }

    @JsonProperty("tertiary_category")
    public String getTertiaryCategory() {
        return tertiaryCategory;
    }

    @JsonProperty("tertiary_category")
    public void setTertiaryCategory(String tertiaryCategory) {
        this.tertiaryCategory = tertiaryCategory;
    }

    @JsonProperty("sugar_in_grams_per_liter")
    public String getSugarInGramsPerLiter() {
        return sugarInGramsPerLiter;
    }

    @JsonProperty("sugar_in_grams_per_liter")
    public void setSugarInGramsPerLiter(String sugarInGramsPerLiter) {
        this.sugarInGramsPerLiter = sugarInGramsPerLiter;
    }

    @JsonProperty("clearance_sale_savings_in_cents")
    public Integer getClearanceSaleSavingsInCents() {
        return clearanceSaleSavingsInCents;
    }

    @JsonProperty("clearance_sale_savings_in_cents")
    public void setClearanceSaleSavingsInCents(Integer clearanceSaleSavingsInCents) {
        this.clearanceSaleSavingsInCents = clearanceSaleSavingsInCents;
    }

    @JsonProperty("has_clearance_sale")
    public Boolean getHasClearanceSale() {
        return hasClearanceSale;
    }

    @JsonProperty("has_clearance_sale")
    public void setHasClearanceSale(Boolean hasClearanceSale) {
        this.hasClearanceSale = hasClearanceSale;
    }

    @JsonProperty("product_no")
    public Integer getProductNo() {
        return productNo;
    }

    @JsonProperty("product_no")
    public void setProductNo(Integer productNo) {
        this.productNo = productNo;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}