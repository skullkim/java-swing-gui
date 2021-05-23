/*
 * Classname: Button
 * Version: 1.0
 * Date: 2021-05-21
 * Copyright: MIT
 */

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.BatchUpdateException;
//버튼을 사용 및 그리위 한 버튼 리스너, 버튼을 그릴때 필요한 값들을 포함하는 클래스
public class Button extends JButton {
    public static final String[] BUTTONS = {"rectangle", "oval"};//버튼 종류
    public static final int FIRSTX = 0;//x좌표
    public static final int FIRSTY = 0;//y좌표
    public static final int WIDTH = 40;//너비
    public static final int HEIGHT = 30;//높이
    class ButtonActionListener implements ActionListener{//버튼 액션 리스너
        @Override
        public void actionPerformed(ActionEvent _e) {
            DrawFigure.setCurrClicked(_e.getActionCommand());
        }
    }

    //생성자
    public Button(String _name){
        super(_name);
    }

    //action listener getter
    public ButtonActionListener getButtonActionListener(){
        return new ButtonActionListener();
    }
}
