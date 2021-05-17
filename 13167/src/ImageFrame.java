import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageFrame extends JFrame {
    private String image_name;//이미지 이름
    private int width;//이미지 너비
    private int height;//이미지 높이
    private BufferedImage img = null;//이미지
    MyPanel myPanel = null;//이미지 패널

    class MyPanel extends JPanel{
        @Override
        public void paint(Graphics _g){
            _g.drawImage(img, 0, 0, null);
        }
    }

    public ImageFrame(String _img_name){
        super();
        this.image_name =  _img_name;
        loadImage();
        super.setSize(width + 50, height + 50);
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);
        super.setVisible(true);
        super.setLayout(null);
    }

    //이미지를 로딩하는 메서드
    public void loadImage(){
        try{
            img = ImageIO.read(new File(image_name));//이미지를 로딩해서
        }
        catch(IOException e){
            e.printStackTrace();
        }
        //이미지를 넣을 패널을 설정한다
        width = img.getWidth();
        height = img.getHeight();
        myPanel = new MyPanel();
        myPanel.setSize(width, height);
        this.add(myPanel);
    }

}
