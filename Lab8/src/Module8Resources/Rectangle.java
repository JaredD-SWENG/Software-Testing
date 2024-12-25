package Module8Resources;

/**
 * Lab7
 *
 * @author Haris Siddiqui, Jared Daniel, Yifan Liu
 */

public class Rectangle {
    int w, h;

    public Rectangle() {
    }

    public Rectangle(int w, int h) {
        this.w = w;
        this.h = h;
    }
    public Rectangle(int f, Rectangle r) {
        this.w = f * r.w;
        this.h = f * r.h;
    }

    public int getArea() {
        return w * h;
    }
}