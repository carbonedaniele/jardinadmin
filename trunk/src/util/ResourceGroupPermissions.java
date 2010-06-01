package util;

public class ResourceGroupPermissions {
	public ResourceGroupPermissions(int read, int delete, int modify,
			int insert, int groupId) {
		super();
		this.read = read;
		this.delete = delete;
		this.modify = modify;
		this.insert = insert;
		this.groupId = groupId;
	}
	private int read;
	private int delete;
	private int modify;
	private int insert;	
	private int groupId;	
	public int getgroupId() {
		return groupId;
	}
	public void setgroupId(int groupId) {
		this.groupId = groupId;
	}
	public int getRead() {
		return read;
	}
	public void setRead(int read) {
		this.read = read;
	}
	public int getDelete() {
		return delete;
	}
	public void setDelete(int delete) {
		this.delete = delete;
	}
	public int getModify() {
		return modify;
	}
	public void setModify(int modify) {
		this.modify = modify;
	}
	public int getInsert() {
		return insert;
	}
	public void setInsert(int insert) {
		this.insert = insert;
	}

}
