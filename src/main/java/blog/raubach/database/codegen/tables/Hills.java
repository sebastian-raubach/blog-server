/*
 * This file is generated by jOOQ.
 */
package blog.raubach.database.codegen.tables;


import blog.raubach.database.codegen.BlogDb;
import blog.raubach.database.codegen.enums.HillsType;
import blog.raubach.database.codegen.tables.records.HillsRecord;

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
public class Hills extends TableImpl<HillsRecord> {

    private static final long serialVersionUID = 1482377466;

    /**
     * The reference instance of <code>blog_db.hills</code>
     */
    public static final Hills HILLS = new Hills();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<HillsRecord> getRecordType() {
        return HillsRecord.class;
    }

    /**
     * The column <code>blog_db.hills.id</code>.
     */
    public final TableField<HillsRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>blog_db.hills.name</code>.
     */
    public final TableField<HillsRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>blog_db.hills.type</code>.
     */
    public final TableField<HillsRecord, HillsType> TYPE = createField("type", org.jooq.impl.SQLDataType.VARCHAR(10).nullable(false).defaultValue(org.jooq.impl.DSL.inline("other", org.jooq.impl.SQLDataType.VARCHAR)).asEnumDataType(blog.raubach.database.codegen.enums.HillsType.class), this, "");

    /**
     * The column <code>blog_db.hills.region</code>.
     */
    public final TableField<HillsRecord, String> REGION = createField("region", org.jooq.impl.SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>blog_db.hills.url</code>.
     */
    public final TableField<HillsRecord, String> URL = createField("url", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>blog_db.hills.latitude</code>.
     */
    public final TableField<HillsRecord, Double> LATITUDE = createField("latitude", org.jooq.impl.SQLDataType.DOUBLE, this, "");

    /**
     * The column <code>blog_db.hills.longitude</code>.
     */
    public final TableField<HillsRecord, Double> LONGITUDE = createField("longitude", org.jooq.impl.SQLDataType.DOUBLE, this, "");

    /**
     * The column <code>blog_db.hills.elevation</code>.
     */
    public final TableField<HillsRecord, Double> ELEVATION = createField("elevation", org.jooq.impl.SQLDataType.DOUBLE, this, "");

    /**
     * The column <code>blog_db.hills.created_on</code>.
     */
    public final TableField<HillsRecord, Timestamp> CREATED_ON = createField("created_on", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>blog_db.hills.updated_on</code>.
     */
    public final TableField<HillsRecord, Timestamp> UPDATED_ON = createField("updated_on", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * Create a <code>blog_db.hills</code> table reference
     */
    public Hills() {
        this(DSL.name("hills"), null);
    }

    /**
     * Create an aliased <code>blog_db.hills</code> table reference
     */
    public Hills(String alias) {
        this(DSL.name(alias), HILLS);
    }

    /**
     * Create an aliased <code>blog_db.hills</code> table reference
     */
    public Hills(Name alias) {
        this(alias, HILLS);
    }

    private Hills(Name alias, Table<HillsRecord> aliased) {
        this(alias, aliased, null);
    }

    private Hills(Name alias, Table<HillsRecord> aliased, Field<?>[] parameters) {
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
    public Identity<HillsRecord, Integer> getIdentity() {
        return Internal.createIdentity(blog.raubach.database.codegen.tables.Hills.HILLS, blog.raubach.database.codegen.tables.Hills.HILLS.ID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<HillsRecord> getPrimaryKey() {
        return Internal.createUniqueKey(blog.raubach.database.codegen.tables.Hills.HILLS, "KEY_hills_PRIMARY", blog.raubach.database.codegen.tables.Hills.HILLS.ID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<HillsRecord>> getKeys() {
        return Arrays.<UniqueKey<HillsRecord>>asList(
              Internal.createUniqueKey(blog.raubach.database.codegen.tables.Hills.HILLS, "KEY_hills_PRIMARY", blog.raubach.database.codegen.tables.Hills.HILLS.ID)
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Hills as(String alias) {
        return new Hills(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Hills as(Name alias) {
        return new Hills(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Hills rename(String name) {
        return new Hills(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Hills rename(Name name) {
        return new Hills(name, null);
    }
// @formatter:on
}
