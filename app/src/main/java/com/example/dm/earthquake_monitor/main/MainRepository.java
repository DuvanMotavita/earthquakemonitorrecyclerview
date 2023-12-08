package com.example.dm.earthquake_monitor.main;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.dm.earthquake_monitor.Earthquake;
import com.example.dm.earthquake_monitor.api.EarthquakeJSONResponse;
import com.example.dm.earthquake_monitor.api.EqApiClient;
import com.example.dm.earthquake_monitor.api.Feature;
import com.example.dm.earthquake_monitor.database.EqDatabase;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainRepository {

    public interface  DownloadEqsListener {
        void onEqsDownloaded(List<Earthquake> earthquakeList );
    }
    private final EqDatabase database;

    public interface  DownloadStatusListener{
        void downloadSuccess();
        void downloadError(String message);
    }

    public MainRepository(EqDatabase database) {
        this.database = database;
    }

    public LiveData<List<Earthquake>> getEqList(){
        return database.eqDao().getEarthQuakes();
    }

    public void downloadAndSaveEarthquakes( DownloadStatusListener downloadStatusListener /*DownloadEqsListener downloadEqsListener*/){
        EqApiClient.EqService service =  EqApiClient.getInstance().getService() ;
        service.getEarthquakes().enqueue(new Callback<EarthquakeJSONResponse>() {
            @Override
            public void onResponse(Call<EarthquakeJSONResponse> call, Response<EarthquakeJSONResponse> response) {
                // List<Earthquake> earthquakeList = parseEarthquakes(response.body());

                List<Earthquake> earthquakeList = getEarthquakesWithMoshi(response.body());
                EqDatabase.databaseWriteExecutor.execute(()->{
                    database.eqDao().insertAll(earthquakeList);
                });
                //downloadEqsListener.onEqsDownloaded(earthquakeList);
                downloadStatusListener.downloadSuccess();
            }

            @Override
            public void onFailure(Call<EarthquakeJSONResponse> call, Throwable t) {
                //Log.d("MainViewModel",t.getMessage());
                downloadStatusListener.downloadError("There was an error in downloading earthquakes, check internet connection");
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
