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
public class User implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
    private String username;
    private String password;
	private String name;
    private String surname;
    private String email;
    private String office;
    private String telephone;
    private int status;
    private int id_group;
    private String group_name;

    public User (	int id, 
    				String username, 
    				String password, 
    				String name, 
    				String surname, 
    				String email, 
    				String office, 
    				String telephone, 
    				int status, 
    				int id_group,
    				String group_name) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.office = office;
        this.telephone = telephone;
        this.status = status;
        this.id_group = id_group;
        this.group_name = group_name;
    }

    public int   get_id() {
        return this.id;
    }

    public String  get_username() {
        return this.username;
    }

    public String  get_name() {
        return this.name;
    }

    public String getPassword() {
		return password;
	}

    public String  get_surname() {
        return this.surname;
    }

    public String  get_email() {
        return this.email;
    }

    public String  get_telephone() {
        return this.telephone;
    }

    public String  get_office() {
        return this.office;
    }

    public int   get_status() {
        return this.status;
    }

    public int   get_id_group() {
        return this.id_group;
    }

    public String  get_group_name() {
        return this.group_name;
    }

}

