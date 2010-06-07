package util;


/**
 * Description of resultset
 *
 * @author gpantanetti
 */
public class Resultset extends ResourceMinimal {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String statement;

    public Resultset (int id, String name, String alias, String statement) {
        super (id, name, alias);
        this.statement = statement;
    }

    public String get_statement() {
        return this.statement;
    }

}
