package by.kalilaska.ktattoo.bean;

public class RoleBean {
	private byte id;
	private String name;	

	public RoleBean() {
		
	}
	
	public RoleBean(byte id, String name) {
		super();
		this.id = id;
		this.name = name;		
	}

	public byte getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setId(byte id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "RoleBean [" + name + "]";
	}	

}
