package np.com.rijaldinesh.www.sctnetworkalpha.qrcodegenerator;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import np.com.rijaldinesh.www.sctnetworkalpha.MainActivity;
import np.com.rijaldinesh.www.sctnetworkalpha.R;

public class GenerateQRCodeActivity extends AppCompatActivity implements OnClickListener {

    private String LOG_TAG = "GenerateQRCode";
    Button button1,button2;
    Bitmap bitmap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qr_generation_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.show();
//        actionBar.setTitle("QR Code Generator");
//        actionBar.setTitle(Html.fromHtml("<font color=\"black\">" + getString(R.string.generator_action_bar) + "</font>"));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.scan_barcode);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.action_bar_generator_content);
         button1 = (Button) findViewById(R.id.button1);
        button2=(Button) findViewById(R.id.button2);
        button1.setOnClickListener(this);
        View v =getSupportActionBar().getCustomView();
        /*** sample click is a id of the view i have used in action bar view ***/
        ((ImageView)v.findViewById(R.id.generateback)).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });
    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button1:
                EditText qrInput = (EditText) findViewById(R.id.qrInput);
                String qrInputText = qrInput.getText().toString();
                if (qrInputText != null) {
                    Log.v(LOG_TAG, qrInputText);

                    //Find screen size
                    WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
                    Display display = manager.getDefaultDisplay();
                    Point point = new Point();
                    display.getSize(point);
                    int width = point.x;
                    int height = point.y;
                    int smallerDimension = width < height ? width : height;
                    smallerDimension = smallerDimension * 3 / 4;

                    //Encode with a QR Code image
                    QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(qrInputText,
                            null,
                            Contents.Type.TEXT,
                            BarcodeFormat.QR_CODE.toString(),
                            smallerDimension);
                    try {
                         bitmap = qrCodeEncoder.encodeAsBitmap();
                        ImageView myImage = (ImageView) findViewById(R.id.imageView1);
                        myImage.setImageBitmap(bitmap);
                        SaveImage(bitmap);
                       /* button2.setText("Save To SD card");
                        button1.setVisibility(View.GONE);
                        button2.setVisibility(View.VISIBLE);
*/

                    } catch (WriterException e) {
                        e.printStackTrace();
                    }

                } else if (qrInputText.equals("")) {
                    Snackbar snackbar = Snackbar.make(v, "Please enter input.. ", Snackbar.LENGTH_LONG);
                    View snackBarView = snackbar.getView();
                    snackBarView.setBackgroundColor(getResources().getColor(R.color.background));
                    snackbar.setActionTextColor(getResources().getColor(R.color.black));
                    snackbar.show();
                }


                break;

            case R.id.button2:


        }
    }

    private void SaveImage(Bitmap finalBitmap) {

//        String root = Environment.getExternalStorageDirectory().toString();
        String root = Environment.getExternalStorageDirectory() + "/SCT QR Code";
//        File folder = new File(Environment.getExternalStorageDirectory()+"/SCT QR Code");
        File myDir = new File(root);
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-" + n + ".jpg";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onBackPressed();
        return super.onOptionsItemSelected(item);
    }*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(GenerateQRCodeActivity.this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();

    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }
}