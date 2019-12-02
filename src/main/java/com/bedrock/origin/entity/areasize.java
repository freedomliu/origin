package com.bedrock.origin.beans;

public class areasize {
    private Integer id;

    private String areaname;

    private Double longitudemin;

    private Double longitudemax;

    private Double latitudemin;

    private Double latitudemax;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAreaname() {
        return areaname;
    }

    public void setAreaname(String areaname) {
        this.areaname = areaname == null ? null : areaname.trim();
    }

    public Double getLongitudemin() {
        return longitudemin;
    }

    public void setLongitudemin(Double longitudemin) {
        this.longitudemin = longitudemin;
    }

    public Double getLongitudemax() {
        return longitudemax;
    }

    public void setLongitudemax(Double longitudemax) {
        this.longitudemax = longitudemax;
    }

    public Double getLatitudemin() {
        return latitudemin;
    }

    public void setLatitudemin(Double latitudemin) {
        this.latitudemin = latitudemin;
    }

    public Double getLatitudemax() {
        return latitudemax;
    }

    public void setLatitudemax(Double latitudemax) {
        this.latitudemax = latitudemax;
    }
}