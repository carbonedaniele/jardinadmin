package util;

import java.io.Serializable;


/**
 * Description of resultset
 *
 * @author mavellino
 */

public class Notify implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id_resultset;
    private String notify_name;
    private String address_statement;
    private String data_statement;
    private String xslt;
    private int id_notify;
    private String bmdid;

    public Notify (	int id_resultset, 
    				String notify_name, 
    				String address_statement, 
    				String data_statement, 
    				String xslt, 
    				int id_notify, 
    				String bmdid) {
        this.id_resultset = id_resultset;
        this.notify_name = notify_name;
        this.address_statement = address_statement;
        this.data_statement = data_statement;
        this.xslt = xslt;
        this.id_notify = id_notify;
        this.bmdid = bmdid;
    }

    public int  get_id_resultset(){
    	return this.id_resultset;
    }
    public String  get_notify_name(){
    	return this.notify_name;
    }
    public String  get_address_statement(){
    	return this.address_statement;
    }
    public String  get_data_statement(){
    	return this.data_statement;
    }
    public String  get_xslt(){
    	return this.xslt;
    }
    public int  get_id_notify(){
    	return this.id_notify;
    }
    public String  get_bmdid(){
    	return this.bmdid;
    }
}


