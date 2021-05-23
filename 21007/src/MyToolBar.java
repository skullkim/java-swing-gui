/*
 * Classname: MyToolBar
 * Version: 1.0
 * Date: 2021-05-23
 * Copyright: MIT
 */
import java.util.ArrayList;

public class MyToolBar {
    private ArrayList<MyButton> buttons;//버튼을 저장하는 리스트
    private int button_idx;//버튼 리스트의 인덱스
    private static String[] btn_lists = {"Rectangle", "Oval", "Line"};//현재까지 버튼의 이름

    //생성자
    public MyToolBar() {
        this.buttons = new ArrayList<>();
    }

    //툴바 초기 생성 메서드
    public void initToolBar() {
        for(int i = 0; i < btn_lists.length; i++){
            MyButton tmp_btn = new MyButton(btn_lists[i]);
            tmp_btn.setBounds(MyButton.BTN_X + (MyButton.BTN_WIDTH * i), MyButton.BTN_Y, MyButton.BTN_WIDTH, MyButton.BTN_HEIGHT);
            tmp_btn.addListener(new MyButtonListener());
            this.buttons.add(tmp_btn);
        }
    }

    //클릭한 버튼을 툴바에서 찾아서 이벤트를 처리한다
    public void findClickedButton(Pos curr_pos) {
        for(MyButton btn : buttons){//어느버튼이 클릭되었는지 확인한다.
            if(btn.include(curr_pos) == true){
                btn.click();
            }
        }
    }

    //그림을 그릴때 버튼위치를 침범했는지 판단한다
    public boolean onToolBar(Pos _pos) {
        return MyButton.BTN_X <= _pos.getX() &&
                _pos.getX() <= MyButton.BTN_X + (MyButton.BTN_WIDTH * btn_lists.length - 1)&&
                MyButton.BTN_Y <= _pos.getY() &&
                _pos.getY() <= MyButton.BTN_HEIGHT;
    }

    //더 순회할 버튼이 있는지를 검사
    public boolean iterateButton(){
        if(this.button_idx >= buttons.size() || this.button_idx < 0){
            this.button_idx = 0;
            return false;
        }
        return true;
    }

    //현재의 버튼을 반환한다
    public MyButton getButton(){
        return this.buttons.get(this.button_idx++);
    }
}
