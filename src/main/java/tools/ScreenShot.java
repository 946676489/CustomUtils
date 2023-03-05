package tools;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
 
public class ScreenShot {
 
    private String filePreStr; // 默认前缀（选择存储路径例如： D：\\）
    private String defName = "cameraImg";  // 默认截图名称
    static int serialNum = 0;  //截图名称后面的数字累加
    private String imageFormat; // 图像文件的格式
    private String defaultImageFormat = "png"; //截图后缀
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize(); //获取全屏幕的宽高尺寸等数据
 
    public ScreenShot() {
        filePreStr = defName;
        imageFormat = defaultImageFormat;
    }
 
    public ScreenShot(String s, String format) {
        filePreStr = s;
        imageFormat = format;
    }
 
    public void snapShot() {
        try {
            // *** 核心代码 *** 拷贝屏幕到一个BufferedImage对象screenshot
            BufferedImage screenshot = new Robot().createScreenCapture(new Rectangle(0, 0, (int) d.getWidth(), (int) d.getHeight()));
            serialNum++;
            // 根据文件前缀变量和文件格式变量，自动生成文件名
            String name = filePreStr + String.valueOf(serialNum) + "." + imageFormat;
            File f = new File(name);
            System.out.print("Save File " + name);
            // 将screenshot对象写入图像文件
            ImageIO.write(screenshot, imageFormat, f);
            System.out.print("..Finished!\n");
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public static void main(String[] args) {
        ScreenShot cam = new ScreenShot("D:/screen/screen", "png");//
        cam.snapShot();
    }
}