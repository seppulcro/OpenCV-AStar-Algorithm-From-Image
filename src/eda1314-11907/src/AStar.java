import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.opencv.core.Mat;
import org.opencv.core.Point;

public class AStar
{
   BufferedImage     b;
   ArrayList<Point>  closedList;
   ArrayList<Point>  openList;
   ArrayList<String> timesList;
   Point             startPoint;
   Point             endPoint;
   Point             currentPoint;
   int               max;
   private Mat       pgm;
   long              totalTime = 0;

   /**
    * Receives a buffered image (png) and starting and end points, also the max
    * value of the color and the pgm matrix. It uses the PNG to get RGB value,
    * converts it to gray scale for neighbour finding.
    * 
    * @param b
    * @param sP
    * @param eP
    * @param max
    * @param pgm
    */
   public AStar(BufferedImage b, Point sP, Point eP, int max, Mat pgm)
   {
      this.setBufferedImage(b);
      this.pgm = pgm;
      this.startPoint = sP;
      this.endPoint = eP;
      this.currentPoint = sP;
      this.closedList = new ArrayList<Point>();
      this.timesList = new ArrayList<String>();
      this.max = max;
      this.closedList.add(currentPoint);

      int count = 1;
      while (!this.endPoint.equals(this.currentPoint))
      {
         long time = System.nanoTime();
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
         time = (System.nanoTime() - time);
         this.totalTime += time;
         this.timesList.add(new String(count + " "
                  + Long.toString(this.totalTime)));
         count++;
      }
   }

   /**
    * Gets the neighbours of a current point
    * 
    * @param current
    * @return
    */
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

   /**
    * Sets the buffered image (png)
    * 
    * @param b
    */
   public void setBufferedImage(BufferedImage b)
   {
      this.b = b;
   }

   /**
    * Returns the optimal path (closedList)
    * 
    * @return
    */
   public ArrayList<Point> getList()
   {
      return this.closedList;
   }

   /**
    * Returns the points color's intensity to the h method
    * 
    * @param p
    * @return
    */
   private double g(Point p)
   {
      return 1 - this.pgm.get((int) p.y, (int) p.x)[0] / this.max;
   }

   /**
    * Returns the distance between the current point and the end point
    * 
    * @param p
    * @return
    */
   private double h(Point p)
   {
      return Math.sqrt(Math.pow(p.x - this.endPoint.x, 2)
               + Math.pow(p.y - this.endPoint.y, 2));
   }

   /**
    * Divides h by 20 for better neighbour finding through the heuristic
    * 
    * @param p
    * @return
    */
   private double f(Point p)
   {
      return (((this.h(p))) / 20 + this.g(p));
   }

   /**
    * Write a file containing the execution times
    * 
    * @param filename
    * @throws IOException
    */
   public void writeFile(String filename) throws IOException
   {
      filename = filename.replaceAll("images:", "output");
      BufferedWriter out = null;
      out = new BufferedWriter(new FileWriter(filename));
      String results = timesList.toString();
      results = results.replaceAll("\\[", "").replaceAll("\\]", "")
               .replace(", ", "\n");
      out.write(results);
      out.close();
   }
}
