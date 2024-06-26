/*
 * This file is generated by jOOQ.
 */
package blog.raubach.database.codegen.tables;


import blog.raubach.database.codegen.BlogDb;
import blog.raubach.database.codegen.tables.records.PostsitesRecord;

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
public class Postsites extends TableImpl<PostsitesRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>blog_db.postsites</code>
     */
    public static final Postsites POSTSITES = new Postsites();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<PostsitesRecord> getRecordType() {
        return PostsitesRecord.class;
    }

    /**
     * The column <code>blog_db.postsites.post_id</code>.
     */
    public final TableField<PostsitesRecord, Integer> POST_ID = createField(DSL.name("post_id"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>blog_db.postsites.site_id</code>.
     */
    public final TableField<PostsitesRecord, Integer> SITE_ID = createField(DSL.name("site_id"), SQLDataType.INTEGER.nullable(false), this, "");

    private Postsites(Name alias, Table<PostsitesRecord> aliased) {
        this(alias, aliased, null);
    }

    private Postsites(Name alias, Table<PostsitesRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>blog_db.postsites</code> table reference
     */
    public Postsites(String alias) {
        this(DSL.name(alias), POSTSITES);
    }

    /**
     * Create an aliased <code>blog_db.postsites</code> table reference
     */
    public Postsites(Name alias) {
        this(alias, POSTSITES);
    }

    /**
     * Create a <code>blog_db.postsites</code> table reference
     */
    public Postsites() {
        this(DSL.name("postsites"), null);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : BlogDb.BLOG_DB;
    }

    @Override
    public UniqueKey<PostsitesRecord> getPrimaryKey() {
        return Internal.createUniqueKey(Postsites.POSTSITES, DSL.name("KEY_postsites_PRIMARY"), new TableField[] { Postsites.POSTSITES.POST_ID, Postsites.POSTSITES.SITE_ID }, true);
    }

    @Override
    public Postsites as(String alias) {
        return new Postsites(DSL.name(alias), this);
    }

    @Override
    public Postsites as(Name alias) {
        return new Postsites(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Postsites rename(String name) {
        return new Postsites(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Postsites rename(Name name) {
        return new Postsites(name, null);
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
