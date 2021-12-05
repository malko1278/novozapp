package ru.novozapp.fam.activities;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import ru.novozapp.fam.activities.databinding.WeatherActivityBinding;
import ru.novozapp.fam.adapter.WeatherAdapter;

public class WeatherActivity extends AppCompatActivity {

    private Context weatherContext;
    public static final String TAG = WeatherActivity.class.getSimpleName();
    private WeatherActivityBinding weatherBinding;
    public static float density;
    private WeatherAdapter weatherAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        weatherBinding = WeatherActivityBinding.inflate(getLayoutInflater());
        setContentView(weatherBinding.getRoot());

        weatherContext = this;
        density = getResources().getDisplayMetrics().density;
    }

    @Override
    protected void onResume() {
        super.onResume();
        // NovozApp application = (NovozApp) getApplication();
        final Tracker tracker = ((NovozApp) getApplication()).getTracker();
        if(tracker != null){
            tracker.setScreenName(getClass().getSimpleName());
            tracker.send(new HitBuilders.ScreenViewBuilder().build());
        }
    }

    private class OnBackClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            // (MainMapActivity) getActivity().getSupportFragmentManager().popBackStack();
        }
    }
}