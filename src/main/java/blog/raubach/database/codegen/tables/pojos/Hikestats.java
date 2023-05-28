/*
 * This file is generated by jOOQ.
 */
package blog.raubach.database.codegen.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;


// @formatter:off
/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Hikestats implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer   postId;
    private Integer   duration;
    private Double    distance;
    private Double    ascent;
    private String    gpxPath;
    private String    elevationProfilePath;
    private String    timeDistanceProfilePath;
    private Timestamp createdOn;
    private Timestamp updatedOn;

    public Hikestats() {}

    public Hikestats(Hikestats value) {
        this.postId = value.postId;
        this.duration = value.duration;
        this.distance = value.distance;
        this.ascent = value.ascent;
        this.gpxPath = value.gpxPath;
        this.elevationProfilePath = value.elevationProfilePath;
        this.timeDistanceProfilePath = value.timeDistanceProfilePath;
        this.createdOn = value.createdOn;
        this.updatedOn = value.updatedOn;
    }

    public Hikestats(
        Integer   postId,
        Integer   duration,
        Double    distance,
        Double    ascent,
        String    gpxPath,
        String    elevationProfilePath,
        String    timeDistanceProfilePath,
        Timestamp createdOn,
        Timestamp updatedOn
    ) {
        this.postId = postId;
        this.duration = duration;
        this.distance = distance;
        this.ascent = ascent;
        this.gpxPath = gpxPath;
        this.elevationProfilePath = elevationProfilePath;
        this.timeDistanceProfilePath = timeDistanceProfilePath;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
    }

    /**
     * Getter for <code>blog_db.hikestats.post_id</code>.
     */
    public Integer getPostId() {
        return this.postId;
    }

    /**
     * Setter for <code>blog_db.hikestats.post_id</code>.
     */
    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    /**
     * Getter for <code>blog_db.hikestats.duration</code>.
     */
    public Integer getDuration() {
        return this.duration;
    }

    /**
     * Setter for <code>blog_db.hikestats.duration</code>.
     */
    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    /**
     * Getter for <code>blog_db.hikestats.distance</code>.
     */
    public Double getDistance() {
        return this.distance;
    }

    /**
     * Setter for <code>blog_db.hikestats.distance</code>.
     */
    public void setDistance(Double distance) {
        this.distance = distance;
    }

    /**
     * Getter for <code>blog_db.hikestats.ascent</code>.
     */
    public Double getAscent() {
        return this.ascent;
    }

    /**
     * Setter for <code>blog_db.hikestats.ascent</code>.
     */
    public void setAscent(Double ascent) {
        this.ascent = ascent;
    }

    /**
     * Getter for <code>blog_db.hikestats.gpx_path</code>.
     */
    public String getGpxPath() {
        return this.gpxPath;
    }

    /**
     * Setter for <code>blog_db.hikestats.gpx_path</code>.
     */
    public void setGpxPath(String gpxPath) {
        this.gpxPath = gpxPath;
    }

    /**
     * Getter for <code>blog_db.hikestats.elevation_profile_path</code>.
     */
    public String getElevationProfilePath() {
        return this.elevationProfilePath;
    }

    /**
     * Setter for <code>blog_db.hikestats.elevation_profile_path</code>.
     */
    public void setElevationProfilePath(String elevationProfilePath) {
        this.elevationProfilePath = elevationProfilePath;
    }

    /**
     * Getter for <code>blog_db.hikestats.time_distance_profile_path</code>.
     */
    public String getTimeDistanceProfilePath() {
        return this.timeDistanceProfilePath;
    }

    /**
     * Setter for <code>blog_db.hikestats.time_distance_profile_path</code>.
     */
    public void setTimeDistanceProfilePath(String timeDistanceProfilePath) {
        this.timeDistanceProfilePath = timeDistanceProfilePath;
    }

    /**
     * Getter for <code>blog_db.hikestats.created_on</code>.
     */
    public Timestamp getCreatedOn() {
        return this.createdOn;
    }

    /**
     * Setter for <code>blog_db.hikestats.created_on</code>.
     */
    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    /**
     * Getter for <code>blog_db.hikestats.updated_on</code>.
     */
    public Timestamp getUpdatedOn() {
        return this.updatedOn;
    }

    /**
     * Setter for <code>blog_db.hikestats.updated_on</code>.
     */
    public void setUpdatedOn(Timestamp updatedOn) {
        this.updatedOn = updatedOn;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Hikestats (");

        sb.append(postId);
        sb.append(", ").append(duration);
        sb.append(", ").append(distance);
        sb.append(", ").append(ascent);
        sb.append(", ").append(gpxPath);
        sb.append(", ").append(elevationProfilePath);
        sb.append(", ").append(timeDistanceProfilePath);
        sb.append(", ").append(createdOn);
        sb.append(", ").append(updatedOn);

        sb.append(")");
        return sb.toString();
    }
    // @formatter:on
}
