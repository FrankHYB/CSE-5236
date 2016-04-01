package com.example.course.easylease;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class MapActivity extends AppCompatActivity
        implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener {
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private GoogleMap mMap;
    private String json;

    private List<House> houses = new LinkedList<>();
    private List<MarkerOptions> markers = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Bundle bundle = getIntent().getExtras();

        if (!bundle.isEmpty() && !TextUtils.isEmpty(bundle.getString("json"))) {
            json = bundle.getString("json");

            try {
                JSONArray array = new JSONArray(json);
                int size = array.length();

                for (int i = 0; i < size; i++) {
                    JSONObject houseJSON = array.getJSONObject(i);

                    House house = new House();
                    house.setAppcontext(this);
                    house.setLatitude(houseJSON.getDouble("latitude"));
                    house.setLongitude(houseJSON.getDouble("longtitude"));
                    house.setAddress(houseJSON.getString("address"));
                    house.setZipCode(houseJSON.getString("zipcode"));
                    house.setName(houseJSON.getString("name"));
                    house.setDescription(houseJSON.getString("description"));
                    house.setPrice(houseJSON.getInt("price"));

                    markers.add(new MarkerOptions()
                            .position(new LatLng(houseJSON.getDouble("latitude"),
                                    houseJSON.getDouble("longtitude")))
                            .title(houseJSON.getString("name")));

                    houses.add(house);
                }
            } catch (JSONException e) {
                // Should not happen
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_go_to_search:
                Intent intent = new Intent(MapActivity.this, SearchHouseInfo.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(this);

        for (MarkerOptions options : markers) {
            mMap.addMarker(options);
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow();

        return true;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        int index = indexOfHouse(marker.getTitle());

        if (index != -1) {
            Bundle bundle = new Bundle();
            bundle.putString("house_info", houses.get(index).toString());
            Intent intent = new Intent(MapActivity.this, HouseDetailActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //noinspection MissingPermission
                    mMap.setMyLocationEnabled(true);
                }
            }
        }
    }

    private int indexOfHouse(String name) {
        for (int i = 0; i < houses.size(); i++) {
            if (houses.get(i).getName().equals(name)) {
                return i;
            }
        }

        return -1;
    }
}
