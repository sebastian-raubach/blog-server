/*
 * This file is generated by jOOQ.
 */
package blog.raubach.database.codegen.tables;


import blog.raubach.database.codegen.BlogDb;
import blog.raubach.database.codegen.tables.records.PosthillsRecord;

import org.jooq.Field;
import org.jooq.Name;
import org.jooq.Row3;
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
public class Posthills extends TableImpl<PosthillsRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>blog_db.posthills</code>
     */
    public static final Posthills POSTHILLS = new Posthills();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<PosthillsRecord> getRecordType() {
        return PosthillsRecord.class;
    }

    /**
     * The column <code>blog_db.posthills.post_id</code>.
     */
    public final TableField<PosthillsRecord, Integer> POST_ID = createField(DSL.name("post_id"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>blog_db.posthills.hill_id</code>.
     */
    public final TableField<PosthillsRecord, Integer> HILL_ID = createField(DSL.name("hill_id"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>blog_db.posthills.successful</code>.
     */
    public final TableField<PosthillsRecord, Boolean> SUCCESSFUL = createField(DSL.name("successful"), SQLDataType.BOOLEAN.nullable(false).defaultValue(DSL.inline("1", SQLDataType.BOOLEAN)), this, "");

    private Posthills(Name alias, Table<PosthillsRecord> aliased) {
        this(alias, aliased, null);
    }

    private Posthills(Name alias, Table<PosthillsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>blog_db.posthills</code> table reference
     */
    public Posthills(String alias) {
        this(DSL.name(alias), POSTHILLS);
    }

    /**
     * Create an aliased <code>blog_db.posthills</code> table reference
     */
    public Posthills(Name alias) {
        this(alias, POSTHILLS);
    }

    /**
     * Create a <code>blog_db.posthills</code> table reference
     */
    public Posthills() {
        this(DSL.name("posthills"), null);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : BlogDb.BLOG_DB;
    }

    @Override
    public UniqueKey<PosthillsRecord> getPrimaryKey() {
        return Internal.createUniqueKey(Posthills.POSTHILLS, DSL.name("KEY_posthills_PRIMARY"), new TableField[] { Posthills.POSTHILLS.POST_ID, Posthills.POSTHILLS.HILL_ID }, true);
    }

    @Override
    public Posthills as(String alias) {
        return new Posthills(DSL.name(alias), this);
    }

    @Override
    public Posthills as(Name alias) {
        return new Posthills(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Posthills rename(String name) {
        return new Posthills(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Posthills rename(Name name) {
        return new Posthills(name, null);
    }

    // -------------------------------------------------------------------------
    // Row3 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row3<Integer, Integer, Boolean> fieldsRow() {
        return (Row3) super.fieldsRow();
    }
    // @formatter:on
}
