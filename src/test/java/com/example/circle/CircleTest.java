package com.example.circle;

public class CircleTest {
    public static void main(String[] args) {
        Circle myCircle = new Circle(5, 6);
        System.out.println(myCircle.getX());
        System.out.println(myCircle.getY());
        moveCircle(myCircle,3,4);
        System.out.println(myCircle.getX());
        System.out.println(myCircle.getY());



    }
    public static void moveCircle(Circle circle, int deltaX, int deltaY){
        circle.setX(circle.getX() + deltaX);
        circle.setY(circle.getY() + deltaY);
        circle = new Circle();
    }
}
