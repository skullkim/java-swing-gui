import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        final int RECT_INFO = 4;
        DrawFigure draw_figure = new DrawFigure();
        Scanner scan = new Scanner(System.in);
        int[] rect_info = new int[RECT_INFO];
        while(scan.hasNextInt()){
            for(int i = 0; i < RECT_INFO; i++){
                rect_info[i] = scan.nextInt();
            }
            draw_figure.addRectangle(new Rectangle(rect_info));
        }
        draw_figure.paint(draw_figure.getGraphics());
    }
}
