package util;

import java.io.Serializable;

/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of group
 *
 * @author amuliello
 */
public class Grouping implements Serializable {
    private int  id;
    private String  name;
    private String  alias;

    public Grouping (	int id, 
    					String name, 
    					String alias) {
        this.id = id;
        this.name = name;
        this.alias = alias;
    }

    public int get_id() {
        return this.id;
    }

    public String get_name() {
        return this.name;
    }

    public String get_alias() {
        return this.alias;
    }

}
