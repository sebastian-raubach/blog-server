/*
 * This file is generated by jOOQ.
 */
package blog.raubach.database.codegen.tables.pojos;


import blog.raubach.database.codegen.enums.PostsType;

import java.io.Serializable;
import java.sql.Timestamp;

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
public class Posts implements Serializable {

    private static final long serialVersionUID = -117571622;

    private Integer   id;
    private PostsType type;
    private String    title;
    private String    content;
    private Integer   viewCount;
    private Timestamp endDate;
    private Timestamp createdOn;
    private Timestamp updatedOn;

    public Posts() {}

    public Posts(Posts value) {
        this.id = value.id;
        this.type = value.type;
        this.title = value.title;
        this.content = value.content;
        this.viewCount = value.viewCount;
        this.endDate = value.endDate;
        this.createdOn = value.createdOn;
        this.updatedOn = value.updatedOn;
    }

    public Posts(
        Integer   id,
        PostsType type,
        String    title,
        String    content,
        Integer   viewCount,
        Timestamp endDate,
        Timestamp createdOn,
        Timestamp updatedOn
    ) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
        this.endDate = endDate;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PostsType getType() {
        return this.type;
    }

    public void setType(PostsType type) {
        this.type = type;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getViewCount() {
        return this.viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Timestamp getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public Timestamp getCreatedOn() {
        return this.createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    public Timestamp getUpdatedOn() {
        return this.updatedOn;
    }

    public void setUpdatedOn(Timestamp updatedOn) {
        this.updatedOn = updatedOn;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Posts (");

        sb.append(id);
        sb.append(", ").append(type);
        sb.append(", ").append(title);
        sb.append(", ").append(content);
        sb.append(", ").append(viewCount);
        sb.append(", ").append(endDate);
        sb.append(", ").append(createdOn);
        sb.append(", ").append(updatedOn);

        sb.append(")");
        return sb.toString();
    }
// @formatter:on
}
