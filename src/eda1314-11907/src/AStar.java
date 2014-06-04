import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.opencv.core.Point;

public class AStar
{
   BufferedImage b;
   Point[]       closedList;
   Point         startPoint;
   Point         endPoint;

   // Double pixelValue = ((double) (byte) b.getRGB(2, 0) / this.maxValue);

   public AStar(BufferedImage b, Point sP, Point eP)
   {
      this.setBufferedImage(b);
      this.startPoint = sP;
      this.endPoint = eP;
      System.out.println(startPoint.toString());
   }

   public void setBufferedImage(BufferedImage b)
   {
      this.b = b;
   }

   public Point[] getList()
   {
      return this.closedList;
   }

   public void writeFile(String filename) throws IOException
   {
      BufferedWriter out = null;
      out = new BufferedWriter(new FileWriter(filename));
      for (int i = 0; i < this.closedList.length; i++)
      {

      }
   }
}
