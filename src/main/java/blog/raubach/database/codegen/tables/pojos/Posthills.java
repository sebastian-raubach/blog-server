/*
 * This file is generated by jOOQ.
 */
package blog.raubach.database.codegen.tables.pojos;


import java.io.Serializable;


// @formatter:off
/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Posthills implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer postId;
    private Integer hillId;
    private Boolean successful;

    public Posthills() {}

    public Posthills(Posthills value) {
        this.postId = value.postId;
        this.hillId = value.hillId;
        this.successful = value.successful;
    }

    public Posthills(
        Integer postId,
        Integer hillId,
        Boolean successful
    ) {
        this.postId = postId;
        this.hillId = hillId;
        this.successful = successful;
    }

    /**
     * Getter for <code>blog_db.posthills.post_id</code>.
     */
    public Integer getPostId() {
        return this.postId;
    }

    /**
     * Setter for <code>blog_db.posthills.post_id</code>.
     */
    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    /**
     * Getter for <code>blog_db.posthills.hill_id</code>.
     */
    public Integer getHillId() {
        return this.hillId;
    }

    /**
     * Setter for <code>blog_db.posthills.hill_id</code>.
     */
    public void setHillId(Integer hillId) {
        this.hillId = hillId;
    }

    /**
     * Getter for <code>blog_db.posthills.successful</code>.
     */
    public Boolean getSuccessful() {
        return this.successful;
    }

    /**
     * Setter for <code>blog_db.posthills.successful</code>.
     */
    public void setSuccessful(Boolean successful) {
        this.successful = successful;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Posthills (");

        sb.append(postId);
        sb.append(", ").append(hillId);
        sb.append(", ").append(successful);

        sb.append(")");
        return sb.toString();
    }
    // @formatter:on
}
