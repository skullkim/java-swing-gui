import javax.swing.*;

public class Main {
    public static void main(String[] args){
        JFrame frame = new JFrame();
        DrawFigure draw_figure = new DrawFigure();
        frame.getContentPane().add(draw_figure);
        frame.setSize(1000, 1000);
        frame.setLocation(3000, 0);
        frame.setVisible(true);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
