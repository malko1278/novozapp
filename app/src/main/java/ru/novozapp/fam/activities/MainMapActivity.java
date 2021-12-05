package ru.novozapp.fam.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import ru.novozapp.fam.activities.databinding.ActivityMapMainBinding;
import ru.novozapp.fam.adapter.AutoViewAdapter;
import ru.novozapp.fam.models.Monument;
import ru.novozapp.fam.settings.AppSettings;
import ru.novozapp.fam.utils.ServiceReturnData;

public class MainMapActivity extends FragmentActivity implements View.OnClickListener, LocationListener, OnMapReadyCallback,
        TextWatcher, GoogleMap.OnMarkerClickListener {
    private Context mainContext;
    private ActivityMapMainBinding mainBinding;

    private LocationManager locManage;
    private static final int PERMS_CALL_ID = 1234;
    private SupportMapFragment mapFragment;
    private GoogleMap googleMap;

    private Monument MONUMENT_ACTUEL;
    private AutoViewAdapter autoViewAdapter;
    private ArrayList<Monument> listMonument;
    private boolean MOOVE_MARKER = true;
    private Marker MARKER_MONUMENT = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMapMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        mainContext = this;
        mainBinding.hideViewMeteo.hide();
        mainBinding.hideSearchView.hide();
        mainBinding.fragmentTourisme.contentInterface.setVisibility(View.GONE);
        mainBinding.includeSearchLocation.linearSearch.setVisibility(View.GONE);

        mainBinding.showViewMeteo.setOnClickListener(this);
        mainBinding.hideViewMeteo.setOnClickListener(this);
        mainBinding.showSearchView.setOnClickListener(this);
        mainBinding.hideSearchView.setOnClickListener(this);
        mainBinding.fragmentTourisme.includeTourisme.btnHitory.setOnClickListener(this);
        mainBinding.fragmentTourisme.includeTourisme.btnMonument.setOnClickListener(this);

        // Sites recovery
        takeData();
        //
        initFragment();
        mainBinding.includeSearchLocation.autoSearchLocation.addTextChangedListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param view The view that was clicked.
     */
    @Override
    public void onClick(View view) {
        if (mainBinding.hideViewMeteo.equals(view)) {
            mainBinding.hideViewMeteo.hide();
            mainBinding.showViewMeteo.show();
            mainBinding.fragmentTourisme.contentInterface.setVisibility(View.GONE);
        } else {
            if (mainBinding.showViewMeteo.equals(view)) {
                mainBinding.hideViewMeteo.show();
                mainBinding.showViewMeteo.hide();
                mainBinding.fragmentTourisme.contentInterface.setVisibility(View.VISIBLE);
            } else {
                if (mainBinding.showSearchView.equals(view)) {
                    mainBinding.hideSearchView.show();
                    mainBinding.showSearchView.hide();
                    mainBinding.includeSearchLocation.linearSearch.setVisibility(View.VISIBLE);
                    //
                    // If there is text, search and display it
                } else {
                    if (mainBinding.hideSearchView.equals(view)) {
                        //
                        mainBinding.hideSearchView.hide();
                        mainBinding.showSearchView.show();
                        mainBinding.includeSearchLocation.linearSearch.setVisibility(View.GONE);
                        mainBinding.includeSearchLocation.autoSearchLocation.setText("");
                    } else {
                        if (mainBinding.fragmentTourisme.includeTourisme.btnHitory.equals(view)) {
                            Intent i = new Intent(MainMapActivity.this, CityHistoryActivity.class);
                            startActivity(i);
                        } else {
                            if (mainBinding.fragmentTourisme.includeTourisme.btnMonument.equals(view)) {
                                Intent i = new Intent(MainMapActivity.this, MonumentActivity.class);
                                startActivity(i);
                            } else {}
                        }
                    }
                }
            }
        }
    }

    private void takeData() {
        // Initialisation de la variable qui contiendra toutes les échéances
        listMonument = ServiceReturnData.getRessaourceMonument(mainContext);
    }

    /**
     *
     */
    private void initFragment() {
        if (listMonument.size() > 0) {
            MONUMENT_ACTUEL = null;
            FragmentManager fragManager = getSupportFragmentManager();
            mapFragment = (SupportMapFragment) fragManager.findFragmentById(R.id.nach_carta);

            autoViewAdapter = new AutoViewAdapter(mainContext, listMonument);
            mainBinding.includeSearchLocation.autoSearchLocation.setAdapter(autoViewAdapter);
        } else    Toast.makeText(this, "Не удалось загрузить памятники...", Toast.LENGTH_LONG).show();
    }

    // PERMISSION_GRANTED
    private void loadMap() {
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        if(MOOVE_MARKER) {
            if(googleMap != null) {
                if(MARKER_MONUMENT != null) {
                    MARKER_MONUMENT.remove();
                    MARKER_MONUMENT = null;
                }
                double lat = 0, log = 0;
                String title = "Город Новозыбковa", strSnip = "Население: 39,510";
                if (MONUMENT_ACTUEL != null) {
                    lat = MONUMENT_ACTUEL.getLatitude_monut();
                    log = MONUMENT_ACTUEL.getLongitude_monut();
                    title = MONUMENT_ACTUEL.getName_monument();
                    strSnip = "Адрес : " + MONUMENT_ACTUEL.getAdresse();
                } else {
                    lat = AppSettings.LATITUDE;
                    log = AppSettings.LONGITUDE;
                }
                addMarkersToMap(lat, log, title, strSnip);

                googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat, log)));
            }
            MOOVE_MARKER = false;
        } else {}
    }

    @SuppressWarnings("MissingPermission")
    @Override
    public void onMapReady(GoogleMap gMap) {
        googleMap = gMap;
        googleMap.moveCamera(CameraUpdateFactory.zoomBy(15));   // 15
        googleMap.setMyLocationEnabled(true);
        addMarkersToMap(AppSettings.LATITUDE, AppSettings.LONGITUDE, "Город Новозыбков", "Население: 39,510");
        // googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        // Set a listener for marker click.
        googleMap.setOnMarkerClickListener(this);
    }

    /**
     *
     * @param lat
     * @param log
     * @param title
     * @param snipe
     */
    private void addMarkersToMap(double lat, double log, String title, String snipe) {
        // Add some markers to the map, and add a data object to each marker.
        MARKER_MONUMENT = googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(lat, log))
                .title(title)
                .snippet(snipe)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        // Directly displays the information window
        MARKER_MONUMENT.showInfoWindow();
        MARKER_MONUMENT.setTag(0);

    }

    private void checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            }, PERMS_CALL_ID);
            return;
        }
        locManage = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (locManage.isProviderEnabled(LocationManager.GPS_PROVIDER))
            locManage.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 0, this);
        if(locManage.isProviderEnabled(LocationManager.PASSIVE_PROVIDER))
            locManage.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 500, 0, this);
        if(locManage.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
            locManage.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 0, this);
        loadMap();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(locManage != null)    locManage.removeUpdates(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPermissions();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == PERMS_CALL_ID)    checkPermissions();
    }

    /**
     * This method is called to notify you that, within <code>s</code>,
     * the <code>count</code> characters beginning at <code>start</code>
     * are about to be replaced by new text with length <code>after</code>.
     * It is an error to attempt to make changes to <code>s</code> from
     * this callback.
     *
     * @param s
     * @param start
     * @param count
     * @param after
     */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    /**
     * This method is called to notify you that, within <code>s</code>,
     * the <code>count</code> characters beginning at <code>start</code>
     * have just replaced old text that had length <code>before</code>.
     * It is an error to attempt to make changes to <code>s</code> from
     * this callback.
     *
     * @param s
     * @param start
     * @param before
     * @param count
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}

    /**
     * This method is called to notify you that, somewhere within
     * <code>s</code>, the text has been changed.
     * It is legitimate to make further changes to <code>s</code> from
     * this callback, but be careful not to get yourself into an infinite
     * loop, because any changes you make will cause this method to be
     * called again recursively.
     * (You are not told where the change took place because other
     * afterTextChanged() methods may already have made other changes
     * and invalidated the offsets.  But if you need to know here,
     * you can use {@link Spannable#setSpan} in {@link #onTextChanged}
     * to mark your place and then look up from here where the span
     * ended up.
     *
     * @param s
     */
    @Override
    public void afterTextChanged(Editable s) {
        s.getFilters();
        getMonutSelect(mainBinding.includeSearchLocation.autoSearchLocation.getText().toString());
    }

    /**
     *
     * @param name
     * @return
     */
    private void getMonutSelect(String name) {
        if(name.equals("")) {
            MONUMENT_ACTUEL = null;
            MOOVE_MARKER = true;
            onLocationChanged(null);
        } else {
            for(int i = 0; i < listMonument.size(); i++) {
                if(name.equals(listMonument.get(i).getAdresse()) || name.equals(listMonument.get(i).getName_monument())) {
                    MONUMENT_ACTUEL = listMonument.get(i);
                    MOOVE_MARKER = true;
                    onLocationChanged(null);
                    return;
                } /*else {
                if(MONUMENT_ACTUEL != null)   MONUMENT_ACTUEL = null;
            }*/
            }
        }
    }

    /** Called when the user clicks a marker. */
    @Override
    public boolean onMarkerClick(final Marker marker) {
        // Retrieve the data from the marker.
        Integer clickCount = (Integer) marker.getTag();

        // Check if a click count was set, then display the click count.
        if (clickCount != null) {
            if(MONUMENT_ACTUEL != null) {
                Intent intMonut = new Intent(MainMapActivity.this, ItemMonumentActivity.class);
                intMonut.putExtra("NAME", MONUMENT_ACTUEL.getName_monument());
                intMonut.putExtra("IMAGE", MONUMENT_ACTUEL.getImage_monument());
                intMonut.putExtra("DESCRIPTION", MONUMENT_ACTUEL.getDescription());
                startActivity(intMonut);
            } else   Toast.makeText(this, "Только памятник может быть отображен.", Toast.LENGTH_SHORT).show();
        }

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }
}