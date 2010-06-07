package util;

import java.io.Serializable;

/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

public class Toolbar implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id_resultset;
    private int id_group;
    private String tools;


    public Toolbar (	int id_resultset, 
    					int id_group , 
    					String tools) {
        this.id_resultset = id_resultset;
        this.id_group = id_group;
        this.tools = tools;
    }

    public int get_id_group() {
        return this.id_group;
    }

    public int get_id_resultset() {
        return this.id_resultset;
    }

    public String get_tools() {
        return this.tools;
    }
}

