/*
 * Classname: MenuItem
 * Version: 1.0
 * Date: 2021-06-14
 * Copyright: MIT
 */
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;

/*
 * 메뉴아이템 항목과 각 항목에 대한 이벤트 처리를 하는 클래스
 */
public class MenuItem extends JMenuItem {
    public static final String[] FILE_ITEMS = {"open", "save"};//파일에 대한 항목
    public static final String[] EDIT = {"copy", "paste"};//수정에 대한 항목
    public static final HashMap<String, String[]> items = new HashMap<>() {{
        put("File", FILE_ITEMS);
        put("Edit", EDIT);
    }};
    private DrawFigure draw_figure;

    public MenuItem(){}
    public MenuItem(String _item_name, DrawFigure _draw_figure){
        super(_item_name);
        this.draw_figure = _draw_figure;
    }

    private class MenuActionListener implements ActionListener {//메뉴 아이템 클릭 이벤트
        @Override
        public void actionPerformed(ActionEvent e) {//클릭된 각 아아템이 해야하는 로직으로 이동
            String clicked_item = e.getActionCommand();
            switch(clicked_item){
                case "open":
                    openFile();
                    break;
                case "save":
                    saveFile();
                    break;
                case "copy":
                    copyFigure();
                    break;
                case "paste":
                    pastFigure();
                    break;
                default:
                    System.out.println("unresolved item");
            }
        }

        //복사한 도형을 붙여넣은 메서드
        private void pastFigure() {
            Figure copied_fig = draw_figure.getCopyBuffer();
                Graphics g = draw_figure.getScrollPane().getGraphics();
                copied_fig.paint(g);
                if(copied_fig.getFigureType().equals("group")){
                    ArrayList<Figure> figure = ((FigureGroup)copied_fig).getGroups();
                    for(Figure fig : figure){
                        draw_figure.setFigures(fig);
                    }
                }
                draw_figure.setFigures(copied_fig);
                draw_figure.repaint();
        }

        //복사를 하는 메서드
        private void copyFigure() {
            draw_figure.setClickedMenuItem("copy");
        }

        //그린 그림을 저장하는 메서드
        private void saveFile() {
            JFileChooser file_chooser = new JFileChooser(new File("/Users/iskull/"));
            file_chooser.setDialogTitle("Save a File");
            file_chooser.setFileFilter(new FileTypeFilter(".txt", "Text File"));
            int result = file_chooser.showSaveDialog(null);
            if(result == JFileChooser.APPROVE_OPTION){
                String file_name = file_chooser.getName(file_chooser.getSelectedFile());
                file_chooser.setName(file_name + ".txt");
//                System.out.println(file_chooser.getName(file_chooser.getSelectedFile()));
                File fi = file_chooser.getSelectedFile();
                try{
                    FileWriter fw = new FileWriter(fi.getPath());
                    ArrayList<Figure> figure = draw_figure.getFigures();
                    for(Figure fig : figure){
                        String info = fig.getFigureInfo();
                        info += "\n";
                        fw.write(info);
                    }
                    fw.flush();
                    fw.close();
                }
                catch(Exception e){
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        }
        //저장한 그림을 여는 메서드
        private void openFile() {
            JFileChooser file_chooser = new JFileChooser("/User/iskull/");
            file_chooser.setDialogTitle("Open a File");
            file_chooser.setFileFilter(new FileTypeFilter(".txt", "Text File"));
            int result = file_chooser.showOpenDialog(null);
            if(result == JFileChooser.APPROVE_OPTION){
                try{
                    File fi = file_chooser.getSelectedFile();
                    BufferedReader br = new BufferedReader(new FileReader(fi.getPath()));
                    String fig = "";
                    while((fig = br.readLine()) != null){
                        //System.out.println(fig);
                        drawFigure(fig);
                    }
                    draw_figure.repaint();
                    br.close();
                }
                catch(Exception e){
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
            }
        }
        //저장한 그림을 열때 그림을 그리는 메서드
        private void drawFigure(String _fig_info){
            String[] fig_type = _fig_info.split(",");
            Figure tmp_fig = null;
            Pos pos = new Pos(Integer.parseInt(fig_type[1]), Integer.parseInt(fig_type[2]));
            int height = Integer.parseInt(fig_type[3]);
            int width = Integer.parseInt(fig_type[4]);
            float line_thick = Float.parseFloat(fig_type[5]);
            Color line_color = null;
            if(!fig_type[6].equals("null")){
                line_color = new Color(Integer.parseInt(fig_type[6]));
            }
            Color fill_color = null;
            if(!fig_type[7].equals("null")){
                fill_color = new Color(Integer.parseInt(fig_type[7]));
            }
            switch (fig_type[0]){
                case "rectangle":
                    tmp_fig = new Rectangle(pos, height, width, line_thick, line_color, fill_color);
                    break;
                case "oval":
                    tmp_fig = new Oval(pos, height, width, line_thick, line_color, fill_color);
                    break;
                case "group":
                    tmp_fig = new FigureGroup(pos, height, width);
                    draw_figure.makeFigureGroup(tmp_fig);
                    break;
                default:
                    System.out.println("unknown");
            }
            Graphics g = draw_figure.getScrollPane().getGraphics();
            tmp_fig.paint(g);
            draw_figure.setFigures(tmp_fig);
        }
    }

    public MenuActionListener getMenuActionListener(){
        return new MenuActionListener();
    }

}

//파일을 저장, 불러올때 사용하는 파일 확장자 필터
class FileTypeFilter extends FileFilter{
    private String extension;
    private String description;
    public FileTypeFilter(String _extension, String _description){
        this.extension = _extension;
        this.description = _description;
    }
    @Override
    public boolean accept(File f) {
        return f.isDirectory() || f.getName().endsWith(extension);
    }

    @Override
    public String getDescription() {
        return description + String.format("(*%s)", extension);
    }
}