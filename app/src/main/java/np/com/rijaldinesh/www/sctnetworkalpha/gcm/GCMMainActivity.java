package np.com.rijaldinesh.www.sctnetworkalpha.gcm;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.IOException;

import cz.msebera.android.httpclient.Header;
import np.com.rijaldinesh.www.sctnetworkalpha.R;

public class GCMMainActivity extends Activity {
    ProgressDialog prgDialog;
    RequestParams params = new RequestParams();
    GoogleCloudMessaging gcmObj;
    Context applicationContext;
    String regId = "";

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 80;

    AsyncTask<Void, Void, String> createRegIdTask;

    public static final String REG_ID = "regId";
    public static final String EMAIL_ID = "eMailId";
    EditText emailET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_gcm);
        applicationContext = getApplicationContext();
        emailET = (EditText) findViewById(R.id.email);

        prgDialog = new ProgressDialog(this);
        // Set Progress Dialog Text
        prgDialog.setMessage("Please wait...");
        // Set Cancelable as False
        prgDialog.setCancelable(false);

        GetGCM();

        SharedPreferences prefs = getSharedPreferences("UserDetails",
                Context.MODE_PRIVATE);
        String registrationId = prefs.getString(REG_ID, "");
        if (!TextUtils.isEmpty(registrationId)) {
            Intent i = new Intent(applicationContext, HomeActivity.class);
            i.putExtra("regId", registrationId);
            startActivity(i);
            finish();
        }
    }

    public void RegisterUser(View view) {
        String emailID = emailET.getText().toString();

        if (!TextUtils.isEmpty(emailID) && Utility.validate(emailID)) {
            if (checkPlayServices()) {
                registerInBackground(emailID);
            }
        }
        // When Email is invalid
        else {
            Toast.makeText(applicationContext, "Please enter valid email",
                    Toast.LENGTH_LONG).show();
        }

    }

    private void registerInBackground(final String emailID) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcmObj == null) {
                        gcmObj = GoogleCloudMessaging
                                .getInstance(applicationContext);
                    }
                    regId = gcmObj
                            .register(ApplicationConstant.SENDER_ID);
                    msg = "Registration ID :" + regId;

                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                if (!TextUtils.isEmpty(regId)) {
                    storeRegIdinSharedPref(applicationContext, regId, emailID);
                    Toast.makeText(
                            applicationContext,
                            "Registered with GCM Server successfully.\n\n"
                                    + msg, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(
                            applicationContext,
                            "Reg ID Creation Failed.\n\nEither you haven't enabled Internet or GCM server is busy right now. Make sure you enabled Internet and try registering again after some time."
                                    + msg, Toast.LENGTH_LONG).show();
                }
            }
        }.execute(null, null, null);
    }

    private void storeRegIdinSharedPref(Context context, String regId,
                                        String emailID) {
        SharedPreferences prefs = getSharedPreferences("UserDetails",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Log.d("rjl", "regId :" + regId);
        editor.putString(REG_ID, regId);
        editor.putString(EMAIL_ID, emailID);
        editor.commit();
        storeRegIdinServer(regId);

    }

    private void storeRegIdinServer(final String regId) {
        prgDialog.show();
        params.put("regId", regId);
        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(ApplicationConstant.APP_SERVER_URL, params,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                        prgDialog.hide();
                        if (prgDialog != null) {
                            prgDialog.dismiss();
                        }
                        Toast.makeText(applicationContext,
                                "Reg Id shared successfully with Web App ",
                                Toast.LENGTH_LONG).show();
                        Intent i = new Intent(applicationContext,
                                HomeActivity.class);
                        i.putExtra("regId", regId);
                        startActivity(i);
                        finish();

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        prgDialog.hide();
                        if (prgDialog != null) {
                            prgDialog.dismiss();
                        }
                        // When Http response code is '404'
                        if (statusCode == 404) {
                            Toast.makeText(applicationContext,
                                    "Requested resource not found",
                                    Toast.LENGTH_LONG).show();
                        }
                        // When Http response code is '500'
                        else if (statusCode == 500) {
                            Toast.makeText(applicationContext,
                                    "Something went wrong at server end",
                                    Toast.LENGTH_LONG).show();
                        }
                        // When Http response code other than 404, 500
                        else {
                            Toast.makeText(
                                    applicationContext,
                                    "Unexpected Error occcured! [Most common Error: Device might "
                                            + "not be connected to Internet or remote server is not up and running], check for other errors as well",
                                    Toast.LENGTH_LONG).show();
                        }

                    }



                });

    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(
                        applicationContext,
                        "This device doesn't support Play services, App will not work normally",
                        Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        } else {
            Toast.makeText(
                    applicationContext,
                    "This device supports Play services, App will work normally",
                    Toast.LENGTH_LONG).show();
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPlayServices();
    }
    public void GetGCM() {

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    InstanceID instanceID = InstanceID.getInstance(getApplicationContext());
                    String token = instanceID.getToken(ApplicationConstant.SENDER_ID,
                            GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                    Log.d("token", token);
                } catch (Exception bug) {
                    bug.printStackTrace();
                }

            }
        });

        thread.start();
    }
}