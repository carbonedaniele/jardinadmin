package util;
import util.ResourceMinimal;


public class ResourceField extends ResourceMinimal {
	private static final long serialVersionUID = 1L;
	String javaSqlType;
	
	public ResourceField(int id, String name, String alias, String javaSqlType) {
		super(id, name, alias);
		this.javaSqlType = javaSqlType;
	}

	public String getJavaSqlType (){
		return javaSqlType;
	}
}
