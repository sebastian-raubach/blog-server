/*
 * This file is generated by jOOQ.
 */
package blog.raubach.database.codegen.tables.pojos;


import blog.raubach.database.codegen.enums.HillsType;

import java.io.Serializable;
import java.sql.Timestamp;


// @formatter:off
/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Hills implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer   id;
    private String    name;
    private HillsType type;
    private String    region;
    private String    url;
    private Double    latitude;
    private Double    longitude;
    private Double    elevation;
    private Timestamp createdOn;
    private Timestamp updatedOn;

    public Hills() {}

    public Hills(Hills value) {
        this.id = value.id;
        this.name = value.name;
        this.type = value.type;
        this.region = value.region;
        this.url = value.url;
        this.latitude = value.latitude;
        this.longitude = value.longitude;
        this.elevation = value.elevation;
        this.createdOn = value.createdOn;
        this.updatedOn = value.updatedOn;
    }

    public Hills(
        Integer   id,
        String    name,
        HillsType type,
        String    region,
        String    url,
        Double    latitude,
        Double    longitude,
        Double    elevation,
        Timestamp createdOn,
        Timestamp updatedOn
    ) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.region = region;
        this.url = url;
        this.latitude = latitude;
        this.longitude = longitude;
        this.elevation = elevation;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
    }

    /**
     * Getter for <code>blog_db.hills.id</code>.
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * Setter for <code>blog_db.hills.id</code>.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Getter for <code>blog_db.hills.name</code>.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Setter for <code>blog_db.hills.name</code>.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for <code>blog_db.hills.type</code>.
     */
    public HillsType getType() {
        return this.type;
    }

    /**
     * Setter for <code>blog_db.hills.type</code>.
     */
    public void setType(HillsType type) {
        this.type = type;
    }

    /**
     * Getter for <code>blog_db.hills.region</code>.
     */
    public String getRegion() {
        return this.region;
    }

    /**
     * Setter for <code>blog_db.hills.region</code>.
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * Getter for <code>blog_db.hills.url</code>.
     */
    public String getUrl() {
        return this.url;
    }

    /**
     * Setter for <code>blog_db.hills.url</code>.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Getter for <code>blog_db.hills.latitude</code>.
     */
    public Double getLatitude() {
        return this.latitude;
    }

    /**
     * Setter for <code>blog_db.hills.latitude</code>.
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     * Getter for <code>blog_db.hills.longitude</code>.
     */
    public Double getLongitude() {
        return this.longitude;
    }

    /**
     * Setter for <code>blog_db.hills.longitude</code>.
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    /**
     * Getter for <code>blog_db.hills.elevation</code>.
     */
    public Double getElevation() {
        return this.elevation;
    }

    /**
     * Setter for <code>blog_db.hills.elevation</code>.
     */
    public void setElevation(Double elevation) {
        this.elevation = elevation;
    }

    /**
     * Getter for <code>blog_db.hills.created_on</code>.
     */
    public Timestamp getCreatedOn() {
        return this.createdOn;
    }

    /**
     * Setter for <code>blog_db.hills.created_on</code>.
     */
    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    /**
     * Getter for <code>blog_db.hills.updated_on</code>.
     */
    public Timestamp getUpdatedOn() {
        return this.updatedOn;
    }

    /**
     * Setter for <code>blog_db.hills.updated_on</code>.
     */
    public void setUpdatedOn(Timestamp updatedOn) {
        this.updatedOn = updatedOn;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Hills (");

        sb.append(id);
        sb.append(", ").append(name);
        sb.append(", ").append(type);
        sb.append(", ").append(region);
        sb.append(", ").append(url);
        sb.append(", ").append(latitude);
        sb.append(", ").append(longitude);
        sb.append(", ").append(elevation);
        sb.append(", ").append(createdOn);
        sb.append(", ").append(updatedOn);

        sb.append(")");
        return sb.toString();
    }
    // @formatter:on
}
