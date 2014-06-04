import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.opencv.core.Mat;
import org.opencv.core.Point;

public class AStar
{
   BufferedImage    b;
   ArrayList<Point> closedList;
   ArrayList<Point> openList;
   Point            startPoint;
   Point            endPoint;
   Point            currentPoint;
   int              max;
   private Mat      pgm;

   // Double pixelValue = ((double) (byte) b.getRGB(2, 0) / this.maxValue);

   public AStar(BufferedImage b, Point sP, Point eP, int max, Mat pgm)
   {
      this.setBufferedImage(b);
      this.pgm = pgm;
      this.startPoint = sP;
      this.endPoint = eP;
      this.currentPoint = sP;
      this.closedList = new ArrayList<Point>();
      this.max = max;

      this.closedList.add(currentPoint);
      while (!this.endPoint.equals(this.currentPoint))
      {
         this.openList = getNeighbours(this.currentPoint);
         ArrayList<Double> fValues = new ArrayList<Double>();
         for (Point point : this.openList)
         {
            fValues.add(this.f(point));
         }
         int index = 0;
         Double min = Double.MAX_VALUE;
         for (int i = 0; i < fValues.size(); i++)
         {
            if (min > fValues.get(i))
            {
               min = fValues.get(i);
               index = i;
            }
         }
         this.currentPoint = this.openList.get(index);
         this.closedList.add(this.currentPoint);
      }
   }

   private ArrayList<Point> getNeighbours(Point current)
   {
      ArrayList<Point> neighbours = new ArrayList<Point>();

      for (int i = -1; i < 2; i++)
      {
         for (int j = -1; j < 2; j++)
         {
            if (i != 0 && j != 0)
            {
               Point p = new Point(current.x + i, current.y + j);
               if (!this.closedList.contains(p))
               {
                  neighbours.add(p);
               }
            }

         }
      }

      return neighbours;
   }

   public void setBufferedImage(BufferedImage b)
   {
      this.b = b;
   }

   public ArrayList<Point> getList()
   {
      return this.closedList;
   }

   private double g(Point p)
   {
      return 1 - this.pgm.get((int) p.y, (int) p.x)[0] / this.max;
   }

   private double h(Point p)
   {
      return Math.sqrt(Math.pow(p.x - this.endPoint.x, 2)
               + Math.pow(p.y - this.endPoint.y, 2));
   }

   private double f(Point p)
   {
      return (((this.h(p))) / 10 + this.g(p));
   }

   public void writeFile(String filename) throws IOException
   {
      BufferedWriter out = null;
      out = new BufferedWriter(new FileWriter(filename));
      for (Point points : this.closedList)
      {

      }
   }
}
