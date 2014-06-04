import java.awt.Point;
import java.util.ArrayList;

public class AStar
{
   double[][]       matrix;
   ArrayList<Point> closedList;

   public AStar(double[][] matrix)
   {
      this.setMatrix(matrix);
   }

   public void setMatrix(double[][] m)
   {
      this.matrix = m;
   }
}
