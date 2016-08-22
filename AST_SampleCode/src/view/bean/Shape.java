package view.bean;

public abstract class Shape {
	public static final int RECTANGLE = 1;
	public static final int DIAMOND = 2;
	
	// 1: Rectangle shape
	// 2: Diamond shape
	public Shape(){
	}
	
	public abstract int getShapeType();

	public abstract int getX1();
	public abstract int getX2();
	public abstract int getY1();
	public abstract int getY2();
	
	public abstract int getWidth();

	public abstract int getHeight();
	
}
