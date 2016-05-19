package np.com.rijaldinesh.www.sctnetworkalpha;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.content.*;
import android.widget.Toast;

import np.com.rijaldinesh.www.sctnetworkalpha.Sqlitedatabases.SctDatabaseHelper;
import np.com.rijaldinesh.www.sctnetworkalpha.gcm.GCMMainActivity;
import np.com.rijaldinesh.www.sctnetworkalpha.gmsvision.MultiTrackerActivity;
import np.com.rijaldinesh.www.sctnetworkalpha.qrcodegenerator.GenerateQRCodeActivity;
import np.com.rijaldinesh.www.sctnetworkalpha.qrcodereader.DecoderActivity;
import np.com.rijaldinesh.www.sctnetworkalpha.register.SCTMain;

import android.provider.Settings.Secure;

public class MainActivity extends AppCompatActivity {

   private  SctDatabaseHelper sctDatabaseHelper;
    private String TAG;
    Context context;
    String authorizedEntity="sctnetwork-1314";
    String scope = "GCM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getDeviceId();
       if (isConnected()){
           Toast.makeText(getApplicationContext(),"Connected",Toast.LENGTH_LONG).show();
       }else {
           Toast.makeText(getApplicationContext(),"Please connect to newtork",Toast.LENGTH_LONG).show();
       }
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.action_bar_content);
        actionBar.setDisplayUseLogoEnabled(true);

        GridView gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(new MyAdapter(this));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if (position == 0) {
                    Intent i = new Intent(MainActivity.this, DecoderActivity.class);
                    MainActivity.this.startActivity(i);
                } else if (position == 1) {
                    Intent i = new Intent(MainActivity.this, GenerateQRCodeActivity.class);
                    MainActivity.this.startActivity(i);
                } else if (position == 2) {
                    Intent i = new Intent(MainActivity.this, MultiTrackerActivity.class);
                    MainActivity.this.startActivity(i);
                }else if (position == 4) {
                    Intent i = new Intent(MainActivity.this, GCMMainActivity.class);
                    MainActivity.this.startActivity(i);
                }
                else if (position == 5) {
                    Intent i = new Intent(MainActivity.this, SCTMain.class);
                    MainActivity.this.startActivity(i);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;

    }



    public boolean isConnected(){
        ConnectivityManager connectivityManager=(ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null || networkInfo.isConnected())

        return true;
        else  return false;
    }
    public void getDeviceId(){
        String android_id=Secure.getString(getApplicationContext().getContentResolver(),Secure.ANDROID_ID);
        Log.d("android_id",android_id);
    }
}