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

public class Plugin implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
    private String name;
    private String configurationfile;
    private String type;
    private String note;

    public Plugin (int id, String name, String configurationfile, String type, String note) {
        this.id = id;
        this.name = name;
        this.configurationfile = configurationfile;
        this.type = type;
        this.note = note;
    }

    public int get_id() {
        return this.id;
    }

    public String get_name() {
        return this.name;
    }


    public String get_configurationfile() {
        return this.configurationfile;
    }

    public String get_type() {
        return this.type;
    }

    public String get_note() {
        return this.note;
    }

}
