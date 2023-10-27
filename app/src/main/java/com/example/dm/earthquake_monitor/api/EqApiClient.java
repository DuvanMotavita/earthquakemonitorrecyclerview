package com.example.dm.earthquake_monitor.api;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class EqApiClient {

    public interface EqService {
        @GET("all_hour.geojson")
        Call<String> getEarthquakes();
    }
    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build();
    private EqService service;
    private static final EqApiClient ourInstance = new EqApiClient();

    public static  EqApiClient getOurInstance() {
        return ourInstance;
    }

    private EqApiClient(){

    }

    public EqService getService(){
        if(service==null){
            service = retrofit.create(EqService.class);
        }
        return service;
    }

}
