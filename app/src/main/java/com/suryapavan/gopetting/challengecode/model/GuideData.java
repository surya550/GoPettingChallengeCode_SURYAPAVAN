package com.suryapavan.gopetting.challengecode.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by surya on 4/4/2017.
 */

public class GuideData {


    @SerializedName("total")
    private String total;

    @SerializedName("startDate")
    private String startDate;
    @SerializedName("objType")
    private String objType;
    @SerializedName("endDate")
    private String endDate;
    @SerializedName("name")
    private String name;
    @SerializedName("loginRequired")
    private boolean loginRequired;
    @SerializedName("url")
    private String url;
    @SerializedName("venue")
    private VenueBean venue;
    @SerializedName("icon")
    private String icon;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getObjType() {
        return objType;
    }

    public void setObjType(String objType) {
        this.objType = objType;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isLoginRequired() {
        return loginRequired;
    }

    public void setLoginRequired(boolean loginRequired) {
        this.loginRequired = loginRequired;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public VenueBean getVenue() {
        return venue;
    }

    public void setVenue(VenueBean venue) {
        this.venue = venue;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public static class VenueBean {
    }
}
