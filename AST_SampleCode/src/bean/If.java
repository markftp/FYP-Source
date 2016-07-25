package bean;

public class If extends Statement{
	
	Statement statement;
	private String header = "If";
	private String description = "If to specify a block of code to be executed, if a specified condition is true";
	
	private double locationX;
	private double locationY;
	
	public If(){
		statement = new Statement();
	}
	
	public String toJavaCode(){
		return "";
	}
	
	public String toString(){
		return "";		
	}

	public double getLocationX() {
		return locationX;
	}

	public void setLocationX(double locationX) {
		this.locationX = locationX;
	}

	public double getLocationY() {
		return locationY;
	}

	public void setLocationY(double locationY) {
		this.locationY = locationY;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

}
