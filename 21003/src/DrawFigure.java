/*
 * Classname: DrawFigure
 * Version: 2.0
 * Date: 2021-05-09
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
    private ArrayList<Figure> buttons;//버튼을 저장하는 리스
    private Figure tmp_figure;//마우스이벤트에서 좌표저장을 위한 Figure클래스

    //기본 생성자
    public DrawFigure(){
        super();
        super.addMouseListener(this);
        this.figures = new ArrayList<>();
        this.buttons = new ArrayList<>();
        MyButton apple = new MyButton("apple");
        MyButton banana = new MyButton("banana");
        apple.setBounds(10, 60, 70, 25);  // 위치와 크기를 결정한다. x=10 y=60 width=70, height=25 이다.
        banana.setBounds(90, 60, 70, 25);
        this.buttons.add(apple);
        this.buttons.add(banana);
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
        for(Figure btn : buttons){//어느버튼이 클릭되었는지 확인한다.
            if(btn.include(curr_pos) == true){
                System.out.println(btn.getName());
            }
        }
    }

    //마우스 버튼 누르는 이베트 메서드
    @Override
    public void mousePressed(MouseEvent e) {
        this.tmp_figure = e.isShiftDown() ? new Oval() : new Rectangle();
        this.tmp_figure.setPos(new Pos(e.getX(), e.getY()));

    }

    //마수스 버튼을 때는 이벤트 메서드
    @Override
    public void mouseReleased(MouseEvent e) {
        Pos tmp_pos = new Pos(e.getX(), e.getY());
        //시작 좌표가 끝나는 좌표보다 작으면 시작점 재정의
        if(tmp_pos.getX() < tmp_figure.getPosX()){
            swapPosX(tmp_pos, tmp_figure);
        }
        if(tmp_pos.getY() < tmp_figure.getPosY()){
            swapPosY(tmp_pos, tmp_figure);
        }
        //높이와 너비 할당
        this.tmp_figure.setHeight((tmp_pos.getY() - tmp_figure.getPosY()));
        this.tmp_figure.setWidth((tmp_pos.getX() - tmp_figure.getPosX()));
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
}
