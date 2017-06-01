package practiseapplications.flickapprebuild;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;


public class StartFlick extends AppCompatActivity {

        String lat, lon;
        public TextView latitude;
        public TextView longitude;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_start_flick);

                MapsActivity temp = new MapsActivity();
                lat = temp.lat;
                lon = temp.lon;

                latitude = (TextView) findViewById(R.id.latitudetest);
                longitude = (TextView) findViewById(R.id.longitudetest);

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




