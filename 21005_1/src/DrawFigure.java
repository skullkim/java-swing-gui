/*
 * Classname: DrawFigure
 * Version: 3.0
 * Date: 2021-05-17
 * Copyright: MIT
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import static java.lang.Math.abs;
/*
 * 그림을 그릴때 트리거 역할을 하는 클래스
 * 그려진 그림에대한 정보는 figures에 저장된다
 */
public class DrawFigure extends JPanel implements MouseListener{
    private ArrayList<Figure> figures;//그림을 저장하는 리스트
    private ArrayList<MyButton> buttons;//버튼을 저장하는 리스
    private Figure tmp_figure;//마우스이벤트에서 좌표저장을 위한 Figure클래스
    private static String curr_clicked;//가장 최근에 클릭한 버튼을 기록
    private static boolean can_draw = true;//그림을 그릴때 버튼을 침범하면 false, 아니면 true
    private static String[] btn_lists = {"Rectangle", "Oval", "Line"};//현재까지 버튼의 이름
    //기본 생성자
    public DrawFigure(){
        super();
        super.addMouseListener(this);
        this.figures = new ArrayList<>();
        this.buttons = new ArrayList<>();
        //버튼을 초기화
        for(int i = 0; i < btn_lists.length; i++){
            MyButton tmp_btn = new MyButton(btn_lists[i]);
            tmp_btn.setBounds(MyButton.BTN_X + (MyButton.BTN_WIDTH * i), MyButton.BTN_Y, MyButton.BTN_WIDTH, MyButton.BTN_HEIGHT);
            tmp_btn.addListener(new MyButtonListener());
            this.buttons.add(tmp_btn);
        }
    }

    //인자로 받은 Figure클래스를 저장하는 성자
    public DrawFigure(Figure _figure){
        this();
        this.figures.add(_figure);
    }

    //저장된 도형을 전부 그리는 클래스
    @Override
    public void paintComponent(Graphics _graphics){
        for(Figure btn : buttons){
            btn.paintComponent(_graphics);
        }

        for(Figure fig : figures) {
            fig.paintComponent(_graphics);
        }
    }

    //도형을 추가한다
    public void addFigure(Figure _figure){
        _figure.paintComponent(getGraphics());
        this.figures.add(_figure);
    }

    //마우스 클릭이벤트 메서드
    @Override
    public void mouseClicked(MouseEvent e) {
        Pos curr_pos = new Pos(e.getX(), e.getY());
        for(MyButton btn : buttons){//어느버튼이 클릭되었는지 확인한다.
            if(btn.include(curr_pos) == true){
                btn.click();
            }
        }
    }

    //마우스 버튼 누르는 이베트 메서드
    @Override
    public void mousePressed(MouseEvent e) {
        Pos tmp_pos = new Pos(e.getX(), e.getY());
        //System.out.println("p" + tmp_pos);
        //마우스를 press한 위치가 버튼이거나 아직 도형을 클릭하지 않았다면 종료
        if(onBtn(tmp_pos) || curr_clicked == null){
            can_draw = false;
            return;
        }
        else{
            can_draw = true;
        }
        //현재 선택된 도형에 맞게 초기화
        switch (curr_clicked){
            case "Rectangle":
                this.tmp_figure = new Rectangle();
                break;
            case "Oval":
                this.tmp_figure = new Oval();
                break;
            case "Line":
                this.tmp_figure = new Line();
                break;
        }
        this.tmp_figure.setPos(tmp_pos);

    }

    //마수스 버튼을 때는 이벤트 메서드
    @Override
    public void mouseReleased(MouseEvent e) {
        Pos tmp_pos = new Pos(e.getX(), e.getY());
        //System.out.println("r" + tmp_pos);
        //마우스를 release한 위치가 버튼이거나 press한 위치가 버튼이면 그림을 그리지 않고 종료
        if(onBtn(tmp_pos) || !can_draw){
            return;
        }
        //시작 좌표가 끝나는 좌표보다 작으면 시작점 재정의
        if(!curr_clicked.equals("Line") && tmp_pos.getX() < tmp_figure.getPosX()){
                swapPosX(tmp_pos, tmp_figure);
        }
        if(!curr_clicked.equals("Line") && tmp_pos.getY() < tmp_figure.getPosY()){
            swapPosY(tmp_pos, tmp_figure);
        }
        this.tmp_figure.setHeight(tmp_pos.getY() - tmp_figure.getPosY());
        this.tmp_figure.setWidth(tmp_pos.getX() - tmp_figure.getPosX());
        //figures에 새로운 도형 추가
        //그림을 그린다
        this.addFigure(tmp_figure);

    }

    //마우스가 창 안에 들어가는 이벤트 메서드
    @Override
    public void mouseEntered(MouseEvent e) {}

    //마우스가 창 밖으로 나가는 이벤트 메서드
    @Override
    public void mouseExited(MouseEvent e) {}

    public static void setCurrClicked(String _clicked){
        curr_clicked = _clicked;
    }

    //_pos와 _figure의 Pos x를 바꾼다
    private void swapPosX(Pos _pos, Figure _figure){
        int x = _pos.getX();
        _pos.setX(_figure.getPosX());
        _figure.setPosX(x);
    }

    //_pos와 _figure의 Pos y를 바꾼다
    private void swapPosY(Pos _pos, Figure _figure){
        int y = _pos.getY();
        _pos.setY(_figure.getPosY());
        _figure.setPosY(y);
    }

    //그림을 그릴때 버튼위치를 침범했는지 판단한다
    private boolean onBtn(Pos _pos){
        return MyButton.BTN_X <= _pos.getX() &&
                _pos.getX() <= MyButton.BTN_X + (MyButton.BTN_WIDTH * btn_lists.length - 1)&&
                MyButton.BTN_Y <= _pos.getY() &&
                _pos.getY() <= MyButton.BTN_HEIGHT;
    }
}
