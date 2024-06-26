/*
 * This file is generated by jOOQ.
 */
package blog.raubach.database.codegen.enums;


import org.jooq.Catalog;
import org.jooq.EnumType;
import org.jooq.Schema;


// @formatter:off
/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public enum SitesSitetype implements EnumType {

    campsite("campsite"),

    wildcamp("wildcamp");

    private final String literal;

    private SitesSitetype(String literal) {
        this.literal = literal;
    }

    @Override
    public Catalog getCatalog() {
        return null;
    }

    @Override
    public Schema getSchema() {
        return null;
    }

    @Override
    public String getName() {
        return "sites_sitetype";
    }

    @Override
    public String getLiteral() {
        return literal;
    }

    /**
     * Lookup a value of this EnumType by its literal
     */
    public static SitesSitetype lookupLiteral(String literal) {
        return EnumType.lookupLiteral(SitesSitetype.class, literal);
    }
    // @formatter:on
}
