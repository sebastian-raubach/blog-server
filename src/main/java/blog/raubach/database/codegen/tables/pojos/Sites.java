/*
 * This file is generated by jOOQ.
 */
package blog.raubach.database.codegen.tables.pojos;


import blog.raubach.database.codegen.enums.SitesGroundtype;
import blog.raubach.database.codegen.enums.SitesSitetype;
import blog.raubach.pojo.SiteFacilities;
import blog.raubach.pojo.SiteRating;

import java.io.Serializable;
import java.sql.Timestamp;


// @formatter:off
/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Sites implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer         id;
    private String          name;
    private String          description;
    private SitesSitetype   sitetype;
    private SitesGroundtype groundtype;
    private Double          latitude;
    private Double          longitude;
    private Double          elevation;
    private SiteRating      rating;
    private SiteFacilities  facilities;
    private Timestamp       createdOn;
    private Timestamp       updatedOn;

    public Sites() {}

    public Sites(Sites value) {
        this.id = value.id;
        this.name = value.name;
        this.description = value.description;
        this.sitetype = value.sitetype;
        this.groundtype = value.groundtype;
        this.latitude = value.latitude;
        this.longitude = value.longitude;
        this.elevation = value.elevation;
        this.rating = value.rating;
        this.facilities = value.facilities;
        this.createdOn = value.createdOn;
        this.updatedOn = value.updatedOn;
    }

    public Sites(
        Integer         id,
        String          name,
        String          description,
        SitesSitetype   sitetype,
        SitesGroundtype groundtype,
        Double          latitude,
        Double          longitude,
        Double          elevation,
        SiteRating      rating,
        SiteFacilities  facilities,
        Timestamp       createdOn,
        Timestamp       updatedOn
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.sitetype = sitetype;
        this.groundtype = groundtype;
        this.latitude = latitude;
        this.longitude = longitude;
        this.elevation = elevation;
        this.rating = rating;
        this.facilities = facilities;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
    }

    /**
     * Getter for <code>blog_db.sites.id</code>.
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * Setter for <code>blog_db.sites.id</code>.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Getter for <code>blog_db.sites.name</code>.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Setter for <code>blog_db.sites.name</code>.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for <code>blog_db.sites.description</code>.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Setter for <code>blog_db.sites.description</code>.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter for <code>blog_db.sites.sitetype</code>.
     */
    public SitesSitetype getSitetype() {
        return this.sitetype;
    }

    /**
     * Setter for <code>blog_db.sites.sitetype</code>.
     */
    public void setSitetype(SitesSitetype sitetype) {
        this.sitetype = sitetype;
    }

    /**
     * Getter for <code>blog_db.sites.groundtype</code>.
     */
    public SitesGroundtype getGroundtype() {
        return this.groundtype;
    }

    /**
     * Setter for <code>blog_db.sites.groundtype</code>.
     */
    public void setGroundtype(SitesGroundtype groundtype) {
        this.groundtype = groundtype;
    }

    /**
     * Getter for <code>blog_db.sites.latitude</code>.
     */
    public Double getLatitude() {
        return this.latitude;
    }

    /**
     * Setter for <code>blog_db.sites.latitude</code>.
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     * Getter for <code>blog_db.sites.longitude</code>.
     */
    public Double getLongitude() {
        return this.longitude;
    }

    /**
     * Setter for <code>blog_db.sites.longitude</code>.
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    /**
     * Getter for <code>blog_db.sites.elevation</code>.
     */
    public Double getElevation() {
        return this.elevation;
    }

    /**
     * Setter for <code>blog_db.sites.elevation</code>.
     */
    public void setElevation(Double elevation) {
        this.elevation = elevation;
    }

    /**
     * Getter for <code>blog_db.sites.rating</code>.
     */
    public SiteRating getRating() {
        return this.rating;
    }

    /**
     * Setter for <code>blog_db.sites.rating</code>.
     */
    public void setRating(SiteRating rating) {
        this.rating = rating;
    }

    /**
     * Getter for <code>blog_db.sites.facilities</code>.
     */
    public SiteFacilities getFacilities() {
        return this.facilities;
    }

    /**
     * Setter for <code>blog_db.sites.facilities</code>.
     */
    public void setFacilities(SiteFacilities facilities) {
        this.facilities = facilities;
    }

    /**
     * Getter for <code>blog_db.sites.created_on</code>.
     */
    public Timestamp getCreatedOn() {
        return this.createdOn;
    }

    /**
     * Setter for <code>blog_db.sites.created_on</code>.
     */
    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    /**
     * Getter for <code>blog_db.sites.updated_on</code>.
     */
    public Timestamp getUpdatedOn() {
        return this.updatedOn;
    }

    /**
     * Setter for <code>blog_db.sites.updated_on</code>.
     */
    public void setUpdatedOn(Timestamp updatedOn) {
        this.updatedOn = updatedOn;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Sites (");

        sb.append(id);
        sb.append(", ").append(name);
        sb.append(", ").append(description);
        sb.append(", ").append(sitetype);
        sb.append(", ").append(groundtype);
        sb.append(", ").append(latitude);
        sb.append(", ").append(longitude);
        sb.append(", ").append(elevation);
        sb.append(", ").append(rating);
        sb.append(", ").append(facilities);
        sb.append(", ").append(createdOn);
        sb.append(", ").append(updatedOn);

        sb.append(")");
        return sb.toString();
    }
    // @formatter:on
}