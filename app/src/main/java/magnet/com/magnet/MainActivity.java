package magnet.com.magnet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int SYSTEM_ALERT_WINDOW_PERMISSION = 2084;
    private BroadcastReceiver broadcastReceiver = null;
    private static final String TAG = "FloatWidget";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            askPermission();
        }
    }

    @Override
    protected void onPause() {

        super.onPause();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("FloatWidget");
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (intent != null) {
                    Log.d(TAG, "MainActivity onCreate onReceive");
                    int datapassed = intent.getIntExtra("DATAPASSED", 0);
                    Toast.makeText(MainActivity.this,
                            "Triggered by Service!\n"
                                    + "Data passed: " + String.valueOf(datapassed),
                            Toast.LENGTH_LONG).show();
                }
            }
        };

        start(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    private void askPermission() {

        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, SYSTEM_ALERT_WINDOW_PERMISSION);
    }

    public void start(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M || Settings.canDrawOverlays(this)) {
            MainActivity.this.registerReceiver(broadcastReceiver, intentFilter);
            startService(new Intent(MainActivity.this, FloatIconService.class));
        } else {
            askPermission();
            Toast.makeText(this, "You need System Alert Window Permission to do this", Toast.LENGTH_SHORT).show();
        }
    }
}