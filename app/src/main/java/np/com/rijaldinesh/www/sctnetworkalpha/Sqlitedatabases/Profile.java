package np.com.rijaldinesh.www.sctnetworkalpha.Sqlitedatabases;

/**
 * Created by dinesh on 5/15/16.
 */
public class Profile {

    String act_flag;
    String card_type;
    String exp_date;
    String last_4;
    String profile_name;

    public Profile() {

    }

    public Profile(String act_flag, String card_type, String exp_date, String last_4, String profile_name) {
        this.act_flag = act_flag;
        this.card_type = card_type;
        this.exp_date = exp_date;
        this.last_4 = last_4;
        this.profile_name = profile_name;
    }

    public String getAct_flag() {
        return this.act_flag = act_flag;
    }

    public String getCard_type() {
        return this.card_type = card_type;
    }

    public String getExp_date() {
        return this.exp_date = exp_date;
    }

    public String getLast_4() {
        return this.last_4 = last_4;
    }

    public String getProfile_name() {
        return this.profile_name = profile_name;
    }

    public void setAct_flag(String act_flag) {
        this.act_flag = act_flag;
    }

    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }

    public void setExp_date(String exp_date) {
        this.exp_date = exp_date;
    }

    public void setLast_4(String last_4) {
        this.last_4 = last_4;
    }

    public void setProfile_name(String profile_name) {
        this.profile_name = profile_name;
    }
}

