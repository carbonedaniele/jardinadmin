package util;


public class ResourceWithGroupPermissions extends Resource {
	private ResourceGroupPermissions resourceGroupPermissions;
	
//	public ResourceGroupPermissions(int id, String name, String alias) {
//		super(id, name, alias);
//		// TODO Auto-generated constructor stub
//	}

	public ResourceGroupPermissions getResourceGroupPermissions() {
		return resourceGroupPermissions;
	}

	public void setResourceGroupPermissions(
			ResourceGroupPermissions resourceGroupPermissions) {
		this.resourceGroupPermissions = resourceGroupPermissions;
	}

	public ResourceWithGroupPermissions(int id, String name, String alias,
			String type, String def, int header, int search , int grouping,
			ResourceGroupPermissions resourceGroupPermissions) {
		super(id, name, alias, type, def, header,search, grouping);
		this.resourceGroupPermissions = resourceGroupPermissions;
	}

	private static final long serialVersionUID = 1L;

	
	
}