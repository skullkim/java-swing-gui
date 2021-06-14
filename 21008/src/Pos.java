/*
 * Classname: Pos
 * Version: 1.0
 * Date: 2021-05-09
 * Copyright: MIT
 */

/*
 * 도형의 시작점을 저장하는 클래스
 */
public class Pos{
    private int y;//y좌표
    private int x;//x좌표

    //constructor
    public Pos(){}

    public Pos(int _x, int _y){
        this.x = _x;
        this.y = _y;
    }

    //getter
    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    //setter
    public void setY(int y) {
        if(y < 0){
            System.out.println("Y pos must larger than Y");
            return;
        }
        this.y = y;
    }

    public void setX(int x) {
        if(x < 0){
            System.out.println("X pos must larger than X");
            return;
        }
        this.x = x;
    }

    public void setPos(int _x, int _y){
        if(_x < 0 || _y < 0){
            System.out.println("X, Y must larger than 0");
            return;
        }
        this.setX(_x);
        this.setY(_y);
    }

    //toString
    @Override
    public String toString() {
        return "Pos{y=" + y + ", x=" + x + '}';
    }
}
