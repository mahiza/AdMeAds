package uuid;

public class Uuid {
	private int id;
	private String appId;
	
	public Uuid() {}

	public Uuid(int id) {
		this.id=id;
	}
	
	public Uuid(int id, String appId) {
		this.id = id;
		this.appId = appId;
	}
	
	public int getId() {
		return id;
	}

	public String getUuId() {
		return appId;
	}
}