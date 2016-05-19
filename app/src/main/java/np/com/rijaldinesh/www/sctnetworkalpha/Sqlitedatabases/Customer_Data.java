package np.com.rijaldinesh.www.sctnetworkalpha.Sqlitedatabases;

/**
 * Created by dinesh on 5/15/16.
 */
public class Customer_Data {


    String app_lock;
    String curr_code;
    String curr_type;
    String cust_id;
    String first_name;
    String last_name;
    String l_pin;
    String pin_flag;
    String pin_retries_count;
    String reset_pin;

    public Customer_Data() {

    }

    public Customer_Data(String app_lock, String curr_code, String curr_type, String cust_id, String first_name, String last_name, String l_pin, String pin_flag, String pin_retries_count, String reset_pin) {
        this.app_lock = app_lock;
        this.curr_code = curr_code;
        this.curr_type = curr_type;
        this.cust_id = cust_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.l_pin = l_pin;
        this.pin_flag = pin_flag;
        this.pin_retries_count = pin_retries_count;
        this.reset_pin = reset_pin;
    }

    public String getApp_lock() {
        return this.app_lock;
    }

    public String getCurr_code() {
        return this.curr_code;
    }

    public String getCurr_type() {
        return this.curr_type;
    }

    public String getCust_id() {
        return this.cust_id;
    }

    public String getFirst_name() {
        return this.first_name;
    }

    public String getLast_name() {
        return this.last_name;
    }

    public String getL_pin() {
        return this.l_pin;
    }

    public String getPin_flag() {
        return this.pin_flag;
    }

    public String getPin_retries_count() {
        return this.pin_retries_count;
    }

    public String getReset_pin() {
        return this.reset_pin;
    }

    public void setApp_lock(String app_lock) {
        this.app_lock = app_lock;
    }

    public void setCurr_code(String curr_code) {
        this.curr_code = curr_code;
    }

    public void setCurr_type(String curr_type) {
        this.curr_type = curr_type;
    }

    public void setCust_id(String cust_id) {
        this.cust_id = cust_id;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setL_pin(String l_pin) {
        this.l_pin = l_pin;
    }

    public void setPin_flag(String pin_flag) {
        this.pin_flag = pin_flag;
    }

    public void setPin_retries_count(String pin_retries_count) {
        this.pin_retries_count = pin_retries_count;
    }

    public void setReset_pin(String reset_pin) {
        this.reset_pin = reset_pin;
    }
}
