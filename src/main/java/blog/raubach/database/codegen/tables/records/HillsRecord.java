/*
 * This file is generated by jOOQ.
 */
package blog.raubach.database.codegen.tables.records;


import blog.raubach.database.codegen.enums.HillsType;
import blog.raubach.database.codegen.tables.Hills;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record10;
import org.jooq.Row10;
import org.jooq.impl.UpdatableRecordImpl;


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
public class HillsRecord extends UpdatableRecordImpl<HillsRecord> implements Record10<Integer, String, HillsType, String, String, Double, Double, Double, Timestamp, Timestamp> {

    private static final long serialVersionUID = 1895457238;

    /**
     * Setter for <code>blog_db.hills.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>blog_db.hills.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>blog_db.hills.name</code>.
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>blog_db.hills.name</code>.
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>blog_db.hills.type</code>.
     */
    public void setType(HillsType value) {
        set(2, value);
    }

    /**
     * Getter for <code>blog_db.hills.type</code>.
     */
    public HillsType getType() {
        return (HillsType) get(2);
    }

    /**
     * Setter for <code>blog_db.hills.region</code>.
     */
    public void setRegion(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>blog_db.hills.region</code>.
     */
    public String getRegion() {
        return (String) get(3);
    }

    /**
     * Setter for <code>blog_db.hills.url</code>.
     */
    public void setUrl(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>blog_db.hills.url</code>.
     */
    public String getUrl() {
        return (String) get(4);
    }

    /**
     * Setter for <code>blog_db.hills.latitude</code>.
     */
    public void setLatitude(Double value) {
        set(5, value);
    }

    /**
     * Getter for <code>blog_db.hills.latitude</code>.
     */
    public Double getLatitude() {
        return (Double) get(5);
    }

    /**
     * Setter for <code>blog_db.hills.longitude</code>.
     */
    public void setLongitude(Double value) {
        set(6, value);
    }

    /**
     * Getter for <code>blog_db.hills.longitude</code>.
     */
    public Double getLongitude() {
        return (Double) get(6);
    }

    /**
     * Setter for <code>blog_db.hills.elevation</code>.
     */
    public void setElevation(Double value) {
        set(7, value);
    }

    /**
     * Getter for <code>blog_db.hills.elevation</code>.
     */
    public Double getElevation() {
        return (Double) get(7);
    }

    /**
     * Setter for <code>blog_db.hills.created_on</code>.
     */
    public void setCreatedOn(Timestamp value) {
        set(8, value);
    }

    /**
     * Getter for <code>blog_db.hills.created_on</code>.
     */
    public Timestamp getCreatedOn() {
        return (Timestamp) get(8);
    }

    /**
     * Setter for <code>blog_db.hills.updated_on</code>.
     */
    public void setUpdatedOn(Timestamp value) {
        set(9, value);
    }

    /**
     * Getter for <code>blog_db.hills.updated_on</code>.
     */
    public Timestamp getUpdatedOn() {
        return (Timestamp) get(9);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record10 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row10<Integer, String, HillsType, String, String, Double, Double, Double, Timestamp, Timestamp> fieldsRow() {
        return (Row10) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row10<Integer, String, HillsType, String, String, Double, Double, Double, Timestamp, Timestamp> valuesRow() {
        return (Row10) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return Hills.HILLS.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return Hills.HILLS.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<HillsType> field3() {
        return Hills.HILLS.TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return Hills.HILLS.REGION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return Hills.HILLS.URL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Double> field6() {
        return Hills.HILLS.LATITUDE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Double> field7() {
        return Hills.HILLS.LONGITUDE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Double> field8() {
        return Hills.HILLS.ELEVATION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field9() {
        return Hills.HILLS.CREATED_ON;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field10() {
        return Hills.HILLS.UPDATED_ON;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HillsType component3() {
        return getType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getRegion();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getUrl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double component6() {
        return getLatitude();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double component7() {
        return getLongitude();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double component8() {
        return getElevation();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component9() {
        return getCreatedOn();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component10() {
        return getUpdatedOn();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HillsType value3() {
        return getType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getRegion();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getUrl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double value6() {
        return getLatitude();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double value7() {
        return getLongitude();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double value8() {
        return getElevation();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value9() {
        return getCreatedOn();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value10() {
        return getUpdatedOn();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HillsRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HillsRecord value2(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HillsRecord value3(HillsType value) {
        setType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HillsRecord value4(String value) {
        setRegion(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HillsRecord value5(String value) {
        setUrl(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HillsRecord value6(Double value) {
        setLatitude(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HillsRecord value7(Double value) {
        setLongitude(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HillsRecord value8(Double value) {
        setElevation(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HillsRecord value9(Timestamp value) {
        setCreatedOn(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HillsRecord value10(Timestamp value) {
        setUpdatedOn(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HillsRecord values(Integer value1, String value2, HillsType value3, String value4, String value5, Double value6, Double value7, Double value8, Timestamp value9, Timestamp value10) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        value10(value10);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached HillsRecord
     */
    public HillsRecord() {
        super(Hills.HILLS);
    }

    /**
     * Create a detached, initialised HillsRecord
     */
    public HillsRecord(Integer id, String name, HillsType type, String region, String url, Double latitude, Double longitude, Double elevation, Timestamp createdOn, Timestamp updatedOn) {
        super(Hills.HILLS);

        set(0, id);
        set(1, name);
        set(2, type);
        set(3, region);
        set(4, url);
        set(5, latitude);
        set(6, longitude);
        set(7, elevation);
        set(8, createdOn);
        set(9, updatedOn);
    }
// @formatter:on
}
