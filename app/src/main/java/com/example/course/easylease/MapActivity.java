package com.example.course.easylease;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
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

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class MapActivity extends AppCompatActivity
        implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener {
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private GoogleMap mMap;

    private List<House> houses = new LinkedList<>();
    private List<MarkerOptions> markers = new LinkedList<>();
    SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        new GetAllHouseInfoTask().execute();


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
            //bundle.putString("house_info", houses.get(index).toString());
            Intent intent = new Intent(MapActivity.this, HouseDetailActivity.class);
            String house = houses.get(index).toJsonString();
            intent.putExtra("house_info", house);
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

    private class GetAllHouseInfoTask extends AsyncTask<Void, Void, String> {
        private boolean isSearchSuccess = true;
        private ProgressDialog progress;
        @Override
        protected void onPreExecute() {
            progress = new ProgressDialog(MapActivity.this, ProgressDialog.STYLE_SPINNER);
            progress.show();
            progress.setMessage("Loading Houses...");
        }

        @Override
        protected String doInBackground(Void... params) {
            String url = "http://52.34.59.35/YBAndroid/Map_results.php";
            RequestBody requestBody = new FormBody.Builder()
                    .build();

            try {
                return HttpController.getInstance().run(url, requestBody);
            } catch (IOException e) {
                isSearchSuccess = false;
            }

            return null;
        }

        @Override
        protected void onPostExecute(String string) {
            progress.dismiss();
            if (isSearchSuccess) {
                try {
                        JSONArray array = new JSONArray(string);
                        int size = array.length();
                        Log.d("Json array length:", size+"");
                        for (int i = 0; i < size; i++) {
                            JSONObject houseJSON = array.getJSONObject(i);

                            House house = new House();
                            house.setAppcontext(MapActivity.this);
                            house.setLatitude(houseJSON.getDouble("latitude"));
                            house.setLongitude(houseJSON.getDouble("longtitude"));
                            house.setAddress(houseJSON.getString("address"));
                            house.setZipCode(houseJSON.getString("zipcode"));
                            house.setName(houseJSON.getString("name"));
                            house.setDescription(houseJSON.getString("description"));
                            house.setOwner(houseJSON.getString("owner"));
                            house.setPrice(houseJSON.getInt("price"));
                            house.setRooms(houseJSON.getInt("rooms"));

                            markers.add(new MarkerOptions()
                                    .position(new LatLng(houseJSON.getDouble("latitude"),
                                            houseJSON.getDouble("longtitude")))
                                    .title(houseJSON.getString("name")));

                            houses.add(house);
                        }

                } catch (JSONException e) {
                    Toast.makeText(MapActivity.this, "Network error!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MapActivity.this, "Network error!", Toast.LENGTH_SHORT).show();
            }
            mapFragment.getMapAsync(MapActivity.this);
        }
    }
}
