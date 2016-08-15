package view;

import java.awt.Color;
import java.awt.Graphics2D;

public class Rectangle {

// Initialize variables
private int x1; // x coordinate of first endpoint
private int y1; // y coordinate of first endpoint
private int x2; // x coordinate of second endpoint
private int y2; // y coordinate of second endpoint
private Color colour; // colour of the shape

// A no-parameter constructor that sets all the coordinates of the shape to
// 0 and the
// colour to Color.BLACK
public Rectangle() {
    x1 = 0;
    y1 = 0;
    x2 = 0;
    y2 = 0;
    colour = Color.BLACK;
}

// A constructor that initializes the coordinates and colour to the values
// of the
// parameters supplied.
public Rectangle(int x1, int y1, int x2, int y2, Color col) {
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;
    this.colour = col;
}

public void setX1(int x1) {
    this.x1 = x1;
}

public void setY1(int y1) {
    this.y1 = y1;
}

public void setX2(int x2) {
    this.x2 = x2;
}

public void setY2(int y2) {
    this.y2 = y2;
}

public void setColor(Color colour) {
    this.colour = colour;
}

public int getX1() {
    return this.x1;
}

public int getY1() {
    return this.y1;
}

public int getX2() {
    return this.x2;
}

public int getY2() {
    return this.y2;
}

public Color getColor() {
    return this.colour;
}

public int getWidth() {
    return (Math.abs(x2 - x1));
}

public int getHeight() {
    return (Math.abs(y2 - y1));
}

public int getUpperLeftX() {
    return (Math.min(x1, x2));
}

public int getUpperLeftY() {
    return (Math.min(y1, y2));
}
}