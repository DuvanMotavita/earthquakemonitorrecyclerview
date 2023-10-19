package com.example.dm.earthquake_monitor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.dm.earthquake_monitor.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.eqRecycler.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<Earthquake> eqList = new ArrayList<>();
        eqList.add(new Earthquake("dsfsdf","Buenos Aires",5.0,545454545L,105.23,98.127));
        eqList.add(new Earthquake("dsfsdf","Bogota",4.0,545454545L,105.23,98.127));
        eqList.add(new Earthquake("dsfsdf","Honolulu",3.5,545454545L,105.23,98.127));
        eqList.add(new Earthquake("dsfsdf","Medellin",6.0,545454545L,105.23,98.127));
        eqList.add(new Earthquake("dsfsdf","Villeta",6.2,545454545L,105.23,98.127));
        eqList.add(new Earthquake("dsfsdf","Villavicencio",3.0,545454545L,105.23,98.127));
        EqAdapter adapter = new EqAdapter();
        binding.eqRecycler.setAdapter(adapter);
        adapter.submitList(eqList);
    }
}