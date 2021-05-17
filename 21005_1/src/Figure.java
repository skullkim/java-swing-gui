/*
 * Classname: Figure
 * Version: 1.1
 * Date: 2021-05-17
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
    protected String name;//도형을 이름

    //기본 생성자
    public Figure(){
        this(new Pos(0, 0), 0, 0);
    }

    public Figure(Pos _pos, int _height, int _width){
        this.pos = _pos;
        this.height = _height;
        this.width = _width;
    }

    public Figure(String _name){
        this.name = _name;
    }

    //해당 클래스를 상속받는 객체에서 해당 객체에 맞는 형식으로 그림을 그린다
    @Override
    public abstract void paintComponent(Graphics _graphics);

    //인자로 받은 위치가 도형 내부에 는지 판단, 있으면 true, 없으면 false 반환
    public boolean include(Pos _curr_pos){
        return this.getPosX() <= _curr_pos.getX() &&
                _curr_pos.getX() <= this.getPosX() + this.width &&
                this.getPosY() <= _curr_pos.getY() &&
                _curr_pos.getY() <= this.getPosY() + this.height;
    }

    //도형의 기본 범위를 설
    public void setBounds(int _x, int _y, int _width, int _height){
        this.pos = new Pos(_x, _y);
        this.height = _height;
        this.width = _width;
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

    public String getName(){ return this.name; }

    //setter
    public void setHeight(int height) {
//        if(height < 0){
//            System.out.println("height must larger than 0");
//            return;
//        }
        this.height = height;
    }

    public void setWidth(int width) {
//        if(width < 0){
//            System.out.println("height must larger than 0");
//            return;
//        }
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
