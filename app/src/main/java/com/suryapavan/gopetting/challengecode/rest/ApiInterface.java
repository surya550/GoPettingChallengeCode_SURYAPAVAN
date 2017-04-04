package com.suryapavan.gopetting.challengecode.rest;

import com.suryapavan.gopetting.challengecode.model.GuideDataResponse;

import retrofit2.Call;
import retrofit2.http.GET;
/**
 * Created by surya on 4/4/2017.
 */


public interface ApiInterface {

    @GET("upcomingGuides/")
    Call<GuideDataResponse> GetGuideDetails();

}
