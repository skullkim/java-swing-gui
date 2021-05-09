import javax.swing.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        DrawFigure draw_figure = new DrawFigure();
        final int FIG_INFOS = 4;
        int[] fig_info = new int[FIG_INFOS];
        String figure;
        while(scanner.hasNext()){
            figure = scanner.next();
            for(int i = 0; i < FIG_INFOS; i++){
                fig_info[i] = scanner.nextInt();
            }
            if(figure.equals("사각형")){
                draw_figure.drawRectangle(new Pos(fig_info[0], fig_info[1]), fig_info[2], fig_info[3]);
            }
            else{
                draw_figure.drawOval(new Pos(fig_info[0], fig_info[1]), fig_info[2], fig_info[3]);
            }
        }
    }
}
