/*
 * This file is generated by jOOQ.
 */
package blog.raubach.database.codegen.tables;


import blog.raubach.database.codegen.BlogDb;
import blog.raubach.database.codegen.tables.records.PostIndividualsRecord;

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
public class PostIndividuals extends TableImpl<PostIndividualsRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>blog_db.post_individuals</code>
     */
    public static final PostIndividuals POST_INDIVIDUALS = new PostIndividuals();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<PostIndividualsRecord> getRecordType() {
        return PostIndividualsRecord.class;
    }

    /**
     * The column <code>blog_db.post_individuals.id</code>.
     */
    public final TableField<PostIndividualsRecord, Integer> ID = createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>blog_db.post_individuals.post_id</code>.
     */
    public final TableField<PostIndividualsRecord, Integer> POST_ID = createField(DSL.name("post_id"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>blog_db.post_individuals.individual_id</code>.
     */
    public final TableField<PostIndividualsRecord, Integer> INDIVIDUAL_ID = createField(DSL.name("individual_id"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>blog_db.post_individuals.created_on</code>.
     */
    public final TableField<PostIndividualsRecord, Timestamp> CREATED_ON = createField(DSL.name("created_on"), SQLDataType.TIMESTAMP(0).nullable(false).defaultValue(DSL.field("CURRENT_TIMESTAMP", SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>blog_db.post_individuals.updated_on</code>.
     */
    public final TableField<PostIndividualsRecord, Timestamp> UPDATED_ON = createField(DSL.name("updated_on"), SQLDataType.TIMESTAMP(0).nullable(false).defaultValue(DSL.field("CURRENT_TIMESTAMP", SQLDataType.TIMESTAMP)), this, "");

    private PostIndividuals(Name alias, Table<PostIndividualsRecord> aliased) {
        this(alias, aliased, null);
    }

    private PostIndividuals(Name alias, Table<PostIndividualsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>blog_db.post_individuals</code> table reference
     */
    public PostIndividuals(String alias) {
        this(DSL.name(alias), POST_INDIVIDUALS);
    }

    /**
     * Create an aliased <code>blog_db.post_individuals</code> table reference
     */
    public PostIndividuals(Name alias) {
        this(alias, POST_INDIVIDUALS);
    }

    /**
     * Create a <code>blog_db.post_individuals</code> table reference
     */
    public PostIndividuals() {
        this(DSL.name("post_individuals"), null);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : BlogDb.BLOG_DB;
    }

    @Override
    public Identity<PostIndividualsRecord, Integer> getIdentity() {
        return (Identity<PostIndividualsRecord, Integer>) super.getIdentity();
    }

    @Override
    public UniqueKey<PostIndividualsRecord> getPrimaryKey() {
        return Internal.createUniqueKey(PostIndividuals.POST_INDIVIDUALS, DSL.name("KEY_post_individuals_PRIMARY"), new TableField[] { PostIndividuals.POST_INDIVIDUALS.ID }, true);
    }

    @Override
    public PostIndividuals as(String alias) {
        return new PostIndividuals(DSL.name(alias), this);
    }

    @Override
    public PostIndividuals as(Name alias) {
        return new PostIndividuals(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public PostIndividuals rename(String name) {
        return new PostIndividuals(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public PostIndividuals rename(Name name) {
        return new PostIndividuals(name, null);
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
