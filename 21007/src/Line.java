import java.awt.*;
import java.awt.color.ICC_ProfileGray;

/*
 * Classname: Line
 * Version: 1.0
 * Date: 2021-05-17
 * Copyright: MIT
 */
public class Line extends Figure{
    //생성자
    public Line(){super();}

    public Line(Pos _pos, int _height, int _width){
        super(_pos, _height, _width);
    }

    //직선을 그린다
    @Override
    public void paintComponent(Graphics _graphics){
        _graphics.drawLine(super.getPosX(), super.getPosY(), super.width + super.getPosX(), super.height + super.getPosY());
    }
}
