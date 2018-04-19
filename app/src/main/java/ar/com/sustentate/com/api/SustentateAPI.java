package com.sustentate.app.api;

import com.sustentate.app.models.ClassificationRequest;
import com.sustentate.app.models.ClassificationResponse;
import com.sustentate.app.models.Tip;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/*
 * Created by mzorilla on 11/4/17.
 */

public interface SustentateAPI {

    @POST("classification")
    Call<ClassificationResponse> classify(@Body ClassificationRequest request);

    @GET("ecotip")
    Call<List<Tip>> getTips(@Query("lastId") int id);
}
