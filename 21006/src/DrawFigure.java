/*
 * Classname: DrawFigure
 * Version: 2.0
 * Date: 2021-05-21
 * Copyright: MIT
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import static java.lang.Math.abs;
/*
 * 그림을 그릴때 트리거 역할을 하는 클래스
 * 그려진 그림에대한 정보는 figures에 저장된다
 */
public class DrawFigure extends JPanel implements MouseListener{
    private ArrayList<Figure> figures;//그림을 저장하는 리스트
    private ArrayList<Button> buttons;//버튼을 저장하는 리스트
    private Figure tmp_figure;//마우스이벤트에서 좌표저장을 위한 Figure클래스
    private static boolean dragged = false;//그룹화 여부
    private static boolean can_draw = true;//버튼위치 침범 여부
    private static String curr_clicked;//가장 최근에 클릭한 버튼 이름

    //기본 생성자
    public DrawFigure(){
        super();
        super.addMouseListener(this);
        this.figures = new ArrayList<>();
        this.buttons = new ArrayList<>();
        initButton();
    }

    //인자로 받은 Figure클래스를 저장하는 성자
    public DrawFigure(Figure _figure){
        this();
        this.figures.add(_figure);
    }

    //저장된 도형을 전부 그리는 클래스
    @Override
    public void paintComponent(Graphics _graphics){
        for(Figure fig : figures){
            fig.paintComponent(_graphics);
        }
    }

    //도형을 추가한다
    public void addFigure(Figure _figure){
        this.figures.add(_figure);
    }

    //마우스 클릭이벤트 메서드
    @Override
    public void mouseClicked(MouseEvent e) {}

    //마우스 버튼 누르는 이베트 메서드
    @Override
    public void mousePressed(MouseEvent e) {
        Pos tmp_pos = new Pos(e.getX(), e.getY());
        if(onBtn(tmp_pos)){//버튼 위치 침범
            can_draw = false;
            return;
        }
        can_draw = true;
        if(e.isShiftDown()){
            this.tmp_figure = new FigureGroup();
            dragged = true;
        }
        else if(e.isControlDown()){
            this.tmp_figure = new FigureGroup();
        }
        else if(curr_clicked.equals("rectangle")){
            this.tmp_figure = new Rectangle();
        }
        else if(curr_clicked.equals("oval")){
            this.tmp_figure = new Oval();
        }
        else{
            return;
        }
        this.tmp_figure.setPos(tmp_pos);

    }

    //마수스 버튼을 때는 이벤트 메서드
    @Override
    public void mouseReleased(MouseEvent e) {
        Pos tmp_pos = new Pos(e.getX(), e.getY());
        if(onBtn(tmp_pos) || !can_draw){//버튼 위치를 침범했으면 종료
            return;
        }
        //System.out.println(tmp_pos);
        if(e.isControlDown()){//도형을 욺직이는 거면
            for(Figure fig : figures){
                if(fig.include(this.tmp_figure.getPos())){//욺직임의 대상이 되는 도형이면
//                    System.out.println(fig);
                    //욺직인다
                    fig.moveFigure(new Pos(e.getX() - this.tmp_figure.getPosX(), e.getY() - this.tmp_figure.getPosY()));
                    repaint();
                    if(fig.isGroup()){//그룹도형이였으면 지운다
                        System.out.println(fig);
                        figures.remove(fig);
                        //System.out.println(fig);
                    }
//                    System.out.println(fig);
                    break;
                }
            }
            return;
        }
        //시작 좌표가 끝나는 좌표보다 작으면 시작점 재정의
        if(!dragged){
            if(tmp_pos.getX() < tmp_figure.getPosX()){
                swapPosX(tmp_pos, tmp_figure);
            }
            if(tmp_pos.getY() < tmp_figure.getPosY()){
                swapPosY(tmp_pos, tmp_figure);
            }
        }
        //높이와 너비 할당
        this.tmp_figure.setHeight((tmp_pos.getY() - tmp_figure.getPosY()));
        this.tmp_figure.setWidth((tmp_pos.getX() - tmp_figure.getPosX()));
        if(dragged){
            makeFigureGroup(tmp_figure);
        }
        //figures에 새로운 도형 추가
        figures.add(this.tmp_figure);
        //그림을 그린다
        tmp_figure.paintComponent(getGraphics());
    }

    //마우스가 창 안에 들어가는 이벤트 메서드
    @Override
    public void mouseEntered(MouseEvent e) {}

    //마우스가 창 밖으로 나가는 이벤트 메서드
    @Override
    public void mouseExited(MouseEvent e) {}

    public static void setCurrClicked(String _btn){
        curr_clicked = _btn;
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

    private void makeFigureGroup(Figure _figure){
        //System.out.println(_figure);
        for(Figure fig : figures){
            if(!fig.inDragRange(_figure)){
                continue;
            }
            ((FigureGroup)_figure).setGroups(fig);
        }
    }

    //맨 처음 시작 시 버튼을 그린다
    private void initButton(){
        for(int i = 0; i < Button.BUTTONS.length; i++){
            Button button = new Button(Button.BUTTONS[i]);
            button.setBounds(Button.FIRSTX + (i * Button.WIDTH), Button.FIRSTY + (i * Button.HEIGHT), Button.WIDTH, Button.HEIGHT);
            button.addActionListener(button.getButtonActionListener());
            add(button);
            buttons.add(button);
        }
    }

    //그림을 그릴때 버튼위치를 침범했는지 판단한다
    private boolean onBtn(Pos _pos){
        return _pos.getY() <= Button.HEIGHT;
    }
}
