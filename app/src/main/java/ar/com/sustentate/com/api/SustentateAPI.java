package ar.com.sustentate.com.api;


import java.util.List;

import ar.com.sustentate.com.models.AssistanceRequest;
import ar.com.sustentate.com.models.AssistanceResponse;
import ar.com.sustentate.com.models.ClassificationRequest;
import ar.com.sustentate.com.models.ClassificationResponse;
import ar.com.sustentate.com.models.Evento;
import ar.com.sustentate.com.models.Tip;
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

    @GET("eventos")
    Call<List<Evento>> getEventos(@Query("lastId")int id);

    @POST("chat")
    Call<AssistanceResponse> chat(@Body AssistanceRequest request);

}
