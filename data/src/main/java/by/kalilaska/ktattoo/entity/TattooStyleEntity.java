package by.kalilaska.ktattoo.entity;

public class TattooStyleEntity {
	private short id;
	private String name;
	private String description;

	public TattooStyleEntity() {		
	}

	public TattooStyleEntity(short id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public short getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public void setId(short id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "TattooStyleEntity [id=" + id + ", name=" + name + ", description=" + description + "]";
	}

}
