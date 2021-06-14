/*
 * Classname: DrawFigure
 * Version: 3.0
 * Date: 2021-06-14
 * Copyright: MIT
 */

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Math.abs;
/*
 * 그림을 그릴때 트리거 역할을 하는 클래스
 * 그려진 그림에대한 정보는 figures에 저장된다
 */
public class DrawFigure extends JFrame{
    private ArrayList<Button> buttons;//버튼을 저장하는 리스트
    private JScrollPane scroll_pane;//도형이 그려지는 scroll pane
    private JMenuBar menu_bar;//메뉴바
    private JToolBar tool_bar;//툴바
    private JComboBox line_color;//선택한 라인 색
    private JComboBox fill_color;//선택한 채움 색
    private JTextField line_thickness;//선 굵기
    private ArrayList<Figure> figures;//그림을 저장하는 리스트
    private Figure copy_buffer;//복사한 도형 저장용
    private static boolean dragged = false;//그룹화 여부
    private static boolean can_draw = true;//버튼위치 침범 여부
    private static String curr_clicked;//가장 최근에 클릭한 버튼 이름
    private String clicked_menu_item;

    //기본 생성자
    public DrawFigure(){
        super();
        this.buttons = new ArrayList<>();
        this.figures = new ArrayList<>();
        copy_buffer = null;
        initMenuBar();
        initToolBar();
        initScrollPan();
        setSize(1000, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLocation(500, 0);
    }


    class ScrollMouseListener implements MouseListener{
        private Figure tmp_figure;//마우스이벤트에서 좌표저장을 위한 Figure클래스
        //마우스 클릭이벤트 메서드
        @Override
        public void mouseClicked(MouseEvent e) {
            Pos clicked_pos = new Pos(e.getX(), e.getY());
            //선택한 도형을 복사한다
            if(clicked_menu_item != null && clicked_menu_item.equals("copy")){
                for(Figure fig : figures){
                    if(!fig.include(clicked_pos)){
                        continue;
                    }
                    System.out.println("copy");
                    Pos pos = new Pos(fig.getPosX(), fig.getPosY());
                    switch(fig.getFigureType()){
                        case "rectangle":
                            copy_buffer = new Rectangle(pos, fig.getHeight(), fig.getWidth(), fig.getLineThick(), fig.getLineColor(), fig.getFillColor());
                            copy_buffer.moveFigure(new Pos(10, 10));
                            break;
                        case "oval":
                            copy_buffer = new Oval(pos, fig.getHeight(), fig.getWidth(), fig.getLineThick(), fig.getLineColor(), fig.getFillColor());
                            copy_buffer.moveFigure(new Pos(10, 10));
                            break;
                        case "group":
                            copy_buffer = new FigureGroup(pos, fig.getHeight(), fig.getWidth());
                            copyFigureGroup(copy_buffer);
                            ((FigureGroup)copy_buffer).moveFigure(new Pos(10, 10));
                            break;
                        default:
                            System.out.println("Unknown figure type");
                    }
                    System.out.println(copy_buffer);
                    repaint();
                    return;
                }
            }
        }

        //마우스 버튼 누르는 이베트 메서드
        @Override
        public void mousePressed(MouseEvent e) {
            Pos tmp_pos = new Pos(e.getX(), e.getY());
            System.out.println(tmp_pos);
            if(onBtn(tmp_pos)){//버튼 위치 침범
                can_draw = false;
                return;
            }
            String l_thickness;
            can_draw = true;
            dragged = false;
            if(curr_clicked != null && (curr_clicked.equals("group") || curr_clicked.equals("ungroup"))){
                this.tmp_figure = new FigureGroup();
                dragged = true;
            }
            else if(e.isControlDown()){
                this.tmp_figure = new FigureGroup();
            }
            else if(curr_clicked != null && curr_clicked.equals("rectangle")){
                this.tmp_figure = new Rectangle();
                l_thickness = line_thickness.getText();
                float l_thickness_f = l_thickness.equals("Line Thickness") ? 1.0f : Float.parseFloat(l_thickness);
                tmp_figure.setLineThick(l_thickness_f);
            }
            else if(curr_clicked != null && curr_clicked.equals("oval")){
                this.tmp_figure = new Oval();
                l_thickness = line_thickness.getText();
                float l_thickness_f = l_thickness.equals("Line Thickness") ? 1.0f : Float.parseFloat(l_thickness);
                tmp_figure.setLineThick(l_thickness_f);
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
            if(onBtn(tmp_pos) || !can_draw || tmp_figure == null){//버튼 위치를 침범했으면 종료
                return;
            }
            if(e.isControlDown()){//도형을 욺직이는 거면
                for(Figure fig : figures){
                    if(fig.include(this.tmp_figure.getPos())){//욺직임의 대상이 되는 도형이면
                        //욺직인다
                        fig.moveFigure(new Pos(e.getX() - this.tmp_figure.getPosX(), e.getY() - this.tmp_figure.getPosY()));
                        repaint();
                        break;
                    }
                }
                return;
            }
            //시작 좌표가 끝나는 좌표보다 작으면 시작점 재정의
            if(!dragged && tmp_pos != null){
                if(tmp_pos.getX() < tmp_figure.getPosX()){
                    swapPosX(tmp_pos, tmp_figure);
                }
                if(tmp_pos.getY() < tmp_figure.getPosY()){
                    swapPosY(tmp_pos, tmp_figure);
                }
            }
            //높이와 너비 할당
            this.tmp_figure.setHeight(Math.abs(tmp_pos.getY() - tmp_figure.getPosY()));
            this.tmp_figure.setWidth(Math.abs(tmp_pos.getX() - tmp_figure.getPosX()));
            if(curr_clicked != null && curr_clicked.equals("group") && dragged){
                makeFigureGroup(tmp_figure);
            }
            else if(curr_clicked != null && curr_clicked.equals("ungroup") && dragged){
                for(Figure fig : figures){
                    if(fig.includeArea(this.tmp_figure.getPos()) && fig.isGroup()){//욺직임의 대상이 되는 도형이면
                        //if(fig.isGroup()){//그룹도형이였으면 지운다
                            System.out.println(111);
                            figures.remove(fig);
                        //}
                        break;
                    }
                }
            }
            //figures에 새로운 도형 추가
            figures.add(this.tmp_figure);
            //그림을 그린다
            //System.out.println(getLineColor(line_color).getRGB());
            this.tmp_figure.setLineColor(getLineColor(line_color));
            this.tmp_figure.setFilleColor(getFillColor(fill_color));
            Graphics g = scroll_pane.getGraphics();
            tmp_figure.paint(g);
        }
        //마우스가 창 안에 들어가는 이벤트 메서드
        @Override
        public void mouseEntered(MouseEvent e) {}

        //마우스가 창 밖으로 나가는 이벤트 메서드
        @Override
        public void mouseExited(MouseEvent e) {}
    }

    public static void setCurrClicked(String _btn){
        curr_clicked = _btn;
    }

    public void setFigures(Figure _figures) {
        this.figures.add(_figures);
    }

    public void setClickedMenuItem(String clicked_menu_item) {
        this.clicked_menu_item = clicked_menu_item;
    }

    public JScrollPane getScrollPane() {
        return scroll_pane;
    }

    public Figure getCopyBuffer() {
        return copy_buffer;
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

    public void makeFigureGroup(Figure _figure){
        //System.out.println(_figure);
        for(Figure fig : figures){
            if(!fig.inDragRange(_figure)){
                continue;
            }
            ((FigureGroup)_figure).addGroups(fig);
        }
    }

    //그룹 도형을 복사했을 때 그 그룹에 속한 도형도 복사한다
    public void copyFigureGroup(Figure _figure){
        ArrayList<Figure> fig_group = new ArrayList<>();
        for(Figure fig : figures){
            if(!fig.inDragRange(_figure)){
                continue;
            }
            Figure tmp_fig = null;
            Pos pos = new Pos(fig.getPosX(), fig.getPosY());
            System.out.println("ffig:  " + fig + ",  " + fig.getFigureType());
            switch(fig.getFigureType()){
                case "rectangle":
                    tmp_fig = new Rectangle(pos, fig.getHeight(), fig.getWidth(), fig.getLineThick(), fig.getLineColor(), fig.getFillColor());
                    //fig_group.add(tmp_fig);
                    ((FigureGroup)_figure).addGroups(tmp_fig);
                    break;
                case "oval":
                    tmp_fig = new Oval(pos, fig.getHeight(), fig.getWidth(), fig.getLineThick(), fig.getLineColor(), fig.getFillColor());
                    ((FigureGroup)_figure).addGroups(tmp_fig);
                    break;
                default:
                    System.out.println("aUnknown figure type");
            }
        }
    }

    private boolean onBtn(Pos _pos){
        return _pos.getY() <= Button.HEIGHT;
    }
    //도형을 추가한다
    public void addFigure(Figure _figure){
        this.figures.add(_figure);
    }

    @Override
    public void paint(Graphics _graphics){
        super.paint(_graphics);
        scroll_pane.paint(scroll_pane.getGraphics());
        for(Button btn : buttons){
            btn.paintComponents(_graphics);
        }
    }


    public ArrayList<Figure> getFigures() {
        return figures;
    }

    //맨 처음 시작 시 버튼을 그린다
    private void initButton(){
        for(int i = 0; i < Button.BUTTONS.length; i++){
            Button button = new Button(Button.BUTTONS[i]);
            button.setBounds(Button.FIRSTX + (i * Button.WIDTH), Button.FIRSTY, Button.WIDTH, Button.HEIGHT);
            button.addActionListener(button.getButtonActionListener());
            add(button);
            buttons.add(button);
        }
    }

    //메뉴바 초기화
    private void initMenuBar(){
        menu_bar = new JMenuBar();
        JMenu menu;
        for(int i = 0; i < MenuBar.MENUS.length; i++){
            menu = new JMenu(MenuBar.MENUS[i]);
            String[] sub_item = MenuItem.items.get(MenuBar.MENUS[i]);
            for(int k = 0; k < sub_item.length; k++){
                MenuItem item = new MenuItem(sub_item[k], this);
                item.addActionListener(item.getMenuActionListener());
                menu.add(item);
//                menu.add(new JMenuItem(sub_item[k]));
            }
            menu_bar.add(menu);
            setJMenuBar(menu_bar);
        }
    }

    //툴바 초기화
    private void initToolBar(){
        tool_bar = new JToolBar();

        for(int i = 0; i < Button.BUTTONS.length; i++){
            Button button = new Button(Button.BUTTONS[i]);
            button.addActionListener(button.getButtonActionListener());
            tool_bar.add(button);
        }
        line_color = new JComboBox(new String[]{"Line color", "black", "red", "blue"});
        fill_color = new JComboBox(new String[]{"fill", "black", "red", "blue"});
        line_thickness = new JTextField();
        line_thickness.setText("Line Thickness");
        tool_bar.add(line_color);
        tool_bar.add(fill_color);
        tool_bar.add(line_thickness);
        getContentPane().add(tool_bar, BorderLayout.NORTH);
    }

    //scrollPan 초기화
    private void initScrollPan(){
        scroll_pane = new JScrollPane(){
            public void paint(Graphics _g){
                super.paint(_g);
                for(Figure fig : figures){
                    fig.paint(_g);
                }
            }
        };
        scroll_pane.getViewport().addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                scroll_pane.repaint();
            }
        });
        scroll_pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll_pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll_pane.addMouseListener(new ScrollMouseListener());
        //scroll_pane.setBackground(Color.red);
        add(scroll_pane, BorderLayout.CENTER);
        //scroll_pane.repaint();
    }

    //선택한 라인 색을 반환
    private Color getLineColor(JComboBox box){
        String color = box.getSelectedItem().toString();
        switch (color){
            case "red":
                return Color.RED;
            case "blue":
                return Color.BLUE;
            default:
                return Color.BLACK;
        }
    }

    //선택한 채움색을 반환
    private Color getFillColor(JComboBox box){
        String color = box.getSelectedItem().toString();
        switch (color){
            case "red":
                return Color.RED;
            case "blue":
                return Color.BLUE;
            case "black":
                return Color.BLACK;
            default:
                return null;
        }
    }
}
