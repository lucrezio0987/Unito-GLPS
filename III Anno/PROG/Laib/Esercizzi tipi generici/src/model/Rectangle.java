package model;
public class Rectangle extends Parallelogram {

    public int getPerimeter() {
        return (this.altezza + this.base) * 2;
    }

}
