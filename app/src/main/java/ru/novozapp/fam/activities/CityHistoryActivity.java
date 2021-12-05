package ru.novozapp.fam.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ru.novozapp.fam.activities.databinding.ActivityCityHistoryBinding;

public class CityHistoryActivity extends AppCompatActivity {
    private ActivityCityHistoryBinding historyBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        historyBinding = ActivityCityHistoryBinding.inflate(getLayoutInflater());
        setContentView(historyBinding.getRoot());
    }
}