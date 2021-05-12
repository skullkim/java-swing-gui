import javax.swing.*;

public class Main {
    public static void main(String[] args){
        JFrame frame = new JFrame();
        DrawFigure draw_figure = new DrawFigure();
        frame.setSize(1000, 1000);
        frame.setLocation(3000, 0);
        frame.getContentPane().add(draw_figure);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
