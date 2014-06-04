import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

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
   String    filename     = "peppersgrad.pgm";
   Mat       pgm          = Highgui.imread(imgPath(filename));
   int       pgmWidth     = 0;
   int       pgmHeight    = 0;
   int[][]   matrix;

   static
   {
      System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
   }

   private void parsePGM()
   {
      try
      {
         InputStream i = new FileInputStream(imgPath(filename));
         BufferedReader b = new BufferedReader(new InputStreamReader(i));
         String magicNumber = b.readLine();
         while (b.readLine().startsWith("#"))
         {
            String line = b.readLine();
            String[] wh = line.replaceAll("^\\D+", "").split("\\D+");
            this.pgmWidth = Integer.parseInt(wh[0]);
            this.pgmHeight = Integer.parseInt(wh[0]);
            this.matrix = new int[this.pgmWidth][this.pgmHeight];
         }
         for (int row = 0; row < this.pgmWidth; row++)
            for (int col = 0; col < this.pgmWidth; col++)
               matrix[row][col] = Integer.parseInt(b.readLine());
         System.out
                  .format("PGM file: '%s'\nType: %s\nWidth: %d\nHeight: %d\nTotal: %d\n",
                           filename, magicNumber, this.pgmHeight,
                           this.pgmWidth, this.pgmHeight * this.pgmWidth);
      } catch (Throwable t)
      {
         t.printStackTrace(System.err);
         return;
      }

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
      f.getContentPane().setBackground(Color.darkGray);
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
      this.drawPixel(pgm, 260, 508, 255, 0, 0); // Start Pixel
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
      f.setIconImage(new ImageIcon(filename).getImage());
      f.setVisible(true);
   }

   private void drawPixel(Mat img, int x, int y, int r, int g, int b)
   {
      Core.line(img, new Point(x, y), new Point(x, y), new Scalar(b, g, r), 7);
   }

   public void run()
   {
      this.parsePGM();
      this.createWindow();
      this.generateImage();
      this.showWindow();
   }

   public static void main(String[] args)
   {
      new Main().run();
   }
}