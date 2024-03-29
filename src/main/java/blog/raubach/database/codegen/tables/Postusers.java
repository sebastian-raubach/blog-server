/*
 * This file is generated by jOOQ.
 */
package blog.raubach.database.codegen.tables;


import blog.raubach.database.codegen.BlogDb;
import blog.raubach.database.codegen.tables.records.PostusersRecord;

import org.jooq.Field;
import org.jooq.Name;
import org.jooq.Row2;
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
public class Postusers extends TableImpl<PostusersRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>blog_db.postusers</code>
     */
    public static final Postusers POSTUSERS = new Postusers();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<PostusersRecord> getRecordType() {
        return PostusersRecord.class;
    }

    /**
     * The column <code>blog_db.postusers.post_id</code>.
     */
    public final TableField<PostusersRecord, Integer> POST_ID = createField(DSL.name("post_id"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>blog_db.postusers.user_id</code>.
     */
    public final TableField<PostusersRecord, Integer> USER_ID = createField(DSL.name("user_id"), SQLDataType.INTEGER.nullable(false), this, "");

    private Postusers(Name alias, Table<PostusersRecord> aliased) {
        this(alias, aliased, null);
    }

    private Postusers(Name alias, Table<PostusersRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>blog_db.postusers</code> table reference
     */
    public Postusers(String alias) {
        this(DSL.name(alias), POSTUSERS);
    }

    /**
     * Create an aliased <code>blog_db.postusers</code> table reference
     */
    public Postusers(Name alias) {
        this(alias, POSTUSERS);
    }

    /**
     * Create a <code>blog_db.postusers</code> table reference
     */
    public Postusers() {
        this(DSL.name("postusers"), null);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : BlogDb.BLOG_DB;
    }

    @Override
    public UniqueKey<PostusersRecord> getPrimaryKey() {
        return Internal.createUniqueKey(Postusers.POSTUSERS, DSL.name("KEY_postusers_PRIMARY"), new TableField[] { Postusers.POSTUSERS.POST_ID, Postusers.POSTUSERS.USER_ID }, true);
    }

    @Override
    public Postusers as(String alias) {
        return new Postusers(DSL.name(alias), this);
    }

    @Override
    public Postusers as(Name alias) {
        return new Postusers(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Postusers rename(String name) {
        return new Postusers(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Postusers rename(Name name) {
        return new Postusers(name, null);
    }

    // -------------------------------------------------------------------------
    // Row2 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row2<Integer, Integer> fieldsRow() {
        return (Row2) super.fieldsRow();
    }
    // @formatter:on
}
