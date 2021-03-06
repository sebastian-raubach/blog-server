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
public class Postusers implements Serializable {

    private static final long serialVersionUID = -1587206606;

    private Integer postId;
    private Integer userId;

    public Postusers() {}

    public Postusers(Postusers value) {
        this.postId = value.postId;
        this.userId = value.userId;
    }

    public Postusers(
        Integer postId,
        Integer userId
    ) {
        this.postId = postId;
        this.userId = userId;
    }

    public Integer getPostId() {
        return this.postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Postusers (");

        sb.append(postId);
        sb.append(", ").append(userId);

        sb.append(")");
        return sb.toString();
    }
// @formatter:on
}
