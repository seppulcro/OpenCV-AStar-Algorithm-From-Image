import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;

public class GUI
{
   JFrame                f;
   JPanel                p;
   String                imagesFolder = "images";
   Dimension             imageSize    = null;
   String                filename     = "peppersgrad.pgm";
   Mat                   pgm          = Highgui.imread(filePath(filename));
   String                magicNumber  = null;
   int                   maxValue     = 0;
   int                   pgmWidth     = 0;
   int                   pgmHeight    = 0;
   Point                 startPoint   = null;
   Point                 endPoint     = null;
   private BufferedImage b;

   static
   {
      System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
   }

   private void parsePGM()
   {
      InputStream i = null;
      try
      {
         i = new FileInputStream(filePath(filename));
      } catch (Throwable t)
      {
         t.printStackTrace();
      }
      Scanner s = new Scanner(i);
      this.magicNumber = s.nextLine();
      s.nextLine();
      String line = s.nextLine();
      String[] wh = line.replaceAll("^\\D+", "").split("\\D+"); // Split string
      this.pgmWidth = Integer.parseInt(wh[0]);
      this.pgmHeight = Integer.parseInt(wh[0]);
      this.maxValue = Integer.parseInt(new String(s.nextLine()));
      System.out.format("File: '%s' \n", filePath(filename));
      s.close();
   }

   public String filePath(String s)
   {
      String path = getClass().getProtectionDomain().getCodeSource()
               .getLocation().toString()
               + imagesFolder + "/" + s;
      path = path.replaceAll("file:", "");
      path = path.replaceAll(getClass().getName() + ".jar", "");
      return path;
   }

   private void createWindow()
   {
      f = new JFrame("11907 - Marco Sacristão");
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
      String filename = filePath("generated.png");
      this.startPoint = new Point(192, 48);
      this.endPoint = new Point(260, 508);
      ArrayList<Point> point = new AStar(this.b, this.startPoint,
               this.endPoint, this.maxValue, this.pgm).getList();
      for (Point points : point)
      {
         this.drawPixel(this.pgm, (int) points.x, (int) points.y, 0, 0, 255, 1);
      }
      this.drawPixel(pgm, (int) this.startPoint.x, (int) this.startPoint.y, 0,
               255, 0, 6);
      this.drawPixel(pgm, (int) this.endPoint.x, (int) this.endPoint.y, 255, 0,
               0, 6);
      Highgui.imwrite(filename, pgm);
      System.out.format("Generated: '%s'\n", filename);
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

   private void drawPixel(Mat img, int x, int y, int r, int g, int b, int t)
   {
      Core.line(img, new Point(x, y), new Point(x, y), new Scalar(b, g, r), t);
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
      System.out
               .format("A* Pathfinding and PGM parsing\n11907 - eMarco Sacristão \n");
      System.out
               .format("Green: Staring point.\nRed: Ending point.\nBlue: Optimal path.\n");
      new GUI().run();
   }
}