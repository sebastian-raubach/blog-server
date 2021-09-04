/*
 * This file is generated by jOOQ.
 */
package blog.raubach.database.codegen.tables;


import blog.raubach.database.codegen.BlogDb;
import blog.raubach.database.codegen.tables.records.StoriesRecord;

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
public class Stories extends TableImpl<StoriesRecord> {

    private static final long serialVersionUID = -1910339443;

    /**
     * The reference instance of <code>blog_db.stories</code>
     */
    public static final Stories STORIES = new Stories();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<StoriesRecord> getRecordType() {
        return StoriesRecord.class;
    }

    /**
     * The column <code>blog_db.stories.id</code>.
     */
    public final TableField<StoriesRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>blog_db.stories.title</code>.
     */
    public final TableField<StoriesRecord, String> TITLE = createField("title", org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>blog_db.stories.content</code>.
     */
    public final TableField<StoriesRecord, String> CONTENT = createField("content", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>blog_db.stories.created_on</code>.
     */
    public final TableField<StoriesRecord, Timestamp> CREATED_ON = createField("created_on", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>blog_db.stories.updated_on</code>.
     */
    public final TableField<StoriesRecord, Timestamp> UPDATED_ON = createField("updated_on", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * Create a <code>blog_db.stories</code> table reference
     */
    public Stories() {
        this(DSL.name("stories"), null);
    }

    /**
     * Create an aliased <code>blog_db.stories</code> table reference
     */
    public Stories(String alias) {
        this(DSL.name(alias), STORIES);
    }

    /**
     * Create an aliased <code>blog_db.stories</code> table reference
     */
    public Stories(Name alias) {
        this(alias, STORIES);
    }

    private Stories(Name alias, Table<StoriesRecord> aliased) {
        this(alias, aliased, null);
    }

    private Stories(Name alias, Table<StoriesRecord> aliased, Field<?>[] parameters) {
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
    public Identity<StoriesRecord, Integer> getIdentity() {
        return Internal.createIdentity(blog.raubach.database.codegen.tables.Stories.STORIES, blog.raubach.database.codegen.tables.Stories.STORIES.ID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<StoriesRecord> getPrimaryKey() {
        return Internal.createUniqueKey(blog.raubach.database.codegen.tables.Stories.STORIES, "KEY_stories_PRIMARY", blog.raubach.database.codegen.tables.Stories.STORIES.ID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<StoriesRecord>> getKeys() {
        return Arrays.<UniqueKey<StoriesRecord>>asList(
              Internal.createUniqueKey(blog.raubach.database.codegen.tables.Stories.STORIES, "KEY_stories_PRIMARY", blog.raubach.database.codegen.tables.Stories.STORIES.ID)
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stories as(String alias) {
        return new Stories(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stories as(Name alias) {
        return new Stories(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Stories rename(String name) {
        return new Stories(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Stories rename(Name name) {
        return new Stories(name, null);
    }
// @formatter:on
}
