package by.kalilaska.ktattoo.entity;

public class RoleEntity {
	private byte id;
	private String name;	

	public RoleEntity() {
		
	}

	public RoleEntity(byte id, String name) {
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
		return "RoleEntity [id=" + id + ", name=" + "]";
	}

}
