import java.awt.CardLayout;
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

public class Main
{
   JFrame    f;
   JPanel    p;
   String    imagesFolder = "images";
   Dimension imageSize    = null;
   Mat       pgm          = Highgui.imread(imgPath("peppersgrad.pgm"));

   static
   {
      System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
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
      f.add(p);
      f.setResizable(false);
      f.setLayout(new FlowLayout(FlowLayout.LEADING, 10, 10));
      f.setLocationRelativeTo(null); // Center Screen
      f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }

   private void generateImage()
   {
      String filename = imgPath("generated.png");
      // A* Here
      Core.line(pgm, new Point(15, 10), new Point(15, 15), new Scalar(255, 255,
               0));
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

   public void run()
   {
      this.createWindow();
      this.generateImage();
      this.showWindow();
   }

   public static void main(String[] args)
   {
      new Main().run();
   }
}