package com.example.dm.earthquake_monitor.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dm.earthquake_monitor.Earthquake;

import java.util.List;

public class MainViewModel  extends ViewModel {
    private final MutableLiveData <List<Earthquake>> eqList = new MutableLiveData<List<Earthquake>>();

    public LiveData<List<Earthquake>> getEqList() {
        return eqList;
    }

    private final MainRepository repository = new MainRepository();

    public void getEarthquakes(){
        repository.getEarthquakes(eqList::setValue);
    }

    //this.eqList.setValue(eqList);
    /*private List<Earthquake> parseEarthquakes(String responseString){
        ArrayList<Earthquake> eqList = new ArrayList<>();
        try {
            JSONObject jsonResponse = new JSONObject(responseString);
            JSONArray featuresJSONArray = jsonResponse.getJSONArray("features");
            for(int i = 0; i<featuresJSONArray.length();i++){
                JSONObject jsonFeature = featuresJSONArray.getJSONObject(i);
                String id = jsonFeature.getString("id");

                JSONObject jsonProperties = jsonFeature.getJSONObject("properties");
                double magnitud =jsonProperties.getDouble("mag");
                String place =jsonProperties.getString("place");
                long time =jsonProperties.getLong("time");

                JSONObject jsonGeometry = jsonFeature.getJSONObject("geometry");
                JSONArray coordinatesJSONArray = jsonGeometry.getJSONArray("coordinates");
                double longitude = coordinatesJSONArray.getDouble(0);
                double latitude = coordinatesJSONArray.getDouble(0);

                Earthquake earthquake = new Earthquake(id,place,magnitud,time,latitude,longitude);
                eqList.add(earthquake);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return eqList;
    }*/
}
