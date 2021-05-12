/*
 * Classname: MyButton
 * Version: 2.0
 * Date: 2021-05-21
 * Copyright: MIT
 */
import java.awt.*;

public class MyButton extends Figure{
    //기본 생성자
    public MyButton(){super();}

    //버튼 이름을 파라미터로 하는 생성자
    public MyButton(String _btn_name){
        super(_btn_name);
    }

    //버튼의 범위를 설정
//    public void setBounds(int _x, int _y, int _width, int _height){
//        super.setPos(new Pos(_x, _y));
//        super.setHeight(_height);
//        super.setWidth(_width);
//    }


    //버튼을 그린다
    @Override
    public void paintComponent(Graphics _graphics) {
        _graphics.drawRect(super.getPosX(), super.getPosY(), super.width, super.height);
        _graphics.drawString(super.name, (super.getPosX() + 10), (super.getPosY() + 15));
    }

}
