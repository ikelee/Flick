package practiseapplications.flickapprebuild;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;

public class StartFlick extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

        private GoogleMap mMap;
        private UiSettings mUiSettings;
        protected GoogleApiClient mGoogleApiClient;
        private Button button;
        public TextView latitude;
        public TextView longitude;
        public static String lat, lon;
        LocationManager locationManager;
        MapFragment mapFragment;
        boolean connection = false;

        Location mLastLocation;
        LocationRequest mLocationRequest;

        public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;


        //This builds the API Client.
        protected synchronized void buildGoogleApiClient() {
                Toast.makeText(this, "buildGoogleApiClient", Toast.LENGTH_SHORT).show();
                mGoogleApiClient = new GoogleApiClient.Builder(this)
                        .addConnectionCallbacks(this)
                        .addOnConnectionFailedListener(this)
                        .addApi(LocationServices.API)
                        .build();
        }


        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_maps);

                //This checks for permission, if that wasn't obvious enough.
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        checkLocationPermission();
                }

                //This builds the API client.
                buildGoogleApiClient();


                //This part actually initializes the map, and is not glitchy.
                mapFragment = (MapFragment) getFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(this);

                //This is just for testing purposes. Sike not anymore.
                latitude = (TextView) findViewById(R.id.latitude);
                longitude = (TextView) findViewById(R.id.longitude);

        }

        //Calls the Droid system to uplink the Death Star Blueprint. Also, the permission.
        private void checkLocationPermission() {
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                MY_PERMISSIONS_REQUEST_LOCATION);
                }

        }

        //starts the API client.
        protected void onStart() {
                mGoogleApiClient.connect();
                super.onStart();
        }

        //Stops the API client.
        protected void onStop() {
                mGoogleApiClient.disconnect();
                super.onStop();
        }


        //Happens when map is ready.
        @Override
        public void onMapReady(GoogleMap map) {
                mUiSettings = map.getUiSettings();
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                }
                map.setMyLocationEnabled(true);
                Toast.makeText(this, "mapReady", Toast.LENGTH_SHORT).show();

        }

        //Happens when API connects. Still unsure whether this and the map getting ready is synchronous or asynchronous.
        @Override
        public void onConnected(Bundle bundle) {
                Toast.makeText(this, "OnConnected", Toast.LENGTH_SHORT).show();

                mLocationRequest = LocationRequest.create();
                mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
                mLocationRequest.setMaxWaitTime(5000);
                mLocationRequest.setInterval(10000);

                try {
                        LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

                        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                                mGoogleApiClient);
                        updateUI();
                } catch (SecurityException e) {
                        Log.e("LocatonUpdate", e + " ");
                }
                if (mLastLocation != null) {
                        lat = String.valueOf(mLastLocation.getLatitude());
                        lon = String.valueOf(mLastLocation.getLongitude());
                }
                updateUI();

       /* CameraUpdate center =
                CameraUpdateFactory.newLatLng(new LatLng(mLastLocation.getLatitude(),
                        mLastLocation.getLongitude()));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);

        mMap.moveCamera(center);
        mMap.animateCamera(zoom);*/
        }

        @Override
        public void onConnectionSuspended(int i) {
                Toast.makeText(this, "onConnectionSuspended", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                Toast.makeText(this, "onConnectionFailed", Toast.LENGTH_SHORT).show();
                buildGoogleApiClient();
        }

        //Is called when the user moves around.
        public void onLocationChanged(Location location) {

                Toast.makeText(this, "Location Changed", Toast.LENGTH_SHORT).show();
                latitude.setText(String.valueOf(location.getLatitude()));
                longitude.setText(String.valueOf(location.getLongitude()));
                updateUI();
        }

        //Updates my testing UI up top. And, moves camera to current location.
        void updateUI() {
                latitude.setText(lat);
                longitude.setText(lon);

        }
}







/*
public class StartFlick extends AppCompatActivity {

    CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
            getApplicationContext(),
            "us-east-1:cc4e4511-5a69-43a5-bd3b-bce3da703344", // Identity Pool ID
            Regions.US_EAST_1 // Region
    );


    AmazonDynamoDBClient ddbClient = new AmazonDynamoDBAsyncClient(credentialsProvider);
    DynamoDBMapper mapper = new DynamoDBMapper(ddbClient);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_flick);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Events event = new Events();
                event.setEventid("TestEvent");
                event.setUserid("Ike");
                event.setLatitude("N22345.23");
                event.setLongitude("S3829,34");

                mapper.save(event);
            }
        };
        Thread myThread = new Thread(runnable);
        myThread.start();
    }


    @DynamoDBTable(tableName = "eventsByLocation")
    public class Events {
        private String eventid;
        private String userid;
        private String longitude;
        private String latitude;

        @DynamoDBIndexRangeKey(attributeName = "eventid")
        public String getEventid() {
            return eventid;
        }

        public void setEventid(String eventid) {
            this.eventid = eventid;
        }

        @DynamoDBIndexHashKey(attributeName = "userid")
        public String getUserid() {
            return userid;
        }
        public void setUserid(String userid) {
            this.userid = userid;
        }

        @DynamoDBAttribute(attributeName = "longitude")
        public String getLongitude () {
            return longitude;
        }
        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        @DynamoDBAttribute(attributeName = "latitude")
        public String getLatitude () {
            return latitude;
        }
        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }
    }

    */




