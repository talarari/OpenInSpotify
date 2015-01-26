package com.openinspotify;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.openinspotify.extracors.ShazamExtractor;
import com.openinspotify.extracors.TrackDetails;
import com.openinspotify.extracors.TrackDetailsExtractor;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private static final ArrayList<TrackDetailsExtractor> EXTRACTORS =  new ArrayList<TrackDetailsExtractor>(){{
       add(new ShazamExtractor());
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getIntent().getAction().equals(Intent.ACTION_SEND)) {

            String trackShareText = getIntent().getStringExtra(Intent.EXTRA_TEXT);

            try {
                TrackDetails trackDetails = getTrackDetails(trackShareText);
                String uri = String.format("spotify:search:%s+%s",trackDetails.ArtistName,trackDetails.TrackName);
                Intent launcher = new Intent( Intent.ACTION_VIEW, Uri.parse(uri) );
                startActivity(launcher);
                finish();
            } catch (FailedToExtractTrackDetailsException e) {
                Toast.makeText(MainActivity.this,"We dont support sharing from this app yet, sorry!",Toast.LENGTH_SHORT);
                return;
            }
        }
    }

    private TrackDetails getTrackDetails(String trackShareText) throws FailedToExtractTrackDetailsException {
        for(TrackDetailsExtractor extractor: EXTRACTORS)
        {
            try {
                return extractor.extract(trackShareText);
            } catch (FailedToExtractTrackDetailsException e) {}
        }

        throw new FailedToExtractTrackDetailsException();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
