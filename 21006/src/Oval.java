/*
 * Classname: Oval
 * Version: 1.0
 * Date: 2021-05-09
 * Copyright: MIT
 */
import java.awt.*;

/*
 * 원을 그리는 메서드, Figure를 상속한다
 */
public class Oval extends Figure{
    //constructor
    public Oval(){super();}

    public Oval(Pos _pos, int _height, int _width){
        super(_pos, _height, _width);
    }

    //원을 그리는 메서드
    @Override
    public void paintComponent(Graphics _graphics){
        _graphics.drawOval(super.getPosX(), super.getPosY(), super.width, super.height);
    }
    //toString
    @Override
    public String toString() { return "Oval{pos=" + pos.getX() + ", " + pos.getY() + ", height=" + height + ", width=" + width + '}'; }
}
