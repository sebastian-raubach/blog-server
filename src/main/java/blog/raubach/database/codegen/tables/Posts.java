/*
 * This file is generated by jOOQ.
 */
package blog.raubach.database.codegen.tables;


import blog.raubach.database.codegen.BlogDb;
import blog.raubach.database.codegen.enums.PostsType;
import blog.raubach.database.codegen.tables.records.PostsRecord;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;
import org.jooq.impl.TableImpl;


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
public class Posts extends TableImpl<PostsRecord> {

    private static final long serialVersionUID = 302017516;

    /**
     * The reference instance of <code>blog_db.posts</code>
     */
    public static final Posts POSTS = new Posts();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<PostsRecord> getRecordType() {
        return PostsRecord.class;
    }

    /**
     * The column <code>blog_db.posts.id</code>.
     */
    public final TableField<PostsRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>blog_db.posts.type</code>.
     */
    public final TableField<PostsRecord, PostsType> TYPE = createField("type", org.jooq.impl.SQLDataType.VARCHAR(4).nullable(false).defaultValue(org.jooq.impl.DSL.inline("news", org.jooq.impl.SQLDataType.VARCHAR)).asEnumDataType(blog.raubach.database.codegen.enums.PostsType.class), this, "");

    /**
     * The column <code>blog_db.posts.title</code>.
     */
    public final TableField<PostsRecord, String> TITLE = createField("title", org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>blog_db.posts.content</code>.
     */
    public final TableField<PostsRecord, String> CONTENT = createField("content", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>blog_db.posts.view_count</code>.
     */
    public final TableField<PostsRecord, Integer> VIEW_COUNT = createField("view_count", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>blog_db.posts.end_date</code>.
     */
    public final TableField<PostsRecord, Timestamp> END_DATE = createField("end_date", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * The column <code>blog_db.posts.created_on</code>.
     */
    public final TableField<PostsRecord, Timestamp> CREATED_ON = createField("created_on", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>blog_db.posts.updated_on</code>.
     */
    public final TableField<PostsRecord, Timestamp> UPDATED_ON = createField("updated_on", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * Create a <code>blog_db.posts</code> table reference
     */
    public Posts() {
        this(DSL.name("posts"), null);
    }

    /**
     * Create an aliased <code>blog_db.posts</code> table reference
     */
    public Posts(String alias) {
        this(DSL.name(alias), POSTS);
    }

    /**
     * Create an aliased <code>blog_db.posts</code> table reference
     */
    public Posts(Name alias) {
        this(alias, POSTS);
    }

    private Posts(Name alias, Table<PostsRecord> aliased) {
        this(alias, aliased, null);
    }

    private Posts(Name alias, Table<PostsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return BlogDb.BLOG_DB;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<PostsRecord, Integer> getIdentity() {
        return Internal.createIdentity(blog.raubach.database.codegen.tables.Posts.POSTS, blog.raubach.database.codegen.tables.Posts.POSTS.ID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<PostsRecord> getPrimaryKey() {
        return Internal.createUniqueKey(blog.raubach.database.codegen.tables.Posts.POSTS, "KEY_posts_PRIMARY", blog.raubach.database.codegen.tables.Posts.POSTS.ID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<PostsRecord>> getKeys() {
        return Arrays.<UniqueKey<PostsRecord>>asList(
              Internal.createUniqueKey(blog.raubach.database.codegen.tables.Posts.POSTS, "KEY_posts_PRIMARY", blog.raubach.database.codegen.tables.Posts.POSTS.ID)
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Posts as(String alias) {
        return new Posts(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Posts as(Name alias) {
        return new Posts(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Posts rename(String name) {
        return new Posts(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Posts rename(Name name) {
        return new Posts(name, null);
    }
// @formatter:on
}
