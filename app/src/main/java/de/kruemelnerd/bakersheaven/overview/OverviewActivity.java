package de.kruemelnerd.bakersheaven.overview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import de.kruemelnerd.bakersheaven.R;
import timber.log.Timber;

public class OverviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        Timber.plant(new Timber.DebugTree());
    }

}
