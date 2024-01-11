/*
 * This file is generated by jOOQ.
 */
package blog.raubach.database.codegen.tables.records;


import blog.raubach.database.codegen.enums.PostsType;
import blog.raubach.database.codegen.tables.Posts;

import java.sql.Timestamp;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record10;
import org.jooq.Row10;
import org.jooq.impl.UpdatableRecordImpl;


// @formatter:off
/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class PostsRecord extends UpdatableRecordImpl<PostsRecord> implements Record10<Integer, PostsType, String, String, String, Boolean, Integer, Timestamp, Timestamp, Timestamp> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>blog_db.posts.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>blog_db.posts.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>blog_db.posts.type</code>.
     */
    public void setType(PostsType value) {
        set(1, value);
    }

    /**
     * Getter for <code>blog_db.posts.type</code>.
     */
    public PostsType getType() {
        return (PostsType) get(1);
    }

    /**
     * Setter for <code>blog_db.posts.title</code>.
     */
    public void setTitle(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>blog_db.posts.title</code>.
     */
    public String getTitle() {
        return (String) get(2);
    }

    /**
     * Setter for <code>blog_db.posts.content</code>.
     */
    public void setContent(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>blog_db.posts.content</code>.
     */
    public String getContent() {
        return (String) get(3);
    }

    /**
     * Setter for <code>blog_db.posts.content_markdown</code>.
     */
    public void setContentMarkdown(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>blog_db.posts.content_markdown</code>.
     */
    public String getContentMarkdown() {
        return (String) get(4);
    }

    /**
     * Setter for <code>blog_db.posts.visible</code>.
     */
    public void setVisible(Boolean value) {
        set(5, value);
    }

    /**
     * Getter for <code>blog_db.posts.visible</code>.
     */
    public Boolean getVisible() {
        return (Boolean) get(5);
    }

    /**
     * Setter for <code>blog_db.posts.view_count</code>.
     */
    public void setViewCount(Integer value) {
        set(6, value);
    }

    /**
     * Getter for <code>blog_db.posts.view_count</code>.
     */
    public Integer getViewCount() {
        return (Integer) get(6);
    }

    /**
     * Setter for <code>blog_db.posts.end_date</code>.
     */
    public void setEndDate(Timestamp value) {
        set(7, value);
    }

    /**
     * Getter for <code>blog_db.posts.end_date</code>.
     */
    public Timestamp getEndDate() {
        return (Timestamp) get(7);
    }

    /**
     * Setter for <code>blog_db.posts.created_on</code>.
     */
    public void setCreatedOn(Timestamp value) {
        set(8, value);
    }

    /**
     * Getter for <code>blog_db.posts.created_on</code>.
     */
    public Timestamp getCreatedOn() {
        return (Timestamp) get(8);
    }

    /**
     * Setter for <code>blog_db.posts.updated_on</code>.
     */
    public void setUpdatedOn(Timestamp value) {
        set(9, value);
    }

    /**
     * Getter for <code>blog_db.posts.updated_on</code>.
     */
    public Timestamp getUpdatedOn() {
        return (Timestamp) get(9);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record10 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row10<Integer, PostsType, String, String, String, Boolean, Integer, Timestamp, Timestamp, Timestamp> fieldsRow() {
        return (Row10) super.fieldsRow();
    }

    @Override
    public Row10<Integer, PostsType, String, String, String, Boolean, Integer, Timestamp, Timestamp, Timestamp> valuesRow() {
        return (Row10) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return Posts.POSTS.ID;
    }

    @Override
    public Field<PostsType> field2() {
        return Posts.POSTS.TYPE;
    }

    @Override
    public Field<String> field3() {
        return Posts.POSTS.TITLE;
    }

    @Override
    public Field<String> field4() {
        return Posts.POSTS.CONTENT;
    }

    @Override
    public Field<String> field5() {
        return Posts.POSTS.CONTENT_MARKDOWN;
    }

    @Override
    public Field<Boolean> field6() {
        return Posts.POSTS.VISIBLE;
    }

    @Override
    public Field<Integer> field7() {
        return Posts.POSTS.VIEW_COUNT;
    }

    @Override
    public Field<Timestamp> field8() {
        return Posts.POSTS.END_DATE;
    }

    @Override
    public Field<Timestamp> field9() {
        return Posts.POSTS.CREATED_ON;
    }

    @Override
    public Field<Timestamp> field10() {
        return Posts.POSTS.UPDATED_ON;
    }

    @Override
    public Integer component1() {
        return getId();
    }

    @Override
    public PostsType component2() {
        return getType();
    }

    @Override
    public String component3() {
        return getTitle();
    }

    @Override
    public String component4() {
        return getContent();
    }

    @Override
    public String component5() {
        return getContentMarkdown();
    }

    @Override
    public Boolean component6() {
        return getVisible();
    }

    @Override
    public Integer component7() {
        return getViewCount();
    }

    @Override
    public Timestamp component8() {
        return getEndDate();
    }

    @Override
    public Timestamp component9() {
        return getCreatedOn();
    }

    @Override
    public Timestamp component10() {
        return getUpdatedOn();
    }

    @Override
    public Integer value1() {
        return getId();
    }

    @Override
    public PostsType value2() {
        return getType();
    }

    @Override
    public String value3() {
        return getTitle();
    }

    @Override
    public String value4() {
        return getContent();
    }

    @Override
    public String value5() {
        return getContentMarkdown();
    }

    @Override
    public Boolean value6() {
        return getVisible();
    }

    @Override
    public Integer value7() {
        return getViewCount();
    }

    @Override
    public Timestamp value8() {
        return getEndDate();
    }

    @Override
    public Timestamp value9() {
        return getCreatedOn();
    }

    @Override
    public Timestamp value10() {
        return getUpdatedOn();
    }

    @Override
    public PostsRecord value1(Integer value) {
        setId(value);
        return this;
    }

    @Override
    public PostsRecord value2(PostsType value) {
        setType(value);
        return this;
    }

    @Override
    public PostsRecord value3(String value) {
        setTitle(value);
        return this;
    }

    @Override
    public PostsRecord value4(String value) {
        setContent(value);
        return this;
    }

    @Override
    public PostsRecord value5(String value) {
        setContentMarkdown(value);
        return this;
    }

    @Override
    public PostsRecord value6(Boolean value) {
        setVisible(value);
        return this;
    }

    @Override
    public PostsRecord value7(Integer value) {
        setViewCount(value);
        return this;
    }

    @Override
    public PostsRecord value8(Timestamp value) {
        setEndDate(value);
        return this;
    }

    @Override
    public PostsRecord value9(Timestamp value) {
        setCreatedOn(value);
        return this;
    }

    @Override
    public PostsRecord value10(Timestamp value) {
        setUpdatedOn(value);
        return this;
    }

    @Override
    public PostsRecord values(Integer value1, PostsType value2, String value3, String value4, String value5, Boolean value6, Integer value7, Timestamp value8, Timestamp value9, Timestamp value10) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        value10(value10);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached PostsRecord
     */
    public PostsRecord() {
        super(Posts.POSTS);
    }

    /**
     * Create a detached, initialised PostsRecord
     */
    public PostsRecord(Integer id, PostsType type, String title, String content, String contentMarkdown, Boolean visible, Integer viewCount, Timestamp endDate, Timestamp createdOn, Timestamp updatedOn) {
        super(Posts.POSTS);

        setId(id);
        setType(type);
        setTitle(title);
        setContent(content);
        setContentMarkdown(contentMarkdown);
        setVisible(visible);
        setViewCount(viewCount);
        setEndDate(endDate);
        setCreatedOn(createdOn);
        setUpdatedOn(updatedOn);
    }

    /**
     * Create a detached, initialised PostsRecord
     */
    public PostsRecord(blog.raubach.database.codegen.tables.pojos.Posts value) {
        super(Posts.POSTS);

        if (value != null) {
            setId(value.getId());
            setType(value.getType());
            setTitle(value.getTitle());
            setContent(value.getContent());
            setContentMarkdown(value.getContentMarkdown());
            setVisible(value.getVisible());
            setViewCount(value.getViewCount());
            setEndDate(value.getEndDate());
            setCreatedOn(value.getCreatedOn());
            setUpdatedOn(value.getUpdatedOn());
        }
    }
    // @formatter:on
}
