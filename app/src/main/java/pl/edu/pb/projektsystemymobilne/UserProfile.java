package pl.edu.pb.projektsystemymobilne;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class UserProfile extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton add_button;

    double latitude, longitude;

    ImageView empty_image;
    TextView empty_text;

    MyDatabaseHelper myDB;
    ArrayList<String> main_id, anime_id, anime_name, anime_score, cordX, cordY;
    CustomAdapter customAdapter;

    private LocationRequest locationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        recyclerView = findViewById(R.id.recycleView);
        add_button = findViewById(R.id.add_button);
        empty_image = findViewById(R.id.empty_image);
        empty_text = findViewById(R.id.empty_text);


        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (ActivityCompat.checkSelfPermission(UserProfile.this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                        if (isGPSEnabled()){
                            LocationServices.getFusedLocationProviderClient(UserProfile.this)
                                    .requestLocationUpdates(locationRequest, new LocationCallback() {
                                        @Override
                                        public void onLocationResult(@NonNull LocationResult locationResult) {
                                            super.onLocationResult(locationResult);
                                            LocationServices.getFusedLocationProviderClient(UserProfile.this)
                                                    .removeLocationUpdates(this);
                                            if (locationResult != null && locationResult.getLocations().size() >0){
                                                int index = locationResult.getLocations().size() - 1;
                                                latitude = locationResult.getLocations().get(index).getLatitude();
                                                longitude = locationResult.getLocations().get(index).getLongitude();
                                                //AddressText.setText("Latitude: "+latitude+"\n" + "Longitude: " + longitude);



                                                //zmiana aktiwiti
                                                Intent intent = new Intent(UserProfile.this, AddAdnime.class);
                                                Bundle b = new Bundle();
                                                b.putDouble("X",longitude);
                                                b.putDouble("Y", latitude);
                                                intent.putExtras(b);
                                                startActivity(intent);
                                            }
                                        }
                                    }, Looper.getMainLooper());

                        }else{
                            turnOnGPS();
                        }
                    }else {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
                    }
                }



            }
        });

        myDB = new MyDatabaseHelper(UserProfile.this);
        main_id = new ArrayList<>();
        anime_id = new ArrayList<>();
        anime_name = new ArrayList<>();
        anime_score = new ArrayList<>();
        cordX = new ArrayList<>();
        cordY = new ArrayList<>();

        storeDataInArrays();

        customAdapter = new CustomAdapter(UserProfile.this, this,main_id, anime_id, anime_name, anime_score, cordX, cordY);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager( UserProfile.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            recreate();
        }
    }

    void storeDataInArrays(){
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0){
            empty_text.setVisibility(View.VISIBLE);
            empty_image.setVisibility(View.VISIBLE);
        }else {
            while (cursor.moveToNext()){
                main_id.add(cursor.getString(0));
                anime_name.add(cursor.getString(1));
                anime_id.add(cursor.getString(2));
                cordX.add(cursor.getString(5));
                cordY.add(cursor.getString(6));
                anime_score.add(cursor.getString(7));
            }
            empty_text.setVisibility(View.GONE);
            empty_image.setVisibility(View.GONE);
        }
    }
    private void turnOnGPS() {

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getApplicationContext())
                .checkLocationSettings(builder.build());

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {

                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    Toast.makeText(UserProfile.this, "GPS is already tured on", Toast.LENGTH_SHORT).show();

                } catch (ApiException e) {

                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException)e;
                                resolvableApiException.startResolutionForResult(UserProfile.this, 2);
                            } catch (IntentSender.SendIntentException ex) {
                                ex.printStackTrace();
                            }
                            break;

                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            //Device does not have location
                            break;
                    }
                }
            }
        });
    }

    private boolean isGPSEnabled(){
        LocationManager locationManager = null;
        boolean isEnabled = false;
        if (locationManager == null){
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        }
        isEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isEnabled;
    }



}