package com.example.dm.earthquake_monitor.api;

import java.util.List;

public class EarthquakeJSONResponse {
    private List<Feature> features;
    public  List<Feature> getFeatures(){
      return  features;
    };
}
