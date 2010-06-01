package util ;

import java.io.Serializable;

/**
 * Description of resource
 *
 * @author gpantanetti
 */

public class Resource extends ResourceMinimal {
    private String type;
    private String def;
    private int header;
    private int search;
    private int grouping;

	
public Resource(int id, 
    				String name, 
    				String alias, 
    				String type, 
    				String def, 
    				int header, 
    				int search, 
    				int grouping) {
	super (id, name, alias);
        this.type = type;
        this.def = def;
        this.header = header;
        this.search = search;
        this.grouping = grouping;
    }


    public String get_type() {
        return this.type;
    }

    public String get_def() {
        return this.def;
    }

    public int get_header() {
        return this.header;
    }

    public int get_search() {
        return this.search;
    }

    public int get_grouping() {
        return this.grouping;
    }
}