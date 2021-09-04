/*
 * This file is generated by jOOQ.
 */
package blog.raubach.database.codegen.tables;


import blog.raubach.database.codegen.BlogDb;
import blog.raubach.database.codegen.tables.records.HikeratingsRecord;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
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
public class Hikeratings extends TableImpl<HikeratingsRecord> {

    private static final long serialVersionUID = 901445705;

    /**
     * The reference instance of <code>blog_db.hikeratings</code>
     */
    public static final Hikeratings HIKERATINGS = new Hikeratings();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<HikeratingsRecord> getRecordType() {
        return HikeratingsRecord.class;
    }

    /**
     * The column <code>blog_db.hikeratings.post_id</code>.
     */
    public final TableField<HikeratingsRecord, Integer> POST_ID = createField("post_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>blog_db.hikeratings.weather</code>.
     */
    public final TableField<HikeratingsRecord, Short> WEATHER = createField("weather", org.jooq.impl.SQLDataType.SMALLINT.defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.SMALLINT)), this, "");

    /**
     * The column <code>blog_db.hikeratings.path</code>.
     */
    public final TableField<HikeratingsRecord, Short> PATH = createField("path", org.jooq.impl.SQLDataType.SMALLINT.defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.SMALLINT)), this, "");

    /**
     * The column <code>blog_db.hikeratings.view</code>.
     */
    public final TableField<HikeratingsRecord, Short> VIEW = createField("view", org.jooq.impl.SQLDataType.SMALLINT.defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.SMALLINT)), this, "");

    /**
     * Create a <code>blog_db.hikeratings</code> table reference
     */
    public Hikeratings() {
        this(DSL.name("hikeratings"), null);
    }

    /**
     * Create an aliased <code>blog_db.hikeratings</code> table reference
     */
    public Hikeratings(String alias) {
        this(DSL.name(alias), HIKERATINGS);
    }

    /**
     * Create an aliased <code>blog_db.hikeratings</code> table reference
     */
    public Hikeratings(Name alias) {
        this(alias, HIKERATINGS);
    }

    private Hikeratings(Name alias, Table<HikeratingsRecord> aliased) {
        this(alias, aliased, null);
    }

    private Hikeratings(Name alias, Table<HikeratingsRecord> aliased, Field<?>[] parameters) {
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
    public UniqueKey<HikeratingsRecord> getPrimaryKey() {
        return Internal.createUniqueKey(blog.raubach.database.codegen.tables.Hikeratings.HIKERATINGS, "KEY_hikeratings_PRIMARY", blog.raubach.database.codegen.tables.Hikeratings.HIKERATINGS.POST_ID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<HikeratingsRecord>> getKeys() {
        return Arrays.<UniqueKey<HikeratingsRecord>>asList(
              Internal.createUniqueKey(blog.raubach.database.codegen.tables.Hikeratings.HIKERATINGS, "KEY_hikeratings_PRIMARY", blog.raubach.database.codegen.tables.Hikeratings.HIKERATINGS.POST_ID)
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Hikeratings as(String alias) {
        return new Hikeratings(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Hikeratings as(Name alias) {
        return new Hikeratings(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Hikeratings rename(String name) {
        return new Hikeratings(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Hikeratings rename(Name name) {
        return new Hikeratings(name, null);
    }
// @formatter:on
}
