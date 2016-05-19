package np.com.rijaldinesh.www.sctnetworkalpha.Sqlitedatabases;

/**
 * Created by dinesh on 5/15/16.
 */
public class Friends_List {
    String cell_no;
    String cust_id;
    String name;

    public Friends_List() {

    }

    public Friends_List(String cell_no, String cust_id, String name) {
        this.cell_no = cell_no;
        this.cust_id = cust_id;
        this.name = name;
    }

    public String getCell_no() {
        return this.cell_no = cell_no;

    }
    public void setCell_no(String cell_no){
        this.cell_no=cell_no;
    }
    public String getCust_id(){
        return  this.cust_id=cust_id;
    }
    public void setCust_id(String cust_id){
        this.cust_id=cust_id;
    }
    public String getName(){
        return this.name=name;
    }
    public void setName(String name){
        this.name=name;
    }

}
