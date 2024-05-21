/*
 * This file is generated by jOOQ.
 */
package blog.raubach.database.codegen.tables;


import blog.raubach.database.codegen.BlogDb;
import blog.raubach.database.codegen.tables.records.HillIndividualsRecord;

import java.sql.Timestamp;

import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Row5;
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
public class HillIndividuals extends TableImpl<HillIndividualsRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>blog_db.hill_individuals</code>
     */
    public static final HillIndividuals HILL_INDIVIDUALS = new HillIndividuals();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<HillIndividualsRecord> getRecordType() {
        return HillIndividualsRecord.class;
    }

    /**
     * The column <code>blog_db.hill_individuals.id</code>.
     */
    public final TableField<HillIndividualsRecord, Integer> ID = createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>blog_db.hill_individuals.hill_id</code>.
     */
    public final TableField<HillIndividualsRecord, Integer> HILL_ID = createField(DSL.name("hill_id"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>blog_db.hill_individuals.individual_id</code>.
     */
    public final TableField<HillIndividualsRecord, Integer> INDIVIDUAL_ID = createField(DSL.name("individual_id"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>blog_db.hill_individuals.created_on</code>.
     */
    public final TableField<HillIndividualsRecord, Timestamp> CREATED_ON = createField(DSL.name("created_on"), SQLDataType.TIMESTAMP(0).nullable(false).defaultValue(DSL.field("CURRENT_TIMESTAMP", SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>blog_db.hill_individuals.updated_on</code>.
     */
    public final TableField<HillIndividualsRecord, Timestamp> UPDATED_ON = createField(DSL.name("updated_on"), SQLDataType.TIMESTAMP(0).nullable(false).defaultValue(DSL.field("CURRENT_TIMESTAMP", SQLDataType.TIMESTAMP)), this, "");

    private HillIndividuals(Name alias, Table<HillIndividualsRecord> aliased) {
        this(alias, aliased, null);
    }

    private HillIndividuals(Name alias, Table<HillIndividualsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>blog_db.hill_individuals</code> table reference
     */
    public HillIndividuals(String alias) {
        this(DSL.name(alias), HILL_INDIVIDUALS);
    }

    /**
     * Create an aliased <code>blog_db.hill_individuals</code> table reference
     */
    public HillIndividuals(Name alias) {
        this(alias, HILL_INDIVIDUALS);
    }

    /**
     * Create a <code>blog_db.hill_individuals</code> table reference
     */
    public HillIndividuals() {
        this(DSL.name("hill_individuals"), null);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : BlogDb.BLOG_DB;
    }

    @Override
    public Identity<HillIndividualsRecord, Integer> getIdentity() {
        return (Identity<HillIndividualsRecord, Integer>) super.getIdentity();
    }

    @Override
    public UniqueKey<HillIndividualsRecord> getPrimaryKey() {
        return Internal.createUniqueKey(HillIndividuals.HILL_INDIVIDUALS, DSL.name("KEY_hill_individuals_PRIMARY"), new TableField[] { HillIndividuals.HILL_INDIVIDUALS.ID }, true);
    }

    @Override
    public HillIndividuals as(String alias) {
        return new HillIndividuals(DSL.name(alias), this);
    }

    @Override
    public HillIndividuals as(Name alias) {
        return new HillIndividuals(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public HillIndividuals rename(String name) {
        return new HillIndividuals(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public HillIndividuals rename(Name name) {
        return new HillIndividuals(name, null);
    }

    // -------------------------------------------------------------------------
    // Row5 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row5<Integer, Integer, Integer, Timestamp, Timestamp> fieldsRow() {
        return (Row5) super.fieldsRow();
    }
    // @formatter:on
}
