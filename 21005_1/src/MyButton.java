/*
 * Classname: MyButton
 * Version: 3.0
 * Date: 2021-05-17
 * Copyright: MIT
 */
import java.awt.*;


public class MyButton extends Figure{
    private MyButtonListener button_listener;
    public final static int BTN_X = 0;//버튼 초기 X좌표
    public final static int BTN_Y = 0;//버튼 초기 Y좌표
    public final static int BTN_HEIGHT = 30;//버튼 높이
    public final static int BTN_WIDTH = 80;//버튼 너비
    //기본 생성자
    public MyButton(){super();}

    //버튼 이름을 파라미터로 하는 생성자
    public MyButton(String _btn_name){
        super(_btn_name);
    }

    //해당 버튼에 연결할 이벤트 리스터를 추가한다
    public void addListener(MyButtonListener _btn_listener){
        this.button_listener = _btn_listener;
        this.button_listener.setMyButton(this);
    }

    //해당 버튼이 클릭되면 행해지는 메서드
    public void click(){
        button_listener.actionPerformed();
    }

    //버튼을 그린다
    @Override
    public void paintComponent(Graphics _graphics) {
        _graphics.drawRect(super.getPosX(), super.getPosY(), super.width, super.height);
        _graphics.drawString(super.name, (super.getPosX() + 10), (super.getPosY() + 15));
    }

}
