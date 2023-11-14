package com.example.dm.earthquake_monitor;

import android.util.Log;

import com.example.dm.earthquake_monitor.api.EarthquakeJSONResponse;
import com.example.dm.earthquake_monitor.api.EqApiClient;
import com.example.dm.earthquake_monitor.api.Feature;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainRepository {

    public interface  DownloadEqsListener {
        void onEqsDownloaded(List<Earthquake> earthquakeList );
    }

    public void getEarthquakes(DownloadEqsListener downloadEqsListener){
        EqApiClient.EqService service =  EqApiClient.getInstance().getService() ;
        service.getEarthquakes().enqueue(new Callback<EarthquakeJSONResponse>() {
            @Override
            public void onResponse(Call<EarthquakeJSONResponse> call, Response<EarthquakeJSONResponse> response) {
                // List<Earthquake> earthquakeList = parseEarthquakes(response.body());

                List<Earthquake> earthquakeList = getEarthquakesWithMoshi(response.body());
                downloadEqsListener.onEqsDownloaded(earthquakeList);
            }

            @Override
            public void onFailure(Call<EarthquakeJSONResponse> call, Throwable t) {
                Log.d("MainViewModel",t.getMessage());
            }
        });
    }

    private List<Earthquake> getEarthquakesWithMoshi(EarthquakeJSONResponse body) {
        ArrayList<Earthquake> eqList = new ArrayList<>();
        List<Feature> features = body.getFeatures();
        for (Feature feature: features){
            String id = feature.getId();
            double magnitude = feature.getProperties().getMag();
            String place = feature.getProperties().getPlace();
            long time = feature.getProperties().getTime();

            double longitude = feature.getGeometry().getLongitude();
            double latitude = feature.getGeometry().getLatitude();
            Earthquake earthquake = new Earthquake(id,place,magnitude,time,latitude,longitude);
            eqList.add(earthquake);
        }
        return eqList;
    }
}
