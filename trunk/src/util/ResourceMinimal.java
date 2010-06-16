package util;

import java.io.Serializable;

public class ResourceMinimal implements Serializable{
     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public ResourceMinimal (int id, String name, String alias) {
		super();
		this.id = id;
		this.name = name;
		this.alias = alias;
	}

	private int id;
	private String name;
	private String alias;

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
