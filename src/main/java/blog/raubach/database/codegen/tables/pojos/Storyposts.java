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
public class Storyposts implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer storyId;
    private Integer postId;

    public Storyposts() {}

    public Storyposts(Storyposts value) {
        this.storyId = value.storyId;
        this.postId = value.postId;
    }

    public Storyposts(
        Integer storyId,
        Integer postId
    ) {
        this.storyId = storyId;
        this.postId = postId;
    }

    /**
     * Getter for <code>blog_db.storyposts.story_id</code>.
     */
    public Integer getStoryId() {
        return this.storyId;
    }

    /**
     * Setter for <code>blog_db.storyposts.story_id</code>.
     */
    public void setStoryId(Integer storyId) {
        this.storyId = storyId;
    }

    /**
     * Getter for <code>blog_db.storyposts.post_id</code>.
     */
    public Integer getPostId() {
        return this.postId;
    }

    /**
     * Setter for <code>blog_db.storyposts.post_id</code>.
     */
    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Storyposts (");

        sb.append(storyId);
        sb.append(", ").append(postId);

        sb.append(")");
        return sb.toString();
    }
    // @formatter:on
}
