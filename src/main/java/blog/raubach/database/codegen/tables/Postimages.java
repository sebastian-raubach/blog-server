/*
 * This file is generated by jOOQ.
 */
package blog.raubach.database.codegen.tables;


import blog.raubach.database.codegen.BlogDb;
import blog.raubach.database.codegen.tables.records.PostimagesRecord;

import org.jooq.Field;
import org.jooq.Name;
import org.jooq.Row4;
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
public class Postimages extends TableImpl<PostimagesRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>blog_db.postimages</code>
     */
    public static final Postimages POSTIMAGES = new Postimages();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<PostimagesRecord> getRecordType() {
        return PostimagesRecord.class;
    }

    /**
     * The column <code>blog_db.postimages.post_id</code>.
     */
    public final TableField<PostimagesRecord, Integer> POST_ID = createField(DSL.name("post_id"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>blog_db.postimages.image_id</code>.
     */
    public final TableField<PostimagesRecord, Integer> IMAGE_ID = createField(DSL.name("image_id"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>blog_db.postimages.is_primary</code>.
     */
    public final TableField<PostimagesRecord, Boolean> IS_PRIMARY = createField(DSL.name("is_primary"), SQLDataType.BOOLEAN.nullable(false).defaultValue(DSL.inline("0", SQLDataType.BOOLEAN)), this, "");

    /**
     * The column <code>blog_db.postimages.description</code>.
     */
    public final TableField<PostimagesRecord, String> DESCRIPTION = createField(DSL.name("description"), SQLDataType.CLOB.nullable(false), this, "");

    private Postimages(Name alias, Table<PostimagesRecord> aliased) {
        this(alias, aliased, null);
    }

    private Postimages(Name alias, Table<PostimagesRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>blog_db.postimages</code> table reference
     */
    public Postimages(String alias) {
        this(DSL.name(alias), POSTIMAGES);
    }

    /**
     * Create an aliased <code>blog_db.postimages</code> table reference
     */
    public Postimages(Name alias) {
        this(alias, POSTIMAGES);
    }

    /**
     * Create a <code>blog_db.postimages</code> table reference
     */
    public Postimages() {
        this(DSL.name("postimages"), null);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : BlogDb.BLOG_DB;
    }

    @Override
    public UniqueKey<PostimagesRecord> getPrimaryKey() {
        return Internal.createUniqueKey(Postimages.POSTIMAGES, DSL.name("KEY_postimages_PRIMARY"), new TableField[] { Postimages.POSTIMAGES.POST_ID, Postimages.POSTIMAGES.IMAGE_ID }, true);
    }

    @Override
    public Postimages as(String alias) {
        return new Postimages(DSL.name(alias), this);
    }

    @Override
    public Postimages as(Name alias) {
        return new Postimages(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Postimages rename(String name) {
        return new Postimages(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Postimages rename(Name name) {
        return new Postimages(name, null);
    }

    // -------------------------------------------------------------------------
    // Row4 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row4<Integer, Integer, Boolean, String> fieldsRow() {
        return (Row4) super.fieldsRow();
    }
    // @formatter:on
}
