package np.com.rijaldinesh.www.sctnetworkalpha.Sqlitedatabases;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.*;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dinesh on 5/15/16.
 */
public class SctDatabaseHelper extends SQLiteOpenHelper {

    private static final String LOG = "sctDatabaseHelper";
    private static final String DATABASE_NAME = "sctnetwork";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_CUSTIMER_DATA = "customer_data";
    private static final String TABLE_FRIEND_LIST = "friend_list";
    private static final String TABLE_PROFILE = "profile";
    private static final String TABLE_REGISTRATION_FLAG = "registration_flag";

    //element of table custumer data
    private static final String KEY_ID = "id";
    private static final String KEY_APP_LOCK = "app_lock";
    private static final String KEY_CURR_CODE = "curr_code";
    private static final String KEY_CURR_TYPE = "curr_type";
    private static final String KEY_CUST_ID_CUSTUMER = "cust_id";
    private static final String KEY_FIRST_NAME = "first_name";
    private static final String KEY_LAST_NAME = "last_name";
    private static final String KEY_L_PIN = "l_pin";
    private static final String KEY_PIN_FLAG = "pin_flag";
    private static final String KEY_PIN_RETRIES_COUNT = "pin_retries_count";
    private static final String KEY_RESET_PIN = "reset_pin";

    //element of table friend list

    private static final String KEY_CELL_NO = "cell_no";
    private static final String KEY_CUST_ID_FRIEND = "cust_id";
    private static final String KEY_NAME = "name";

    //element of table profile
    private static final String KEY_ACT_FLAG = "act_flag";
    private static final String KEY_CARD_TYPE = "card_type";
    private static final String KEY_EXP_DATE = "exp_date";
    private static final String KEY_LAST_FOUR = "last_4";
    private static final String KEY_PROFILE_NAME = "profile_name";

    //element of table registration flag
    private static final String KEY_REG_FLAG = "reg_flag";

    //crete table custumer data
    private static final String CREATE_TABLE_CUSTUMER_DATA = "create table " + TABLE_CUSTIMER_DATA + "(" + KEY_ID + " integer primary key, " + KEY_CUST_ID_CUSTUMER + " text ," + KEY_APP_LOCK + " text, " + KEY_CURR_CODE + " text, " + KEY_CURR_TYPE + " text, " + KEY_FIRST_NAME + " text, " + KEY_LAST_NAME + " text ," + KEY_L_PIN + " text ," + KEY_PIN_FLAG + " text, " + KEY_PIN_RETRIES_COUNT + " text, " + KEY_RESET_PIN + " text " + ")";
    //create table friend list
    private static final String CREATE_TABLE_FRIEND_LIST = "create table " + TABLE_FRIEND_LIST + "(" + KEY_CELL_NO + " text, " + KEY_CUST_ID_FRIEND + " text, " + KEY_NAME + " text " + ")";
    // create table profile
    private static final String CREATE_TABLE_PROFILE = " create table " + TABLE_PROFILE + "(" + KEY_ACT_FLAG + " text, " + KEY_CARD_TYPE + " text ," + KEY_EXP_DATE + " text, " + KEY_LAST_FOUR + " text, " + KEY_PROFILE_NAME + " text " + ")";

    // create table of registration flag
    private static final String CREATE_TABLE_FLAG = " create table " + TABLE_REGISTRATION_FLAG + "(" + KEY_REG_FLAG + " text " + ")";


    public SctDatabaseHelper(Context context) {
        super(context, DATABASE_NAME/*"/mnt/sdcard/sctnetwork.db"*/, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CUSTUMER_DATA);
        db.execSQL(CREATE_TABLE_FLAG);
        db.execSQL(CREATE_TABLE_FRIEND_LIST);
        db.execSQL(CREATE_TABLE_PROFILE);
        Log.d("custumer_data", CREATE_TABLE_CUSTUMER_DATA);
        Log.d("flag", CREATE_TABLE_FLAG);
        Log.d("friend_list", CREATE_TABLE_FRIEND_LIST);
        Log.d("profile", CREATE_TABLE_PROFILE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTIMER_DATA);
        db.execSQL("DROP IF EXISTS " + TABLE_FRIEND_LIST);
        db.execSQL("DROP IF EXISTS " + TABLE_REGISTRATION_FLAG);
        db.execSQL("DROP IF EXISTS " + TABLE_PROFILE);

    }

    public long createCustumer(Customer_Data customer_data) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_APP_LOCK, customer_data.getApp_lock());
        values.put(KEY_CURR_CODE, customer_data.getCurr_code());
        values.put(KEY_CURR_TYPE, customer_data.getCurr_type());
        values.put(KEY_FIRST_NAME, customer_data.getFirst_name());
        values.put(KEY_LAST_NAME, customer_data.getLast_name());
        values.put(KEY_L_PIN, customer_data.getL_pin());
        values.put(KEY_PIN_FLAG, customer_data.getPin_flag());
        values.put(KEY_PIN_RETRIES_COUNT, customer_data.getPin_retries_count());
        values.put(KEY_RESET_PIN, customer_data.getReset_pin());

        long custumer = sqLiteDatabase.insert(TABLE_CUSTIMER_DATA, null, values);
        return custumer;
    }

    public long insertCutid(String str) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CUST_ID_CUSTUMER, str);
        return sqLiteDatabase.insert(TABLE_CUSTIMER_DATA, null, values);
    }

    public long createProfile(Profile profile) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ACT_FLAG, profile.getAct_flag());
        values.put(KEY_CARD_TYPE, profile.getCard_type());
        values.put(KEY_EXP_DATE, profile.getExp_date());
        values.put(KEY_LAST_FOUR, profile.getLast_4());
        values.put(KEY_PROFILE_NAME, profile.getProfile_name());
        long profileinsert = sqLiteDatabase.insert(TABLE_PROFILE, null, values);
        return profileinsert;
    }

    public long createFriendlist(Friends_List friends_list) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CELL_NO, friends_list.getCell_no());
        values.put(KEY_CUST_ID_CUSTUMER, friends_list.getCust_id());
        values.put(KEY_NAME, friends_list.getName());
        long friendlistinsert = sqLiteDatabase.insert(TABLE_FRIEND_LIST, null, values);
        return friendlistinsert;
    }

    public long createRegFlag(String str) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_REG_FLAG, str);
        long regflaginsert = sqLiteDatabase.insert(TABLE_REGISTRATION_FLAG, null, values);
        return regflaginsert;
    }

    public int checkHasCustData() {
        boolean data;
        String selectquery = "select * from " + TABLE_CUSTIMER_DATA;
        Log.d("custumer", selectquery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectquery, null);
        int custmr_count = cursor.getCount();
        cursor.close();
        db.close();
        return custmr_count;
    }

    public int checkRegData() {
        boolean flag;
        String selectquery = " select * from " + TABLE_REGISTRATION_FLAG;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectquery, null);
        int reg_count = cursor.getCount();
        cursor.close();
        sqLiteDatabase.close();
        return reg_count;

    }
}
