/*
 * Classname: Figure
 * Version: 2.0
 * Date: 2021-05-21
 * Copyright: MIT
 */
import javax.swing.*;
import java.awt.*;

/*
 * 도형에 대한 정보를 저장하는 클래스
 */
public abstract class Figure extends JPanel {
    protected Pos pos;//시작 위치를 저장
    protected int height;//높이
    protected int width;//너비

    //기본 생성자
    public Figure(){
        this(new Pos(0, 0), 0, 0);
    }

    public Figure(Pos _pos, int _height, int _width){
        this.pos = _pos;
        this.height = _height;
        this.width = _width;
    }

    //해당 클래스를 상속받는 객체에서 해당 객체에 맞는 형식으로 그림을 그린다
    @Override
    public abstract void paintComponent(Graphics _graphics);

    //드래그한 범위 내에 도형이 포함되는지 검사, 포함하면 true, 아니면 false
    public boolean inDragRange(Figure _range){
        boolean a = (this.pos.getX() >= _range.getPosX() && _range.getPosY() <= this.pos.getY()) &&
                (this.pos.getX() + this.getWidth() <= _range.getPosX() + _range.getWidth() &&
                        this.pos.getY() + this.getHeight() <= _range.getPosY() + _range.getHeight());
        System.out.println(this);
        System.out.println(a);
        return a;
    }

    //_pos를 포함하면 true, 아니면 false
    public boolean include(Pos _pos){
        return this.pos.getX() <= _pos.getX() &&
                _pos.getX() <= this.pos.getX() + this.width &&
                this.pos.getY() <= _pos.getY() &&
                _pos.getY() <= this.pos.getY() + this.height;
    }

    //도형을 욺직인다
    public void moveFigure(Pos _moved){
        this.setPosX(this.getPosX() + _moved.getX());
        this.setPosY(this.getPosY() + _moved.getY());
    }

    //그룹도형이면 true, 아니면 false
    public boolean isGroup(){
        return false;
    }

    //getter
    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    public int getPosY(){ return this.pos.getY(); }

    public int getPosX(){
        return this.pos.getX();
    }

    public Pos getPos() { return this.pos; }

    //setter
    public void setHeight(int height) {
        if(height < 0){
            System.out.println("height must larger than 0");
            return;
        }
        this.height = height;
    }

    public void setWidth(int width) {
        if(width < 0){
            System.out.println("height must larger than 0");
            return;
        }
        this.width = width;
    }

    public void setPosY(int _y){
        this.pos.setY(_y);
    }

    public void setPosX(int _x){
        this.pos.setX(_x);
    }

    public void setPos(Pos _pos) {this.pos = _pos;}

    //toString
    @Override
    public String toString() { return "Figure{pos=" + pos.getX() + ", " + pos.getY() + ", height=" + height + ", width=" + width + '}'; }
}
