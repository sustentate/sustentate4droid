package ar.com.sustentate.com.api;


import ar.com.sustentate.com.utils.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
 * Created by mzorilla on 11/4/17.
 */

public class RetroUpload {

    private Retrofit postImage() {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
