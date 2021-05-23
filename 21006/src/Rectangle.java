/*
 * Classname: Rectangle
 * Version: 1.0
 * Date: 2021-05-09
 * Copyright: MIT
 */
import java.awt.*;

/*
 * 사각형을 그리는 메서드
 * Figure를 상속한다
 */
public class Rectangle extends Figure{
    //constructor
    public Rectangle(){super();}

    public Rectangle(Pos _pos, int _height, int _width){
        super(_pos, _height, _width);
    }

    //사각형을 그리는 메서드
    @Override
    public void paintComponent(Graphics _graphics){
        _graphics.drawRect(super.getPosX(), super.getPosY(), super.width, super.height);
    }
    //toString
    @Override
    public String toString() { return "Rectangle{pos=" + pos.getX() + ", " + pos.getY() + ", height=" + height + ", width=" + width + '}'; }
}
