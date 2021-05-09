import javax.swing.*;
import java.awt.*;

class Pos{//position
    private int y;
    private int x;

    //Default constructor
    public Pos(){}

    //Constructor that has _x, _y as parameters
    public Pos(int _x, int _y){
        this.y = _y;
        this.x = _x;
    }

    //y setter
    public void setY(int y) {
        if(y < 0){
            System.out.println("location must larger than 0");
            return;
        }
        this.y = y;
    }

    //x setter
    public void setX(int x) {
        if(x < 0){
            System.out.println("location must larger than 0");
            return;
        }
        this.x = x;
    }

    //y getter
    public int getY() {
        return y;
    }

    //x getter
    public int getX() {
        return x;
    }
}

public class DrawFigure extends JFrame {
    private Graphics figure;
    private Pos pos;//figure position

    public DrawFigure(){
        super.setSize(500, 300);//set application window size
        super.setLocation(3000, 0);//set application position shown
        super.setVisible(true);
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);//Terminate program when user close application window
        figure = getGraphics();
        pos = new Pos();
    }

    //pos setter
    public void setPos(Pos pos) {
        this.pos = pos;
    }

    @Override
    public void paint(Graphics p){}

    public void drawRectangle(Pos pos, int _width, int _height){
        figure.drawRect(pos.getX(), pos.getY(), _width, _height);
    }

    public void drawOval(Pos pos, int _width, int _height){
        figure.drawOval(pos.getX(), pos.getY(), _width, _height);
    }
}
