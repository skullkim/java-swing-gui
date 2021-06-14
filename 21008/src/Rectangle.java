/*
 * Classname: Rectangle
 * Version: 2.0
 * Date: 2021-06-14
 * Copyright: MIT
 */
import java.awt.*;
import java.awt.geom.Rectangle2D;

/*
 * 사각형을 그리는 메서드
 * Figure를 상속한다
 */
public class Rectangle extends Figure{
    //constructor
    public Rectangle(){super("rectangle");}

    public Rectangle(Pos _pos, int _height, int _width, float _line_thick, Color _line_color, Color _fill_color){
        super(_pos, _height, _width, "rectangle", _line_thick, _line_color, _fill_color);
    }

    public Rectangle(Pos _pos, int _height, int _width, float _line_thick){
        super(_pos, _height, _width, "rectangle", _line_thick);
    }

    //사각형을 그리는 메서드
    @Override
    public void paint(Graphics _graphics){
        _graphics.setColor(super.line_color);
        ((Graphics2D)_graphics).setStroke(new BasicStroke(super.line_thick));
        _graphics.drawRect(super.getPosX(), super.getPosY(), super.width, super.height);
        if(fill_color != null){
            _graphics.setColor(super.fill_color);
            _graphics.fillRect(super.getPosX(), super.getPosY(), super.width, super.height);
        }
    }
    //toString
    @Override
    public String toString() { return "Rectangle{pos:" + "x="+ pos.getX() + ", y=" + pos.getY() + ", height=" + height + ", width=" + width + '}'; }
}
