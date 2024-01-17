package pl.edu.pb.projektsystemymobilne;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
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

public class AnimeDetails extends AppCompatActivity {

    private ImageView animeImage;
    private TextView titleOfAnime;
    private TextView detailsOfAnime;
    Button button;

    double latitude, longitude;

    private LocationRequest locationRequest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anime_details);

        //rzeczy do GPSa
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);




        animeImage = findViewById(R.id.AnimeImage);
        titleOfAnime = findViewById(R.id.TitleOfAnime);
        detailsOfAnime = findViewById(R.id.DetailsOfAnime);
        button = findViewById(R.id.addToWatched);

        // Pobierz dane z Intentu
        Intent intent = getIntent();
        if (intent != null) {
            String imageUrl = intent.getStringExtra("imageUrl");
            String title = intent.getStringExtra("title");
            String details = intent.getStringExtra("details");

            //obsługa przycisku
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        if (ActivityCompat.checkSelfPermission(AnimeDetails.this,
                                android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                            if (isGPSEnabled()){
                                LocationServices.getFusedLocationProviderClient(AnimeDetails.this)
                                        .requestLocationUpdates(locationRequest, new LocationCallback() {
                                            @Override
                                            public void onLocationResult(@NonNull LocationResult locationResult) {
                                                super.onLocationResult(locationResult);
                                                LocationServices.getFusedLocationProviderClient(AnimeDetails.this)
                                                        .removeLocationUpdates(this);
                                                if (locationResult != null && locationResult.getLocations().size() >0){
                                                    int index = locationResult.getLocations().size() - 1;
                                                    latitude = locationResult.getLocations().get(index).getLatitude();
                                                    longitude = locationResult.getLocations().get(index).getLongitude();
                                                    //AddressText.setText("Latitude: "+latitude+"\n" + "Longitude: " + longitude);



                                                    //zmiana aktiwiti
                                                    Intent intent_new = new Intent(AnimeDetails.this, AddAdnime.class);
                                                    Bundle b = new Bundle();
                                                    b.putDouble("X",longitude);
                                                    b.putDouble("Y", latitude);
                                                    b.putString("title", intent.getStringExtra("title"));
                                                    intent_new.putExtras(b);
                                                    startActivity(intent_new);
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

            // Ustaw dane w widoku za pomocą Glide
            Glide.with(this)
                    .load(imageUrl)
                    .placeholder(R.drawable.baseline_gpp_maybe_24) // Zdjęcie domyślne, jeśli nie ma wartości
                    .into(animeImage);

            titleOfAnime.setText(title);
            detailsOfAnime.setText(details);
        }
    }
    //włącznie GPSa
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
                    Toast.makeText(AnimeDetails.this, "GPS is already tured on", Toast.LENGTH_SHORT).show();

                } catch (ApiException e) {

                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException)e;
                                resolvableApiException.startResolutionForResult(AnimeDetails.this, 2);
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
