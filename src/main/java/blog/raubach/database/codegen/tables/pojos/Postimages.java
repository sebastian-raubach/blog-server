/*
 * This file is generated by jOOQ.
 */
package blog.raubach.database.codegen.tables.pojos;


import java.io.Serializable;

import javax.annotation.Generated;


// @formatter:off
/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Postimages implements Serializable {

    private static final long serialVersionUID = -1143873674;

    private Integer postId;
    private Integer imageId;
    private Boolean isPrimary;
    private String  description;

    public Postimages() {}

    public Postimages(Postimages value) {
        this.postId = value.postId;
        this.imageId = value.imageId;
        this.isPrimary = value.isPrimary;
        this.description = value.description;
    }

    public Postimages(
        Integer postId,
        Integer imageId,
        Boolean isPrimary,
        String  description
    ) {
        this.postId = postId;
        this.imageId = imageId;
        this.isPrimary = isPrimary;
        this.description = description;
    }

    public Integer getPostId() {
        return this.postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getImageId() {
        return this.imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public Boolean getIsPrimary() {
        return this.isPrimary;
    }

    public void setIsPrimary(Boolean isPrimary) {
        this.isPrimary = isPrimary;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Postimages (");

        sb.append(postId);
        sb.append(", ").append(imageId);
        sb.append(", ").append(isPrimary);
        sb.append(", ").append(description);

        sb.append(")");
        return sb.toString();
    }
// @formatter:on
}