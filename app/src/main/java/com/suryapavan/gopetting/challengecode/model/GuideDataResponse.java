package com.suryapavan.gopetting.challengecode.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by surya on 4/4/2017.
 */

public class GuideDataResponse {

    @SerializedName("data")
    private List<GuideData> dataX;

    public List<GuideData> getDataX() {
        return dataX;
    }

    public void setDataX(List<GuideData> dataX) {
        this.dataX = dataX;
    }
}
