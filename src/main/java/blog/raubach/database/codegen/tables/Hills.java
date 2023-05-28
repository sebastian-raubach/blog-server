/*
 * This file is generated by jOOQ.
 */
package blog.raubach.database.codegen.tables;


import blog.raubach.database.codegen.BlogDb;
import blog.raubach.database.codegen.enums.HillsType;
import blog.raubach.database.codegen.tables.records.HillsRecord;

import java.sql.Timestamp;

import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Row10;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


// @formatter:off
/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Hills extends TableImpl<HillsRecord> {

    private static final long serialVersionUID = 1L;

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
    public final TableField<HillsRecord, Integer> ID = createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>blog_db.hills.name</code>.
     */
    public final TableField<HillsRecord, String> NAME = createField(DSL.name("name"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>blog_db.hills.type</code>.
     */
    public final TableField<HillsRecord, HillsType> TYPE = createField(DSL.name("type"), SQLDataType.VARCHAR(10).nullable(false).defaultValue(DSL.inline("other", SQLDataType.VARCHAR)).asEnumDataType(blog.raubach.database.codegen.enums.HillsType.class), this, "");

    /**
     * The column <code>blog_db.hills.region</code>.
     */
    public final TableField<HillsRecord, String> REGION = createField(DSL.name("region"), SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>blog_db.hills.url</code>.
     */
    public final TableField<HillsRecord, String> URL = createField(DSL.name("url"), SQLDataType.CLOB, this, "");

    /**
     * The column <code>blog_db.hills.latitude</code>.
     */
    public final TableField<HillsRecord, Double> LATITUDE = createField(DSL.name("latitude"), SQLDataType.DOUBLE, this, "");

    /**
     * The column <code>blog_db.hills.longitude</code>.
     */
    public final TableField<HillsRecord, Double> LONGITUDE = createField(DSL.name("longitude"), SQLDataType.DOUBLE, this, "");

    /**
     * The column <code>blog_db.hills.elevation</code>.
     */
    public final TableField<HillsRecord, Double> ELEVATION = createField(DSL.name("elevation"), SQLDataType.DOUBLE, this, "");

    /**
     * The column <code>blog_db.hills.created_on</code>.
     */
    public final TableField<HillsRecord, Timestamp> CREATED_ON = createField(DSL.name("created_on"), SQLDataType.TIMESTAMP(0).nullable(false).defaultValue(DSL.field("CURRENT_TIMESTAMP", SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>blog_db.hills.updated_on</code>.
     */
    public final TableField<HillsRecord, Timestamp> UPDATED_ON = createField(DSL.name("updated_on"), SQLDataType.TIMESTAMP(0).nullable(false).defaultValue(DSL.field("CURRENT_TIMESTAMP", SQLDataType.TIMESTAMP)), this, "");

    private Hills(Name alias, Table<HillsRecord> aliased) {
        this(alias, aliased, null);
    }

    private Hills(Name alias, Table<HillsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
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

    /**
     * Create a <code>blog_db.hills</code> table reference
     */
    public Hills() {
        this(DSL.name("hills"), null);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : BlogDb.BLOG_DB;
    }

    @Override
    public Identity<HillsRecord, Integer> getIdentity() {
        return (Identity<HillsRecord, Integer>) super.getIdentity();
    }

    @Override
    public UniqueKey<HillsRecord> getPrimaryKey() {
        return Internal.createUniqueKey(Hills.HILLS, DSL.name("KEY_hills_PRIMARY"), new TableField[] { Hills.HILLS.ID }, true);
    }

    @Override
    public Hills as(String alias) {
        return new Hills(DSL.name(alias), this);
    }

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

    // -------------------------------------------------------------------------
    // Row10 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row10<Integer, String, HillsType, String, String, Double, Double, Double, Timestamp, Timestamp> fieldsRow() {
        return (Row10) super.fieldsRow();
    }
    // @formatter:on
}
