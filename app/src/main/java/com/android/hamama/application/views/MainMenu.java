package com.android.hamama.application.views;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.hamama.application.ApplicationSettings;
import com.android.hamama.application.R;
import com.android.hamama.application.communication.CommService;

public class MainMenu extends SignedInBasedActivity {
    TextView welcome_tv;
    Button signout_btn;

    @Override
    protected void onBroadcastReceived(Intent intent) {
        switch(intent.getAction()){
            case CommService.SIGNOUT_RESPONSE:
                Intent intent1 = new Intent(MainMenu.this, LoginActivity.class);
                startActivity(intent1);
                break;

            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        signout_btn = findViewById(R.id.signout);
        welcome_tv = findViewById(R.id.welcome_txt);

        String name = getIntent().getStringExtra("name");
        welcome_tv.setText(  "ברוכים הבאים, " + name);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter(CommService.SIGNOUT_RESPONSE);
        registerReceiver(drr, intentFilter);
    }

    public void signOut(View view) {
        Bundle bundle = new Bundle();
        bundle.putInt("recipient", CommService.SIGNOUT_RECIPIENT);
        Intent intent = new Intent(MainMenu.this, CommService.class);
        intent.putExtras(bundle);
        startForegroundService(intent);
    }

    public void ApplicationSettingsActivity(View view) {
        Intent intent = new Intent(MainMenu.this, ApplicationSettings.class);
        startActivity(intent);
    }

    public void LogActivity(View view) {
        Intent intent = new Intent(MainMenu.this, Log.class);
        startActivity(intent);
    }

    public void SettingsActivity(View view) {
        Intent intent = new Intent(MainMenu.this, BoardSettings.class);
        startActivity(intent);
    }


    public void MeasuresActivity(View view) {
        Intent intent = new Intent(MainMenu.this, Measures.class);
        startActivity(intent);
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}