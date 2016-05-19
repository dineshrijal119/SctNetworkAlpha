package np.com.rijaldinesh.www.sctnetworkalpha.register;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.HttpStatus;
import cz.msebera.android.httpclient.StatusLine;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import np.com.rijaldinesh.www.sctnetworkalpha.R;
import np.com.rijaldinesh.www.sctnetworkalpha.Sqlitedatabases.Customer_Data;
import np.com.rijaldinesh.www.sctnetworkalpha.Sqlitedatabases.Registration_flag;
import np.com.rijaldinesh.www.sctnetworkalpha.Sqlitedatabases.SctDatabaseHelper;
import np.com.rijaldinesh.www.sctnetworkalpha.gcm.ApplicationConstant;
import np.com.rijaldinesh.www.sctnetworkalpha.gcm.GCMMainActivity;
import np.com.rijaldinesh.www.sctnetworkalpha.utils.Utility;

/**
 * Created by dinesh on 5/17/16.
 */
public class SCTMain extends AppCompatActivity implements View.OnClickListener {
    EditText email, confirm_email, first_name, last_name, cell_phone;
    Button register;
    private SctDatabaseHelper sctDatabaseHelper;
    ProgressDialog prgDialog;
    ArrayList<Customer_Data> customer_datas_list;
    ArrayList<Registration_flag> registration_flags_list;
    SctDatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prgDialog = new ProgressDialog(this);
        db = new SctDatabaseHelper(this);
        // Set Progress Dialog Text
        prgDialog.setMessage("Please wait...");
        // Set Cancelable as False
        prgDialog.setCancelable(false);
        sctDatabaseHelper = new SctDatabaseHelper(getApplicationContext());
        sctDatabaseHelper.getWritableDatabase();
        hasRegistere();


    }

    public final static boolean isValidateEmail(String target) {
        if (target == null) {
            return false;
        } else {
            return Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }


    }
    public boolean isValidWord(String w) {

        return w.matches("[A-Za-z][^.]*");
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_signup:
//                validate();
                break;
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    public void hasRegistere() {
        int getCusturdata = sctDatabaseHelper.checkHasCustData();
        int getRegistered = sctDatabaseHelper.checkRegData();
        if (getCusturdata ==0){

            if (getRegistered ==0){
            fetchdata();
            }
        }

        Log.d("dinesh", "custumer data:" + getCusturdata);
        Log.d("dinesh", "registered data:" + getRegistered);

    }

    public void validationMain() {
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isValidateEmail(email.getText().toString())) {
                    email.setError("Enter valid email");
                }

            }
        });
        confirm_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isValidateEmail(confirm_email.getText().toString())) {
                    confirm_email.setError("Enter valid Email");
                }

            }
        });
        first_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isValidWord(first_name.getText().toString())){
                    first_name.setError("Character only");
                }

            }
        });
        last_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isValidWord(last_name.getText().toString())){
                    last_name.setError("Character only");
                }

            }
        });
    }
    public void fetchdata() {

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    InstanceID instanceID = InstanceID.getInstance(getApplicationContext());
                    String token = instanceID.getToken(ApplicationConstant.SENDER_ID,
                            GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                    String tokenFinal=token.replaceAll(":","_");
                    String sendUrl=ApplicationConstant.FIRST_GCM_CALL_URL+tokenFinal+"/"+"AND/";
                    Log.d("dinesh","sender url : " + sendUrl);
                    new Requesttask().execute(sendUrl);


                } catch (Exception bug) {
                    bug.printStackTrace();
                }

            }
        });

        thread.start();
    }
    class Requesttask extends AsyncTask<String ,String ,String>{

        @Override
        protected String doInBackground(String... uri) {
            HttpClient httpClient=new DefaultHttpClient();
            HttpResponse httpResponse;
            String responseResult=null;
            try {
                httpResponse=httpClient.execute(new HttpGet(uri[0]));
                StatusLine statusLine=httpResponse.getStatusLine();
                if (statusLine.getStatusCode()== HttpStatus.SC_OK){
                    ByteArrayOutputStream byteArrayOutputStream= new ByteArrayOutputStream();
                    httpResponse.getEntity().writeTo(byteArrayOutputStream);
                    responseResult=byteArrayOutputStream.toString();
//                    byteArrayOutputStream.close();
                    JSONObject jsonObject= new JSONObject(responseResult);
                    JSONObject jsonObject1= jsonObject.getJSONObject("getcustidResult");
                    for (int i=0;i<2;i++){
                        String  cust_id= jsonObject1.getString("cust_id");
                       db.insertCutid(cust_id);
                        int status=jsonObject1.getInt("status");
                        if (status == 00){
                            db.createRegFlag("I");
                        }
                    }
                }else {
                    httpResponse.getEntity().getContent().close();
                    throw new IOException(statusLine.getReasonPhrase());
                }
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("dinesh","response result from call :" + responseResult);
            return responseResult;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            startActivity(new Intent(SCTMain.this,Register.class));


        }
    }
}
