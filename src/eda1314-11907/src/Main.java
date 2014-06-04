import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;

/**
 * 
 * @author 11907_MarcoSacristao
 * 
 */

public class Main
{
   JFrame    f;
   JPanel    p;
   String    imagesFolder = "images";
   Dimension imageSize    = null;
   Mat       pgm          = Highgui.imread(imgPath("peppersgrad.pgm"));

   private static void parseImage()
   {

   }

   private String imgPath(String s)
   {
      String path = getClass().getProtectionDomain().getCodeSource()
               .getLocation().toString()
               + imagesFolder + "/" + s;
      path = path.replaceAll("file:", "");
      return path;
   }

   private void createWindow()
   {
      f = new JFrame("A* + PGM");
      p = new JPanel(new CardLayout());
      f.getContentPane().setBackground(Color.orange);
      f.add(p);
      f.setResizable(false);
      f.setLayout(new FlowLayout(FlowLayout.LEADING, 10, 10));
      f.setLocationRelativeTo(null);
      f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }

   private void generateImage()
   {
      String filename = imgPath("generated.png");
      this.drawPixel(pgm, 191, 48, 0, 255, 0); // Start Pixel
      this.drawPixel(pgm, 260, 508, 255, 0, 0); // End Pixel
      // A* Here
      Highgui.imwrite(filename, pgm);
      p.add(new JLabel(new ImageIcon(filename)));
      f.pack();
   }

   private void showWindow()
   {
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      Dimension windowSize = f.getSize();
      int windowX = Math.max(0, (screenSize.width - windowSize.width) / 2);
      int windowY = Math.max(0, (screenSize.height - windowSize.height) / 2);
      f.setLocation(windowX, windowY);
      f.setVisible(true);
   }

   private void drawPixel(Mat img, int x, int y, int r, int g, int b)
   {
      Core.line(img, new Point(x, y), new Point(x, y), new Scalar(b, g, r), 7);
   }

   public void run()
   {
      this.parseImage();
      this.createWindow();
      this.generateImage();
      this.showWindow();
   }

   public static void main(String[] args)
   {
      new Main().run();
   }
}