package util;

import java.io.Serializable;

/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of group
 *
 * @author gpantanetti
 */
public class Group implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
    private String name;
    private int status;

    public Group( int id, 
    			  String name, 
    			  int status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public int get_id() {
        return this.id;
    }

    public String get_name() {
        return this.name;
    }

    public int get_status() {
        return this.status;
    }

}
