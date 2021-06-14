/*
 * Classname: Button
 * Version: 2.0
 * Date: 2021-06-14
 * Copyright: MIT
 */

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.BatchUpdateException;
//버튼을 사용 및 그리위 한 버튼 리스너, 버튼을 그릴때 필요한 값들을 포함하는 클래스
public class Button extends JButton {
    public static final String[] BUTTONS = {"rectangle", "oval", "group", "ungroup"};//버튼 종류
    public static final int FIRSTX = 0;//x좌표
    public static final int FIRSTY = 0;//y좌표
    public static final int WIDTH = 100;//너비
    public static final int HEIGHT = 30;//높이
    class ButtonActionListener implements ActionListener{//버튼 액션 리스너
        @Override
        public void actionPerformed(ActionEvent _e) {
            System.out.println("e");
            DrawFigure.setCurrClicked(_e.getActionCommand());
        }
    }

    //생성자
    public Button(String _name){
        super(_name);
    }

    public Button (ImageIcon _image_icon){
        super(_image_icon);
    }

    //action listener getter
    public ButtonActionListener getButtonActionListener(){
        return new ButtonActionListener();
    }
}
