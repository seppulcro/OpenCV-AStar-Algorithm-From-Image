import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

public class Main
{

   static
   {
      System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
   }

   public static void main(String[] args)
   {
      System.out.println("Welcome to OpenCV " + Core.VERSION);
      Mat m = Highgui.imread("images/pepersgrad.pgm");

      JFrame f = new JFrame("PGM");
      f.setLayout(new FlowLayout());
      f.setSize(new Dimension(512, 512));
      f.setLocationRelativeTo(null); // Center Screen
      f.setVisible(true);
      f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

   }

}