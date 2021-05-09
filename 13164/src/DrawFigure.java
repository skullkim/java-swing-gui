import javax.swing.*;
import java.awt.*;
import java.rmi.activation.ActivationGroup_Stub;
import java.util.ArrayList;

//position
class Pos{
    private int y;
    private int x;

    //default constructor
    public Pos(){}

    //constructor that has _x, _y as parameters
    public Pos(int _x, int _y){
        this.x = _x;
        this.y = _y;
    }

    //getter
    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    //setter
    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    //toString
    @Override
    public String toString() {
        return "Pos{y=" + y + ", x=" + x + '}';
    }
}

class Rectangle{
    private Pos pos;//position
    private int height;
    private int width;

    //default constructor
    public Rectangle(){}

    //constructor that has array as parameter
    public Rectangle(int[] _rect_info){
        this.pos = new Pos(_rect_info[0], _rect_info[1]);
        this.height = _rect_info[2];
        this.width = _rect_info[3];
    }

    //getter
    public int getPosX() {
        return this.pos.getX();
    }

    public int getPosY(){
        return this.pos.getY();
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    //setter
    public void setPosY(int _y) {
        this.pos.setY(_y);
    }

    public void setPosX(int _x) {
        this.pos.setX(_x);
    }

    public void setHeight(int _height) {
        if(height < 0){
            System.out.println("height must greater than 0");
            return;
        }
        this.height = _height;
    }

    public void setWidth(int _width) {
        if(_width < 0){
            System.out.println("width must greater than 0");
            return;
        }
        this.width = _width;
    }


    //toString
    @Override
    public String toString() {
        return "Rectangle{pos=" + pos + ", height=" + height + ", width=" + width + '}';
    }
}

public class DrawFigure extends JFrame {
    private ArrayList<Rectangle> rects;//rectangle information list

    //default constructor, initialize swing window
    public DrawFigure(){
        rects = new ArrayList<Rectangle>();
        super.setSize(500, 300);
        super.setLocation(3000, 0);
        super.setVisible(true);
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    //setter
    public void setRects(ArrayList<Rectangle> rects) {
        this.rects = rects;
    }

    //add rectangle information
    public void addRectangle(Rectangle _rect){
        rects.add(_rect);
    }

    //draw rectangle
    @Override
    public void paint(Graphics _graphics){
        if(this.rects.size() > 0){
            for(Rectangle rect_it : rects){
                _graphics.drawRect(rect_it.getPosX(), rect_it.getPosY(), rect_it.getWidth(), rect_it.getHeight());
            }
        }
    }

}
