package practiseapplications.flickapprebuild;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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

public class StartFlick extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_flick);
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




