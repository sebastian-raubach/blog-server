/*
 * This file is generated by jOOQ.
 */
package blog.raubach.database.codegen.tables.pojos;


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
public class Stories implements Serializable {

    private static final long serialVersionUID = 790052996;

    private Integer   id;
    private String    title;
    private String    content;
    private Timestamp createdOn;
    private Timestamp updatedOn;

    public Stories() {}

    public Stories(Stories value) {
        this.id = value.id;
        this.title = value.title;
        this.content = value.content;
        this.createdOn = value.createdOn;
        this.updatedOn = value.updatedOn;
    }

    public Stories(
        Integer   id,
        String    title,
        String    content,
        Timestamp createdOn,
        Timestamp updatedOn
    ) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        StringBuilder sb = new StringBuilder("Stories (");

        sb.append(id);
        sb.append(", ").append(title);
        sb.append(", ").append(content);
        sb.append(", ").append(createdOn);
        sb.append(", ").append(updatedOn);

        sb.append(")");
        return sb.toString();
    }
// @formatter:on
}
