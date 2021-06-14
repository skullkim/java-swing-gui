/*
 * Classname: Figure
 * Version: 3.0
 * Date: 2021-06-14
 * Copyright: MIT
 */
import javax.swing.*;
import java.awt.*;

/*
 * 도형에 대한 정보를 저장하는 클래스
 */
public abstract class Figure extends JFrame {
    protected Pos pos;//시작 위치를 저장
    protected int height;//높이
    protected int width;//너비
    protected float line_thick;//라인 두께
    protected String figure_type;//도형 타입
    protected Color line_color = null;//라인 색
    protected Color fill_color = null;//채움색



    //기본 생성자
    public Figure(){
        this(new Pos(0, 0), 0, 0, "");
    }

    public Figure(String _fig_type){
        this.figure_type = _fig_type;
        this.line_thick = 1.0f;
    }

    public Figure(Pos _pos, int _height, int _width, String _fig_type){
        this.pos = _pos;
        this.height = _height;
        this.width = _width;
        this.fill_color = null;
        this.figure_type = _fig_type;
        this.line_thick = 1.0f;
    }

    public Figure(Pos _pos, int _height, int _width, String _fig_type, float _line_thick, Color _line_color, Color _fill_color){
        this(_pos, _height, _width, _fig_type);
        this.line_color = _line_color;
        this.fill_color = _fill_color;
        this.line_thick = _line_thick;
    }

    public Figure(Pos _pos, int _height, int _width, String _fig_type, float _line_thick){
        this(_pos, _height, _width, _fig_type);
        this.line_thick = _line_thick;
    }

    //해당 클래스를 상속받는 객체에서 해당 객체에 맞는 형식으로 그림을 그린다
    @Override
    public abstract void paint(Graphics _graphics);

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

    //_pos를 포함하면 true, 아니면 false
    public boolean includeArea(Pos _pos){
        return this.pos.getX() <= _pos.getX() ||
                _pos.getX() <= this.pos.getX() + this.width ||
                this.pos.getY() <= _pos.getY() ||
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
    public String getFigureInfo(){
        String line_c = this.line_color == null ? "null" : Integer.toString(this.line_color.getRGB());
        String fill_c = this.fill_color == null ? "null" : Integer.toString(this.fill_color.getRGB());
        String info = this.figure_type + "," + this.pos.getX() + "," + this.pos.getY()
                + "," + this.height + "," + this.width + "," + this.line_thick + ","
                + line_c + "," + fill_c;
        return info;
    }

    public String getFigureType() {
        return figure_type;
    }

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

    public float getLineThick() {
        return line_thick;
    }

    public Color getLineColor() {
        return line_color;
    }

    public Color getFillColor() {
        return fill_color;
    }

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

    public void setLineThick(float line_thick) {
        if(line_thick < 0.0f){
            line_thick = 0.0f;
        }
        this.line_thick = line_thick;
    }

    public void setLineColor(Color _color){
        this.line_color = _color;
    }

    public void setFilleColor(Color _color){
        this.fill_color = _color;
    }

    //toString
    @Override
    public String toString() { return "Figure{pos=" + pos.getX() + ", " + pos.getY() + ", height=" + height + ", width=" + width + '}'; }
}
