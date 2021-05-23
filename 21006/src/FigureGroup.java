/*
 * Classname: FigureGroup
 * Version: 1.0
 * Date: 2021-05-21
 * Copyright: MIT
 */

import java.awt.*;
import java.util.ArrayList;

//도형 그룹에 해당하는 클래스
public class FigureGroup extends Figure{
    private ArrayList<Figure> groups;//현재 그룹에 포함된 도형들

    //생성자
    public FigureGroup(){
        super();
        this.groups = new ArrayList<>();
    }

    //그룹에 도형을 추가한다
    public void setGroups(Figure _fig){
        groups.add(_fig);
    }

    //그룹을 그린다
    @Override
    public void paintComponent(Graphics _graphics) {
        for(Figure fig : groups){
            //System.out.println(fig);
            fig.paintComponent(_graphics);
        }
    }

    //그룹 도형을 이동한다
    @Override
    public void moveFigure(Pos _moved){
        super.moveFigure(_moved);
        for(Figure fig : groups){
            fig.moveFigure(_moved);
        }
    }

    //그룹여부, 그룹이면 true, 아니면 false
    @Override
    public boolean isGroup(){
        return true;
    }

    //toString
    @Override
    public String toString() { return "Group{pos=" + pos.getX() + ", " + pos.getY() + ", height=" + height + ", width=" + width + '}'; }
}
