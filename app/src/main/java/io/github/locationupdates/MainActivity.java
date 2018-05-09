package io.github.locationupdates;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import io.github.privacysecurer.core.Event;
import io.github.privacysecurer.core.GeolocationCallback;
import io.github.privacysecurer.core.GeolocationEvent;
import io.github.privacysecurer.core.UQI;
import io.github.privacysecurer.location.Geolocation;
import io.github.privacysecurer.location.LatLon;
import io.github.privacysecurer.utils.Globals;

public class MainActivity extends AppCompatActivity {
    private Button mStartUpdatesButton;
    private Button mStopUpdatesButton;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Globals.LocationConfig.useGoogleService = true;

        final UQI uqi = new UQI(this);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        mStartUpdatesButton = (Button) findViewById(R.id.start_updates_button);
        mStopUpdatesButton = (Button) findViewById(R.id.stop_updates_button);
        textView = (TextView) findViewById(R.id.latlon);
        textView.setPadding(2,400,2,2);

        mStartUpdatesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Event geolocationEvent = new GeolocationEvent.GeolocationEventBuilder()
                                            .setFieldName(GeolocationEvent.Location)
                                            .setOperator(GeolocationEvent.Updated)
                                            .setInterval(3000)
                                            .setLocationPrecision(Geolocation.ACCURACY)
                                            .build();
                uqi.addEventListener(geolocationEvent, new GeolocationCallback() {
                    @Override
                    public void onEvent() {
                        LatLon latLon = this.getLatLon();
                        Log.d("Log", "latitude: "+String.valueOf(latLon.getLatitude()));
                        Log.d("Log", "longitude: "+String.valueOf(latLon.getLongitude()));
                        textView.setText("latitude: "+latLon.getLatitude()+" "+"longitude: "+latLon.getLongitude());
                    }
                });
            }
        });

    }
}
