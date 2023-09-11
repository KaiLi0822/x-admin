package com.example.circle;

public class Circle {
    public Circle(int x, int y) {
        setX(x);
        setY(y);
    }

    public Circle() {

    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    private int x;
    private int y;

    public void moveCircle(Circle circle, int deltaX, int deltaY){
        circle.setX(circle.getX() + deltaX);
        circle.setY(circle.getY() + deltaY);
        circle = new Circle();
    }
}
